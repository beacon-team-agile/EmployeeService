package com.teamagile.employeeService.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Slice;

import com.teamagile.employeeService.domain.response.common.ResponseStatus;
import com.teamagile.employeeService.entity.Employee;

import java.util.List;

@Getter
@Setter
@Builder
public class EmployeesResponse {
    private ResponseStatus responseStatus;

    private List<Employee> employees;
}
