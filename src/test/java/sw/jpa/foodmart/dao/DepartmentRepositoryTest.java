package sw.jpa.foodmart.dao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import sw.jpa.foodmart.models.Department;
import sw.jpa.foodmart.models.Employee;

@DatabaseSetup("/database/department.xml")
public class DepartmentRepositoryTest extends AbstractJpaRepositoryTest {

    @Autowired
    private DepartmentRepository testSubject;

    @Test
    public void shouldFindAll() {
        // Given

        // When
        final List<Department> result = testSubject.findAll();

        // Then
        assertThat(result, hasSize(2));

        assertThat(result.get(0).getId(), equalTo(1));
        assertThat(result.get(0).getDescription(), nullValue(String.class));

        assertThat(result.get(1).getId(), equalTo(2));
        assertThat(result.get(1).getDescription(), equalTo("test_department"));
    }

    @DatabaseSetup("/database/employee.xml")
    @Test
    public void shouldListEmployees() {
        // Given
        final Department dept = testSubject.getOne(1);

        // When
        final List<Employee> result = dept.getEmployees();

        // Then
        assertThat(result, hasSize(2));
    }

}
