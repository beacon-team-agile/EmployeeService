package com.teamagile.employeeService.domain.response;

import com.teamagile.employeeService.domain.response.common.ResponseStatus;
import com.teamagile.employeeService.entity.Employee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SingleEmployeeResponse {
    private ResponseStatus responseStatus;

    private Employee employee;

}
