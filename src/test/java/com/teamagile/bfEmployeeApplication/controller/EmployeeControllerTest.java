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
import com.teamagile.bfEmployeeApplication.entity.PersonalDocument;
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
	PersonalDocument mockPersonalDoc;
	
    @BeforeEach
    public void setupTests() {
    	mockAddr = Address.builder().addressLine1("000 Null St.").city("Faketon").state("CA").zipCode("11111").build();
    	mockCont = Contact.builder().firstName("Bob").lastName("Test").cellPhone("0030030003").relationship("brother").build();
    	mockVisaStat = VisaStatus.builder().activeFlag(true).visaType("H1B").startDate("2020-01-01").build();
    	mockPersonalDoc = PersonalDocument.builder().title("I9999").path("amazon/etc/etc").build();
    	List<Address> addrList = new ArrayList<>();
    	List<Contact> contList = new ArrayList<>();
    	List<VisaStatus> visaList = new ArrayList<>();
    	List<PersonalDocument> docList = new ArrayList<>();
    	
    	addrList.add(mockAddr);
    	contList.add(mockCont);
    	visaList.add(mockVisaStat);
    	docList.add(mockPersonalDoc);
    	
    	mockEmp = Employee.builder()
    			.userId(0).firstName("Alice").lastName("Test")
    			.email("tester@tr.net").gender("male").cellPhone("0000000000")
    			.address(addrList).contact(contList)
    			.visaStatus(visaList).personalDocument(docList).build();
    	empList = new ArrayList<>();
    }
    
    @Test
    public void CreateNewEmployeeTests_base() throws Exception {
        when(employeeRepository.insert(mockEmp)).thenReturn(mockEmp);
        MvcResult result = 
        		mockMvc.perform(MockMvcRequestBuilders.post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(mockEmp))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();   	
        mockAddr.setId(1);
        mockCont.setId(1);
        mockPersonalDoc.setId(1);
        mockVisaStat.setId(1);
        SingleEmployeeResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), SingleEmployeeResponse.class);
        //assertEquals(resp.getResponseStatus().is_success(), true);
        assertEquals(resp.getEmployee(), mockEmp);
    }
    
    @Test
    public void CreateNewEmployeeTests_empty() throws Exception {
        when(employeeRepository.save(null)).thenReturn(null);
        MvcResult result = 
        		mockMvc.perform(MockMvcRequestBuilders.post("/employee/add")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();   	
        SingleEmployeeResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), SingleEmployeeResponse.class);
        assertEquals(resp, null);
    }
    
    @Test
    public void GetAllEmployees_base() throws Exception {
    	Pageable p = PageRequest.of(0, 1);
    	empList.add(mockEmp);
    	Page<Employee> pagedMock = new PageImpl<>(empList);
        when(employeeRepository.findAll()).thenReturn(empList);
        MvcResult result = 
        		mockMvc.perform(MockMvcRequestBuilders.get("/employee/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();   	
        EmployeesResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), EmployeesResponse.class);
        //assertEquals(true, resp.getResponseStatus().is_success());
        assertEquals(empList, resp.getEmployees());
    }
    
    @Test
    public void GetEmployeeById_base() throws Exception {
        when(employeeRepository.findEmployeeByid("test_id")).thenReturn(Optional.of(mockEmp));
        MvcResult result = 
        		mockMvc.perform(MockMvcRequestBuilders.get("/employee/test_id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();   	
        SingleEmployeeResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), SingleEmployeeResponse.class);
        //assertEquals(resp.getResponseStatus().is_success(), true);
        assertEquals(resp.getEmployee(), mockEmp);
    }
    
    @Test
    public void GetEmployeeById_empty() throws Exception {
        when(employeeRepository.findEmployeeByid("test_empty_id")).thenReturn(Optional.empty());
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
        		mockMvc.perform(MockMvcRequestBuilders.get("/employee/userId/999")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();   	
        EmployeesResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), EmployeesResponse.class);
        //assertEquals(resp.getResponseStatus().is_success(), true);
        assertEquals(resp.getEmployees(), empList);
    }
    
    @Test
    public void GetEmployeeByEmail_base() throws Exception {
    	empList.add(mockEmp);
        when(employeeRepository.findEmployeesByEmail("employee@emp")).thenReturn(empList);
        MvcResult result = 
        		mockMvc.perform(MockMvcRequestBuilders.get("/employee/email/")
        		.param("email", "employee@emp")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();   	
        EmployeesResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), EmployeesResponse.class);
        
        //assertEquals(true, resp.getResponseStatus().is_success());
        assertEquals(resp.getEmployees(), empList);
    }
    
    @Test
    public void UpdateEmployeeByIdTests_base() throws Exception {
        when(employeeRepository.save(mockEmp)).thenReturn(mockEmp);
        when(employeeRepository.findEmployeeByid("fake")).thenReturn(Optional.of(mockEmp));
        MvcResult result = 
        		mockMvc.perform(MockMvcRequestBuilders.patch("/employee/update/fake")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(mockEmp))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();   	
        SingleEmployeeResponse resp = new Gson().fromJson(result.getResponse().getContentAsString(), SingleEmployeeResponse.class);
        //assertEquals(resp.getResponseStatus().is_success(), true);
        assertEquals(resp.getEmployee(), mockEmp);
    }
}
