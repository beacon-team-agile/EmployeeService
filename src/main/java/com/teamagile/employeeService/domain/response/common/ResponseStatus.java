package com.teamagile.employeeService.domain.response.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseStatus {
    private boolean is_success;
    private String message;
}
