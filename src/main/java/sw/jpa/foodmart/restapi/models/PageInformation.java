package sw.jpa.foodmart.restapi.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized @Builder
@Getter
@EqualsAndHashCode
public class PageInformation
{
    private int size;
    
    private int totalElements;
    
    private int totalPages;
    
    private int number;
}