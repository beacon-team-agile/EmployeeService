package com.teamagile.bfEmployeeApplication.domain.response;

import com.teamagile.bfEmployeeApplication.domain.response.common.ResponseStatus;
import com.teamagile.bfEmployeeApplication.entity.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@Setter
@Builder
public class EmployeesResponse {
    private ResponseStatus responseStatus;

    private List<Employee> employees;
}
