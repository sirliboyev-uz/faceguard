package com.example.faceguard.controller;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.EmployeeDto;
import com.example.faceguard.model.Employee;
import com.example.faceguard.model.Image;
import com.example.faceguard.model.Users;
import com.example.faceguard.model.utils.Annotation.RoleCheckName;
import com.example.faceguard.repository.EmployeeRepository;
import com.example.faceguard.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @RoleCheckName("CREATE_USER")
    @PostMapping()
    public ResponseEntity<ApiResponse> createEmployee(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("middleName") String middleName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("gender") String gender,
            @RequestParam("jobTitle") String jobTitle,
            @RequestParam("schedule") String schedule,
            @RequestParam("salary") double salary,
            @RequestParam("companyId") Long companyId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        // Create and populate the EmployeeDto
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName(firstName);
        employeeDto.setLastName(lastName);
        employeeDto.setMiddleName(middleName);
        employeeDto.setEmail(email);
        employeeDto.setPhoneNumber(phoneNumber);
        employeeDto.setBirthDate(birthDate);
        employeeDto.setGender(gender);
        employeeDto.setJobTitle(jobTitle);
        employeeDto.setSchedule(schedule);
        employeeDto.setSalary(salary);
        employeeDto.setCompanyId(companyId);
        employeeDto.setImage(image);

        // Create the employee using your service
        ApiResponse response = employeeService.createEmployee(employeeDto);
        HttpStatus status = response.getType() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }


    // Update Employee
    @RoleCheckName("CREATE_USER")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateEmployee(@PathVariable Long id, @ModelAttribute EmployeeDto employeeDto) {
        ApiResponse response = employeeService.updateEmployee(id, employeeDto);
        HttpStatus status = response.getType() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    // Delete Employee
    @RoleCheckName("CREATE_USER")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable Long id) {
        ApiResponse response = employeeService.deleteEmployee(id);
        HttpStatus status = response.getType() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, status);
    }

    // Get Employee by ID
    @RoleCheckName("CREATE_USER")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(employee);
    }

    // Get all Employees
    @RoleCheckName("CREATE_USER")
    @GetMapping("/list")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/avatar/{userId}")
    public ResponseEntity<byte[]> getUserAvatar(@PathVariable Long userId) {
        Employee user = employeeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Image image = user.getImage();
        if (image == null || image.getBytes() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, image.getContentType())
                .body(image.getBytes());
    }
}
