package com.teamagile.bfEmployeeApplication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.teamagile.bfEmployeeApplication.domain.response.EmployeesResponse;
import com.teamagile.bfEmployeeApplication.domain.response.SingleEmployeeResponse;
import com.teamagile.bfEmployeeApplication.entity.Address;
import com.teamagile.bfEmployeeApplication.entity.Contact;
import com.teamagile.bfEmployeeApplication.entity.Employee;
import com.teamagile.bfEmployeeApplication.entity.VisaStatus;
import com.teamagile.bfEmployeeApplication.repository.EmployeeRepository;



@WebMvcTest(EmployeeController.class)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    EmployeeRepository employeeRepository;
    
	
	Employee mockEmp;
	List<Employee> empList;
	Address mockAddr;
	Contact mockCont;
	VisaStatus mockVisaStat;
	
    @BeforeEach
    public void setupTests() {
    	mockEmp = Employee.builder()
    			.UserId(0).firstName("Alice").lastName("Test")
    			.email("tester@tr.net").gender("male").cellPhone("0000000000").build();
    	mockAddr = Address.builder().addressLine1("000 Null St.").city("Faketon").state("CA").zipCode(11111).build();
    	mockCont = Contact.builder().firstName("Bob").lastName("Test").cellPhone("0030030003").relationship("brother").build();
    	mockVisaStat = VisaStatus.builder().activeFlag(true).visaType("H1B").startDate(Date.valueOf("2020-01-01")).build();
    	empList = new ArrayList<>();
    }
    
    @Test
    public void CreateNewEmployeeTests_base() throws Exception {
        when(employeeRepository.createEmployee(mockEmp)).thenReturn(mockEmp);
        MvcResult result = 
        		mockMvc.perform(MockMvcRequestBuilders.post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(mockEmp))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();   	
        SingleEmployeeResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), SingleEmployeeResponse.class);
        assertEquals(resp.getResponseStatus().is_success(), true);
        assertEquals(resp.getEmployee(), mockEmp);
    }
    
    @Test
    public void CreateNewEmployeeTests_empty() throws Exception {
        when(employeeRepository.createEmployee(null)).thenReturn(null);
        MvcResult result = 
        		mockMvc.perform(MockMvcRequestBuilders.post("/employee/add")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();   	
        SingleEmployeeResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), SingleEmployeeResponse.class);
        assertEquals(resp.getResponseStatus().is_success(), false);
    }
    
    @Test
    public void GetAllEmployees_base() throws Exception {
    	Pageable p = PageRequest.of(0, 1);
    	empList.add(mockEmp);
    	Page<Employee> pagedMock = new PageImpl<>(empList);
        when(employeeRepository.listAllEmployee(p)).thenReturn(pagedMock);
        MvcResult result = 
        		mockMvc.perform(MockMvcRequestBuilders.get("/employee/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();   	
        EmployeesResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), EmployeesResponse.class);
        assertEquals(resp.getResponseStatus().is_success(), true);
        assertEquals(resp.getEmployees(), pagedMock);
    }
    
    @Test
    public void GetEmployeeById_base() throws Exception {
        when(employeeRepository.findEmployeeById("test_id")).thenReturn(Optional.of(mockEmp));
        MvcResult result = 
        		mockMvc.perform(MockMvcRequestBuilders.get("/employee/test_id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();   	
        SingleEmployeeResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), SingleEmployeeResponse.class);
        assertEquals(resp.getResponseStatus().is_success(), true);
        assertEquals(resp.getEmployee(), mockEmp);
    }
    
    @Test
    public void GetEmployeeById_empty() throws Exception {
        when(employeeRepository.findEmployeeById("test_empty_id")).thenReturn(Optional.empty());
        MvcResult result = 
        		mockMvc.perform(MockMvcRequestBuilders.get("/employee/test_empty_id")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();   	
        SingleEmployeeResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), SingleEmployeeResponse.class);
        assertEquals(resp.getResponseStatus().is_success(), false);
    }
    
    @Test
    public void GetEmployeeByUserId_base() throws Exception {
    	empList.add(mockEmp);
        when(employeeRepository.findEmployeesByuserId(999)).thenReturn(empList);
        MvcResult result = 
        		mockMvc.perform(MockMvcRequestBuilders.get("/userId/999")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();   	
        EmployeesResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), EmployeesResponse.class);
        assertEquals(resp.getResponseStatus().is_success(), true);
        assertEquals(resp.getEmployees(), empList);
    }
    
    @Test
    public void GetEmployeeByEmail_base() throws Exception {
    	empList.add(mockEmp);
        when(employeeRepository.findEmployeesByEmail("employee@emp")).thenReturn(empList);
        MvcResult result = 
        		mockMvc.perform(MockMvcRequestBuilders.get("/email/employee@emp")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();   	
        EmployeesResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), EmployeesResponse.class);
        assertEquals(resp.getResponseStatus().is_success(), true);
        assertEquals(resp.getEmployees(), empList);
    }
}
