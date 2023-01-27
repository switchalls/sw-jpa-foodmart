package sw.jpa.foodmart.restapi.models;

import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized @Builder
@Getter
@EqualsAndHashCode
public class EmployeesContainer
{
    private List<Employee> employees;
}
