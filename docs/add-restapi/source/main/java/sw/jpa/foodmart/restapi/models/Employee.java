package sw.jpa.foodmart.restapi.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Jacksonized @Builder
@Getter
@EqualsAndHashCode
@ToString
public class Employee
{
    private String fullName;
    
    private String firstName;
    
    private String lastName;
    
    private String educationLevel;
}
