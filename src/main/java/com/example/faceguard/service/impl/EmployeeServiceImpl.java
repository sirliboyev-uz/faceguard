package com.example.faceguard.service.impl;


import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.EmployeeDto;
import com.example.faceguard.dto.RegisterDto;
import com.example.faceguard.model.Employee;
import com.example.faceguard.model.Image;
import com.example.faceguard.model.Users;
import com.example.faceguard.repository.EmployeeRepository;
import com.example.faceguard.repository.ImageRepository;
import com.example.faceguard.service.EmployeeService;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ImageRepository imageRepository;

    @SneakyThrows
    @Override
    public ApiResponse createEmployee(EmployeeDto employeeDto) {
        boolean optionalEmployee=employeeRepository.existsByPhone(employeeDto.getPhone());
        if (!optionalEmployee){
            MultipartFile multipartFile=employeeDto.getImage();
            if (multipartFile!=null){
                String fileName=multipartFile.getOriginalFilename();
                float fileSize=multipartFile.getSize();
                String contentType=multipartFile.getContentType();
                byte[] fileBytes=multipartFile.getBytes();
                Image fileSource=new Image();
                fileSource.setName(fileName);
                fileSource.setFileSize(fileSize);
                fileSource.setContentType(contentType);
                fileSource.setBytes(fileBytes);
                Employee employee = new Employee();
                BeanUtils.copyProperties(employeeDto, employee);
                employee.setImage(imageRepository.save(fileSource));
                employee = employeeRepository.save(employee);
                System.out.println(employee);
                return new ApiResponse("Joylandi", true);
            }
            return new ApiResponse("Error, no", false, 409);
        }
        return new ApiResponse("Avval ro'yxatdan o'tgansiz", false);
    }
}
