package sw.matilion.foodmart.dao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import sw.matilion.foodmart.models.Department;
import sw.matilion.foodmart.models.Employee;
import sw.matilion.foodmart.models.Position;
import sw.matilion.foodmart.models.matchers.DepartmentMatcher;
import sw.matilion.foodmart.models.matchers.PositionMatcher;

@DatabaseSetup("/database/employee.xml")
public class EmployeeRepositoryTest extends AbstractJpaRepositoryTest {

    // TODO - Create EmployeeMatcher

    @Autowired
    private EmployeeRepository testSubject;

    @Test
    public void shouldFindAll() {
        // Given
        final List<Employee> result = testSubject.findAll();

        // When
        assertThat(result, hasSize(3));

        // Then
        assertThat(result.get(0).getId(), equalTo(1));
        assertThat(result.get(0).getFullName(), nullValue(String.class));
        assertThat(result.get(0).getFirstName(), nullValue(String.class));
        assertThat(result.get(0).getLastName(), nullValue(String.class));
        assertThat(result.get(0).getDepartment(), nullValue(Department.class));
        assertThat(result.get(0).getPosition(), nullValue(Position.class));
        assertThat(result.get(0).getSupervisor(), nullValue(Employee.class));

        final Employee headTeacher = result.get(1);
        assertThat(headTeacher.getId(), equalTo(2));
        assertThat(headTeacher.getFullName(), equalTo("Big Head"));
        assertThat(headTeacher.getFirstName(), equalTo("Big"));
        assertThat(headTeacher.getLastName(), equalTo("Head"));
        assertThat(headTeacher.getSupervisor(), nullValue(Employee.class));

        assertThat(headTeacher.getDepartment(), DepartmentMatcher.of()
                .withDescription("Science"));

        assertThat(headTeacher.getPosition(), PositionMatcher.of()
                .withPayType("monthly")
                .withTitle("God"));

        final Employee teacher = result.get(2);
        assertThat(teacher.getId(), equalTo(3));
        assertThat(teacher.getFullName(), equalTo("John Doe"));
        assertThat(teacher.getFirstName(), equalTo("John"));
        assertThat(teacher.getLastName(), equalTo("Doe"));
        assertThat(teacher.getDepartment(), sameInstance(headTeacher.getDepartment()));
        assertThat(teacher.getSupervisor(), sameInstance(headTeacher));

        assertThat(teacher.getPosition(), PositionMatcher.of()
                .withPayType("weekly")
                .withTitle("Teacher"));
    }

}
