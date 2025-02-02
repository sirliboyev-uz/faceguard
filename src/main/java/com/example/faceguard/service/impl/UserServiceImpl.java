package com.example.faceguard.service.impl;

import com.example.faceguard.dto.*;
import com.example.faceguard.model.Company;
import com.example.faceguard.model.Image;
import com.example.faceguard.model.Role;
import com.example.faceguard.model.Users;
import com.example.faceguard.repository.CompanyRepository;
import com.example.faceguard.repository.ImageRepository;
import com.example.faceguard.repository.RoleRepository;
import com.example.faceguard.repository.UserRepository;
import com.example.faceguard.service.UserService;
import com.example.faceguard.web.Token;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    Token token;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CompanyRepository companyRepository;

    @SneakyThrows
    @Override
    public ApiResponse registerUser(RegisterDto registerDTO) {
        boolean optionalUsers=userRepository.existsByUsername(registerDTO.getUsername());
        Optional<Company> companyOptional = companyRepository.findById(Long.valueOf(registerDTO.getCompanyId()));
        Optional<Role>  roleOptional= roleRepository.findById(Long.valueOf(registerDTO.getRoleId()));

        if (registerDTO.getPassword().equals(registerDTO.getRepeatPassword())){
            if (!optionalUsers){
                MultipartFile multipartFile=registerDTO.getImage();
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
                    Users users = new Users();
                    BeanUtils.copyProperties(registerDTO, users);
                    users.setImage(imageRepository.save(fileSource));
                    users.setPassword(passwordEncoder.encode(users.getPassword()));
                    users.setCompany(companyOptional.get());
                    users.setRole(roleOptional.get());
                    users = userRepository.save(users);
                    System.out.println(users);
                    return new ApiResponse("Joylandi", true,200);
                }
                return new ApiResponse("Error, no", false,403);
            }
            return new ApiResponse("Avval ro'yxatdan o'tgansiz", false, 401);
        }
        return new ApiResponse("Parollar mos emas", false, 500);
    }

    @Override
    public Optional<Users> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Users> getUsers() {
        return userRepository.findAll();
    }
}
