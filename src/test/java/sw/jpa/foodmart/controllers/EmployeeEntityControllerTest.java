package sw.jpa.foodmart.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import sw.jpa.foodmart.restapi.models.Employee;
import sw.jpa.foodmart.restapi.models.EmployeesPage;
import sw.jpa.foodmart.restapi.models.PageInformation;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeEntityControllerTest
{
    private static final Employee MS_BOSSY = Employee.builder()
            .fullName("Ms Bossy")
            .firstName("Hard Nosed")
            .lastName("Bossy")
            .educationLevel("Graduate Degree")
            .build();
    
    private static final PageInformation EMPLOYEES_PAGE_INFORMATION = PageInformation.builder()
            .size(20)
            .totalElements(2)
            .totalPages(1)
            .number(0)
            .build();
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldListEmployees() throws Exception
    {
        final MvcResult result = this.mockMvc
                .perform( get( "/employees" ) )
                .andExpect( status().isOk() )
                .andReturn();
        
        final EmployeesPage employees = loadJsonAs( result, EmployeesPage.class );
        assertThat( employees.get_embedded().getEmployees(), hasItem(MS_BOSSY) );
        assertThat( employees.getPage(), equalTo(EMPLOYEES_PAGE_INFORMATION) );
    }
    
    @Test
    void shouldFindEmployeeById() throws Exception
    {
        final MvcResult result = this.mockMvc
                .perform( get( "/employees/30" ) )
                .andExpect( status().isOk() )
                .andReturn();
        
        final Employee employee = loadJsonAs( result, Employee.class );
        assertThat( employee, equalTo(MS_BOSSY) );
    }
    
    @Test
    void shouldReturnNotFoundWhenEmployeeMissing() throws Exception
    {
        this.mockMvc
                .perform( get( "/employees/1" ) )
                .andExpect( status().isNotFound() )
                .andReturn();
    }
    
    private <T> T loadJsonAs( MvcResult result, Class<?> type ) throws JsonProcessingException, UnsupportedEncodingException
    {
        return (T) new ObjectMapper()
                .configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue( result.getResponse().getContentAsString(), type );
    }
}