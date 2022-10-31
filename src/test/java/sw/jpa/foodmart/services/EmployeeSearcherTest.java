package sw.jpa.foodmart.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sw.jpa.foodmart.dao.EmployeeRepository;
import sw.jpa.foodmart.models.Employee;
import sw.jpa.foodmart.services.EmployeeSearcher.Type;

@ExtendWith( MockitoExtension.class)
class EmployeeSearcherTest {

    @Mock
    private EmployeeRepository mockEmployeeRepository;

    @InjectMocks
    private EmployeeSearcher testSubject;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Test
    void shouldFindSearchType() {
        // Given

        // When
        final Type result = Type.findBySearchName("pay");

        // Then
        assertThat(result, equalTo(Type.PAY_TYPE_SEARCH));
    }

    @Test
    void shouldReturnUnknownWhenCannotFindSearchType() {
        // Given

        // When
        final Type result = Type.findBySearchName("xxxx");

        // Then
        assertThat(result, equalTo(Type.UNKNOWN));
    }

    @Test
    void shouldFindByDepartment() {
        // Given
        final List<Employee> expected = new ArrayList<>();

        when(mockEmployeeRepository.findByDepartmentDescription(anyString()))
        .thenReturn(expected);

        // When
        final List<Employee> result = testSubject.findByDepartment("test-dept");

        // Then
        assertThat(result, sameInstance(expected));

        verify(mockEmployeeRepository).findByDepartmentDescription(stringCaptor.capture());

        assertThat(stringCaptor.getValue(), equalTo("test-dept"));
    }

    @Test
    void shouldFindByType_Department() {
        // Given
        final List<Employee> expected = new ArrayList<>();

        when(mockEmployeeRepository.findByDepartmentDescription(anyString()))
        .thenReturn(expected);

        // When
        final List<Employee> result = testSubject.find(Type.DEPARTMENT_SEARCH, "test-dept");

        // Then
        assertThat(result, sameInstance(expected));

        verify(mockEmployeeRepository).findByDepartmentDescription(stringCaptor.capture());

        assertThat(stringCaptor.getValue(), equalTo("test-dept"));
    }

    @Test
    void shouldFindByEducationLevel() {
        // Given
        final List<Employee> expected = new ArrayList<>();

        when(mockEmployeeRepository.findByEducationLevel(anyString()))
        .thenReturn(expected);

        // When
        final List<Employee> result = testSubject.findByEducationLevel("test-education-level");

        // Then
        assertThat(result, sameInstance(expected));

        verify(mockEmployeeRepository).findByEducationLevel(stringCaptor.capture());

        assertThat(stringCaptor.getValue(), equalTo("test-education-level"));
    }

    @Test
    void shouldFindByType_EducationLevel() {
        // Given
        final List<Employee> expected = new ArrayList<>();

        when(mockEmployeeRepository.findByEducationLevel(anyString()))
        .thenReturn(expected);

        // When
        final List<Employee> result = testSubject.find(Type.EDUCATION_LEVEL_SEARCH, "test-education-level");

        // Then
        assertThat(result, sameInstance(expected));

        verify(mockEmployeeRepository).findByEducationLevel(stringCaptor.capture());

        assertThat(stringCaptor.getValue(), equalTo("test-education-level"));
    }

    @Test
    void shouldFindByPayType() {
        // Given
        final List<Employee> expected = new ArrayList<>();

        when(mockEmployeeRepository.findByPositionPayType(anyString()))
        .thenReturn(expected);

        // When
        final List<Employee> result = testSubject.findByPayType("test-pay-type");

        // Then
        assertThat(result, sameInstance(expected));

        verify(mockEmployeeRepository).findByPositionPayType(stringCaptor.capture());

        assertThat(stringCaptor.getValue(), equalTo("test-pay-type"));
    }

    @Test
    void shouldFindByType_PayType() {
        // Given
        final List<Employee> expected = new ArrayList<>();

        when(mockEmployeeRepository.findByPositionPayType(anyString()))
        .thenReturn(expected);

        // When
        final List<Employee> result = testSubject.find(Type.PAY_TYPE_SEARCH, "test-pay-type");

        // Then
        assertThat(result, sameInstance(expected));

        verify(mockEmployeeRepository).findByPositionPayType(stringCaptor.capture());

        assertThat(stringCaptor.getValue(), equalTo("test-pay-type"));
    }

    @Test
    void shouldFindByType_Unknown() {
        // Given

        // When
        final List<Employee> result = testSubject.find(Type.UNKNOWN, "test-pay-type");

        // Then
        assertThat(result, empty());
        verifyNoInteractions(mockEmployeeRepository);
    }
}
