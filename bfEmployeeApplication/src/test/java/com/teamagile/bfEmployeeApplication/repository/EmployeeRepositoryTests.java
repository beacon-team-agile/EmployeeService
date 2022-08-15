package com.teamagile.bfEmployeeApplication.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.teamagile.bfEmployeeApplication.entity.Address;
import com.teamagile.bfEmployeeApplication.entity.Contact;
import com.teamagile.bfEmployeeApplication.entity.Employee;
import com.teamagile.bfEmployeeApplication.entity.VisaStatus;

@ActiveProfiles(value = "test")
@DataMongoTest
public class EmployeeRepositoryTests {
	
	@Autowired
	EmployeeRepository repos;
	List<Employee> empList;
	
	Employee mockEmp;
	Address mockAddr;
	Contact mockCont;
	VisaStatus mockVisaStat;
	
    @BeforeEach
    public void setupTests() {
    	mockEmp = Employee.builder()
    			.userId(0).firstName("Alice").lastName("Test")
    			.email("tester@tr.net").gender("male").cellPhone("0000000000").build();
    	mockAddr = Address.builder().addressLine1("000 Null St.").city("Faketon").state("CA").zipCode("11111").build();
    	mockCont = Contact.builder().firstName("Bob").lastName("Test").cellPhone("0030030003").relationship("brother").build();
    	mockVisaStat = VisaStatus.builder().activeFlag(true).visaType("H1B").startDate("2020-01-01").build();
    	empList = new ArrayList<>();
    }
    
    //Create
    @Test
    public void testCreateEmployee_base() {
    	Employee e2 = repos.insert(mockEmp);
    	assertEquals(e2, mockEmp);	
    }    
    @Test
    public void testCreateEmployee_empty() {
    	Employee en = new Employee();
    	Employee e2	= repos.insert(en);
    	assertEquals(e2, null);
    }
    
    //Update
    @Test
    public void testUpdateEmployee_base() {
    	Employee e1 = repos.insert(mockEmp);
    	e1.setFirstName("Alternate");
    	Employee e2 = repos.save(e1);
    	assertNotEquals(e2, mockEmp);	
    	assertEquals(e1, e2);
    	assertEquals(e1, repos.findById(e2.getId()));
    }    
    @Test
    public void testUpdateEmployee_null() {
    	Employee en = repos.save(null);
    	assertEquals(en, null);
    }   
    @Test
    public void testUpdateEmployee_empty() {
    	Employee e2	= repos.save(mockEmp);
    	assertEquals(e2, null);
    	Pageable p = PageRequest.of(0, 10);
    	assertEquals(empList, repos.findAll(p)); //Empty
    }
    
    //Update
    @Test
    public void testDeleteEmployee_base() {
    	Employee e1 = repos.insert(mockEmp);
    	repos.deleteById(e1.getId());
    	Pageable p = PageRequest.of(0, 10);
    	assertEquals(empList, repos.findAll(p)); //Empty	
    }   
    
    //ListAll
    @Test
    public void testListAllEmployee_base() {
    	Employee e2	= repos.insert(mockEmp);
    	empList.add(e2);
    	repos.insert(e2);
    	Pageable p = PageRequest.of(0, 1);
    	assertEquals(empList, repos.findAll(p));
    }    
    @Test
    public void testListAllEmployee_none() {
    	Pageable p = PageRequest.of(0, 10);
    	assertEquals(empList, repos.findAll(p));
    }  
    @Test
    public void testListAllEmployee_pageLessThanMax() {
    	Employee e2	= repos.insert(mockEmp);
    	empList.add(e2);
    	repos.insert(mockEmp);
    	Pageable p = PageRequest.of(0, 1);
    	assertEquals(empList, repos.findAll(p));
    }   
    @Test
    public void testListAllEmployee_pageMoreThanMax() {
    	Employee e2	= repos.insert(mockEmp);
    	empList.add(e2);
    	Pageable p = PageRequest.of(0, 3);
    	assertEquals(empList, repos.findAll(p));
    }
    
    //FindByUserID
    @Test
    public void testFindEmployeesByuserId_base() {    	
    	Employee e2	= repos.insert(mockEmp);
		empList.add(e2);
    	assertEquals(empList, repos.findEmployeesByuserId(e2.getUserId()));	
    }   
    @Test
    public void testFindEmployeesByuserId_null() {    	
    	Employee e2	= repos.insert(mockEmp);
    	assertEquals(empList, repos.findEmployeesByuserId(1000));	
    }
    
    //FindByEmail
    @Test
    public void testFindEmployeesByEmail_base() {    	
    	Employee e2	= repos.insert(mockEmp);
		empList.add(e2);
    	assertEquals(empList, repos.findEmployeesByEmail(e2.getEmail()));	
    }
    @Test
    public void testFindEmployeesByEmail_null() {    	
    	assertEquals(empList, repos.findEmployeesByEmail(mockEmp.getEmail()));	
    }
    @Test
    public void testFindEmployeesByEmail_badString() {    	
    	Employee e2	= repos.insert(mockEmp);
    	assertEquals(empList, repos.findEmployeesByEmail("none"));	
    }

}
