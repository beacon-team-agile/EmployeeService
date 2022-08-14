package com.teamagile.bfEmployeeApplication.repository;

import com.teamagile.bfEmployeeApplication.entity.Employee;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee,String> {
//    Employee createEmployee(Employee e) finished
//    Employee updateEmployee(Employee e) Todo
//    Pageable<Employee> listAllEmployee(Pageable pageable) finished
//    Employee getEmployeeByEmail; finished
//    Employee getEmployeeByUserId; finsihed
//    Employee DeleteEmployeeById; has some problem
    List<Employee> findEmployeesByuserId(Integer id);
    List<Employee> findEmployeesByEmail(String email);

    Optional<Employee> findEmployeeById(String id);

    void deleteEmployeeById(String id);
}
