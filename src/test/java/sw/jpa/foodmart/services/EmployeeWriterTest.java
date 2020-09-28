package sw.jpa.foodmart.services;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import sw.jpa.foodmart.models.Department;
import sw.jpa.foodmart.models.Employee;
import sw.jpa.foodmart.models.Position;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeWriterTest {

    private static final String EXPECTED_LINE_SEPERATOR = "+------------------------------+---------------------------+------------+-----------------";

    @Mock
    private Department mockDepartment;

    @Mock
    private Employee mockEmployee;

    @Mock
    private Position mockPosition;

    @Mock
    private PrintStream mockPrintStream;

    @InjectMocks
    private EmployeeWriter testSubject;

    @Captor
    private ArgumentCaptor<String> lineSeperatorCaptor;

    @Captor
    private ArgumentCaptor<String> varargsCaptor;

    @Test
    public void shouldWriteEmployees() {
        // Given
        this.setupEmployee();

        // When
        testSubject.writeEmployees(this.mockPrintStream, Arrays.asList(mockEmployee));

        // Then
        verify(mockPrintStream, times(3)).println(lineSeperatorCaptor.capture());

        assertThat(lineSeperatorCaptor.getAllValues(), contains(
                EXPECTED_LINE_SEPERATOR,
                EXPECTED_LINE_SEPERATOR,
                EXPECTED_LINE_SEPERATOR));

        verify(mockPrintStream).printf(anyString(), varargsCaptor.capture());

        assertThat(varargsCaptor.getAllValues(), contains(
                "test-full-name",
                "test-department",
                "test-pay-type",
                "test-education-level"));
    }

    private void setupEmployee() {
        when(mockDepartment.getDescription())
        .thenReturn("test-department");

        when(mockPosition.getPayType())
        .thenReturn("test-pay-type");

        when(mockEmployee.getDepartment())
        .thenReturn(mockDepartment);

        when(mockEmployee.getEducationLevel())
        .thenReturn("test-education-level");

        when(mockEmployee.getFullName())
        .thenReturn("test-full-name");

        when(mockEmployee.getPosition())
        .thenReturn(mockPosition);
    }

}
