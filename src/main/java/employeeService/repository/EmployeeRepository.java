package employeeService.repository;

import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import employeeService.entity.Employee;

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

    Optional<Employee> findEmployeeByid(String id);

    void deleteEmployeeByid(String id);
}
