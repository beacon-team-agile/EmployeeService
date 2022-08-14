package com.teamagile.bfEmployeeApplication.controller;

import com.teamagile.bfEmployeeApplication.domain.response.EmployeesResponse;
import com.teamagile.bfEmployeeApplication.domain.response.SingleEmployeeResponse;
import com.teamagile.bfEmployeeApplication.domain.response.common.ResponseStatus;
import com.teamagile.bfEmployeeApplication.entity.*;
import com.teamagile.bfEmployeeApplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    EmployeeRepository employeeRepository;
    AddressRepository addressRepository;
    ContactRepository contactRepository;
    VisaStatusRepository visaStatusRepository;
    PersonalDocumentRepository personalDocumentRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Autowired
    public void setPersonalDocumentRepository(PersonalDocumentRepository personalDocumentRepository) {
        this.personalDocumentRepository = personalDocumentRepository;
    }

    @Autowired
    public void setVisaStatusRepository(VisaStatusRepository visaStatusRepository) {
        this.visaStatusRepository = visaStatusRepository;
    }

    @PostMapping("add")
    public SingleEmployeeResponse CreateNewEmployee(@RequestBody Employee employee) {
        List<Address> addressList = employee.getAddress();
        List<Contact> contactList = employee.getContact();
        List<VisaStatus> visaStatusList = employee.getVisaStatus();
        List<PersonalDocument> personalDocumentList = employee.getPersonalDocument();

        addressRepository.saveAll(addressList);
        contactRepository.saveAll(contactList);
        visaStatusRepository.saveAll(visaStatusList);
        personalDocumentRepository.saveAll(personalDocumentList);

        Employee newEmployee = employeeRepository.save(employee);

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

    @GetMapping("userId")
    public EmployeesResponse GetEmployeeByUserId(@RequestParam Integer userId) {
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
    public SingleEmployeeResponse DeleteEmployeeByEmail(@RequestParam String Id) {
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeById(Id);

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

        employeeRepository.deleteEmployeeById(Id);


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


}


