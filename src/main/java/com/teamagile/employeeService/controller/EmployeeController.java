package com.teamagile.employeeService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import com.teamagile.employeeService.domain.request.VisaStatusUpdateRequest;
import com.teamagile.employeeService.domain.response.EmployeesResponse;
import com.teamagile.employeeService.domain.response.SingleEmployeeResponse;
import com.teamagile.employeeService.domain.response.common.ResponseStatus;
import com.teamagile.employeeService.entity.*;
import com.teamagile.employeeService.repository.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("add")
    public SingleEmployeeResponse CreateNewEmployee(@RequestBody Employee employee) {
        List<Address> addressList = employee.getAddress();
        for(int i = 1;i<addressList.size()+1;i++) {
            addressList.get(i-1).setId(i);
        }

        List<Contact> contactList = employee.getContact();
        for(int i = 1;i<contactList.size()+1;i++) {
            contactList.get(i-1).setId(i);
        }

        List<VisaStatus> visaStatusList = employee.getVisaStatus();
        for(int i = 1;i<visaStatusList.size()+1;i++) {
            visaStatusList.get(i-1).setId(i);
        }

        List<PersonalDocument> personalDocumentList = employee.getPersonalDocument();
        for(int i = 1;i<personalDocumentList.size()+1;i++) {
            personalDocumentList.get(i-1).setId(i);
        }

        Employee newEmployee = Employee.builder()
                .userId(employee.getUserId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .middleName(employee.getMiddleName())
                .preferredName(employee.getPreferredName())
                .email(employee.getEmail())
                .cellPhone(employee.getCellPhone())
                .alternatePhone(employee.getAlternatePhone())
                .gender(employee.getGender())
                .ssn(employee.getSsn())
                .dob(employee.getDob())
                .startDate(employee.getStartDate())
                .endDate(employee.getEndDate())
                .driverLicense(employee.getDriverLicense())
                .driverLicenseExpiration(employee.getDriverLicenseExpiration())
                .houseId(employee.getHouseId())
                .contact(contactList)
                .address(addressList)
                .visaStatus(visaStatusList)
                .personalDocument(personalDocumentList)
                .build();

        employeeRepository.save(newEmployee);

        return SingleEmployeeResponse.builder()
                .responseStatus(
                        ResponseStatus.builder()
                                .is_success(true)
                                .message("Successfully added new employee!")
                                .build()
                )
                .employee(newEmployee)
                .build();
    }

    @GetMapping("all")
    public EmployeesResponse GetAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return EmployeesResponse.builder()
                .responseStatus(
                        ResponseStatus.builder()
                                .is_success(true)
                                .message("Successfully Getting all employee!")
                                .build()
                )
                .employees(employees)
                .build();
    }

    @GetMapping("{id}")
    public SingleEmployeeResponse GetEmployeeById(@PathVariable String id) {
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByid(id);
        if (!employeeOptional.isPresent()) {
            return SingleEmployeeResponse.builder()
                    .responseStatus(
                            ResponseStatus.builder()
                                    .is_success(false)
                                    .message("Didn't find employee")
                                    .build()
                    )
                    .employee(null)
                    .build();
        }
        return SingleEmployeeResponse.builder()
                .responseStatus(
                        ResponseStatus.builder()
                                .is_success(true)
                                .message("Found Employee")
                                .build()
                )
                .employee(employeeOptional.get())
                .build();
    }

    @GetMapping("userId/{userId}")
    public EmployeesResponse GetEmployeeByUserId(@PathVariable Integer userId) {
        List<Employee> employees = employeeRepository.findEmployeesByuserId(userId);

        return EmployeesResponse.builder()
                .responseStatus(
                        ResponseStatus.builder()
                                .is_success(true)
                                .message("Successfully Get employee By userId!")
                                .build()
                )
                .employees(employees)
                .build();
    }

    @GetMapping("email")
    public EmployeesResponse GetEmployeeByEmail(@RequestParam String email) {
        List<Employee> employees = employeeRepository.findEmployeesByEmail(email);

        return EmployeesResponse.builder()
                .responseStatus(
                        ResponseStatus.builder()
                                .is_success(true)
                                .message("Successfully Get employee By email!")
                                .build()
                )
                .employees(employees)
                .build();
    }

    @DeleteMapping("delete")
    public SingleEmployeeResponse DeleteEmployeeByEmail(@RequestParam String id) {
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByid(id);

        if (!employeeOptional.isPresent()) {
            return SingleEmployeeResponse.builder()
                    .responseStatus(
                            ResponseStatus.builder()
                                    .is_success(false)
                                    .message("Didn't find employee")
                                    .build()
                    )
                    .employee(null)
                    .build();
        }

        employeeRepository.deleteEmployeeByid(id);


        return SingleEmployeeResponse.builder()
                    .responseStatus(
                            ResponseStatus.builder()
                                    .is_success(true)
                                    .message("Deleted Employee")
                                    .build()
                    )
                    .employee(employeeOptional.get())
                    .build();
    }


    @PostMapping("update/{id}")
    public SingleEmployeeResponse updateEmployeeById(@PathVariable String id,
                                                     @RequestBody Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByid(id);

        if (!employeeOptional.isPresent()) {
            return SingleEmployeeResponse.builder()
                    .responseStatus(
                            ResponseStatus.builder()
                                    .is_success(false)
                                    .message("Didn't find employee")
                                    .build()
                    )
                    .employee(null)
                    .build();
        }

        List<Address> addressList = employee.getAddress();
        for(int i = 1;i<addressList.size()+1;i++) {
            addressList.get(i-1).setId(i);
        }

        List<Contact> contactList = employee.getContact();
        for(int i = 1;i<contactList.size()+1;i++) {
            contactList.get(i-1).setId(i);
        }

        List<VisaStatus> visaStatusList = employee.getVisaStatus();
        for(int i = 1;i<visaStatusList.size()+1;i++) {
            visaStatusList.get(i-1).setId(i);
        }

        List<PersonalDocument> personalDocumentList = employee.getPersonalDocument();
        for(int i = 1;i<personalDocumentList.size()+1;i++) {
            personalDocumentList.get(i-1).setId(i);
        }

        Employee updated_employee = employeeOptional.get();

        updated_employee.setUserId(employee.getUserId());
        updated_employee.setFirstName(employee.getFirstName());
        updated_employee.setLastName(employee.getLastName());
        updated_employee.setMiddleName(employee.getMiddleName());
        updated_employee.setPreferredName(employee.getPreferredName());
        updated_employee.setEmail(employee.getEmail());
        updated_employee.setCellPhone(employee.getCellPhone());
        updated_employee.setAlternatePhone(employee.getAlternatePhone());
        updated_employee.setGender(employee.getGender());
        updated_employee.setSsn(employee.getSsn());
        updated_employee.setDob(employee.getDob());
        updated_employee.setStartDate(employee.getStartDate());
        updated_employee.setEndDate(employee.getEndDate());
        updated_employee.setDriverLicense(employee.getDriverLicense());
        updated_employee.setDriverLicenseExpiration(employee.getDriverLicenseExpiration());
        updated_employee.setHouseId(employee.getHouseId());
        updated_employee.setContact(contactList);
        updated_employee.setAddress(addressList);
        updated_employee.setVisaStatus(visaStatusList);
        updated_employee.setPersonalDocument(personalDocumentList);

        employeeRepository.save(updated_employee);

        return SingleEmployeeResponse.builder()
                .responseStatus(
                        ResponseStatus.builder()
                                .is_success(true)
                                .message("Successfully updating employee!")
                                .build()
                )
                .employee(updated_employee)
                .build();
    }

    @PostMapping("/updateVisaStatus")
    public SingleEmployeeResponse updateEmployeeVisaStatusById(@RequestParam String id,
                                                     @RequestBody VisaStatusUpdateRequest visaStatusUpdateRequest) {

        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByid(id);

        if (!employeeOptional.isPresent()) {
            return SingleEmployeeResponse.builder()
                    .responseStatus(
                            ResponseStatus.builder()
                                    .is_success(false)
                                    .message("Didn't find employee")
                                    .build()
                    )
                    .employee(null)
                    .build();
        }

        Employee employee = employeeOptional.get();

        VisaStatus visaStatus;

        if(employee.getVisaStatus().size()==0) {
            visaStatus = new VisaStatus();
            visaStatus.setId(1);
        }else{
            //assume has at most one
            visaStatus = employee.getVisaStatus().get(0);
        }

        Date date = new Date();

        visaStatus.setVisaType(visaStatusUpdateRequest.getVisaStatus());
        visaStatus.setStartDate(visaStatusUpdateRequest.getStartDate());
        visaStatus.setEndDate(visaStatusUpdateRequest.getEndDate());
        visaStatus.setLastModificationDate(String.valueOf(date));

        employeeRepository.save(employee);

        return SingleEmployeeResponse.builder()
                .responseStatus(
                        ResponseStatus.builder()
                                .is_success(true)
                                .message("Successfully updating employee's visa status!")
                                .build()
                )
                .employee(employee)
            .build();
    }

    
    @GetMapping("list/{pageNo}")
    public EmployeesResponse GetEmployeeList(@PathVariable Integer pageNo, @RequestParam Integer pageSize) {

    	Pageable p = PageRequest.of(pageNo, pageSize);
        Page<Employee> pageReturn = employeeRepository.findAll(p);

        return EmployeesResponse.builder()
                .responseStatus(
                        ResponseStatus.builder()
                                .is_success(true)
                                .message("Successfully Getting all employee!")
                                .build()
                )
                .employees(pageReturn.toList())

                .build();
    }
}


