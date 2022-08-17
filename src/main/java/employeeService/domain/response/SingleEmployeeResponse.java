package employeeService.domain.response;

import employeeService.domain.response.common.ResponseStatus;
import employeeService.entity.Employee;
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
