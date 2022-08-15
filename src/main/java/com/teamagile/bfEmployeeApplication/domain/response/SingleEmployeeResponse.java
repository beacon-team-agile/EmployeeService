package com.teamagile.bfEmployeeApplication.domain.response;

import com.teamagile.bfEmployeeApplication.domain.response.common.ResponseStatus;
import com.teamagile.bfEmployeeApplication.entity.Employee;
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
