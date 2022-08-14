package com.teamagile.bfEmployeeApplication.repository;

import com.teamagile.bfEmployeeApplication.entity.Contact;
import com.teamagile.bfEmployeeApplication.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact,String> {
}
