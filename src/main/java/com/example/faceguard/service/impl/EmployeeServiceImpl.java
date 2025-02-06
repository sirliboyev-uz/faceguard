package com.example.faceguard.service.impl;


import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.EmployeeDto;
import com.example.faceguard.model.*;
import com.example.faceguard.repository.CompanyRepository;
import com.example.faceguard.repository.EmployeeRepository;
import com.example.faceguard.repository.ImageRepository;
import com.example.faceguard.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public ApiResponse createEmployee(EmployeeDto employeeDto) {
        if (employeeRepository.existsByPhoneNumber(employeeDto.getPhoneNumber())) {
            return new ApiResponse("Employee with this phone already exists", false, 409);
        }
        try {
            Employee employee = new Employee();
            BeanUtils.copyProperties(employeeDto, employee);

            // Set company if provided
            if (employeeDto.getCompanyId() != null) {
                Optional<Company> companyOpt = companyRepository.findById(employeeDto.getCompanyId());
                companyOpt.ifPresent(employee::setCompany);
            }

            MultipartFile multipartFile = employeeDto.getImage();
            if (multipartFile != null && !multipartFile.isEmpty()) {
                Image image = new Image();
                image.setName(multipartFile.getOriginalFilename());
                image.setFileSize(multipartFile.getSize());
                image.setContentType(multipartFile.getContentType());
                image.setBytes(multipartFile.getBytes());
                image.setFaceEmbeddings(0.0f); // Default value; adjust as needed.
                Image savedImage = imageRepository.save(image);
                employee.setImage(savedImage);
            }
            employeeRepository.save(employee);
            return new ApiResponse("Employee created successfully", true, 200);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ApiResponse("Error processing image", false, 500);
        }
    }

    @Override
    public ApiResponse updateEmployee(Long id, EmployeeDto employeeDto) {
        Optional<Employee> optEmployee = employeeRepository.findById(id);
        if (!optEmployee.isPresent()) {
            return new ApiResponse("Employee not found", false, 404);
        }
        try {
            Employee employee = optEmployee.get();
            // Copy properties except id and image (to be handled separately)
            BeanUtils.copyProperties(employeeDto, employee, "id", "image");

            // Update company if provided
            if (employeeDto.getCompanyId() != null) {
                Optional<Company> companyOpt = companyRepository.findById(employeeDto.getCompanyId());
                companyOpt.ifPresent(employee::setCompany);
            }

            MultipartFile multipartFile = employeeDto.getImage();
            if (multipartFile != null && !multipartFile.isEmpty()) {
                // If an image is provided, update it (or create a new one if none exists)
                Image image = employee.getImage();
                if (image == null) {
                    image = new Image();
                }
                image.setName(multipartFile.getOriginalFilename());
                image.setFileSize(multipartFile.getSize());
                image.setContentType(multipartFile.getContentType());
                image.setBytes(multipartFile.getBytes());
                image.setFaceEmbeddings(0.0f);
                Image savedImage = imageRepository.save(image);
                employee.setImage(savedImage);
            }
            employeeRepository.save(employee);
            return new ApiResponse("Employee updated successfully", true, 200);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ApiResponse("Error processing image", false, 500);
        }
    }

    @Override
    public ApiResponse deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            return new ApiResponse("Employee not found", false, 404);
        }
        employeeRepository.deleteById(id);
        return new ApiResponse("Employee deleted successfully", true, 200);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
