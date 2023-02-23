package sw.jpa.foodmart.restapi.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized @Builder
@Getter
@EqualsAndHashCode
public class EmployeesPage
{
    private EmployeesContainer _embedded;
    
    private PageInformation page;
}