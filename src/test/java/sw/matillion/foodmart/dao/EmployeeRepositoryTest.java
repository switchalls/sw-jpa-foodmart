package sw.matillion.foodmart.dao;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import sw.matillion.foodmart.models.Department;
import sw.matillion.foodmart.models.Employee;
import sw.matillion.foodmart.models.Position;
import sw.matillion.foodmart.models.matchers.DepartmentMatcher;
import sw.matillion.foodmart.models.matchers.PositionMatcher;

@DatabaseSetup("/database/employee.xml")
public class EmployeeRepositoryTest extends AbstractJpaRepositoryTest {

    // TODO - Create EmployeeMatcher

    private static final String DEGREE_EDUCATION = "degree";
    private static final String SCIENCE_DEPARTMENT = "Science";
    private static final String WEEKLY_PAY = "weekly";

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

        assertThat(headTeacher.getPosition(), PositionMatcher.of()
                .withPayType("monthly")
                .withTitle("God"));

        final Department scienceDepartment = headTeacher.getDepartment();
        assertThat(scienceDepartment, DepartmentMatcher.of()
                .withDescription(SCIENCE_DEPARTMENT));

        final Employee teacher = result.get(2);
        assertThat(teacher.getId(), equalTo(3));
        assertThat(teacher.getFullName(), equalTo("John Doe"));
        assertThat(teacher.getFirstName(), equalTo("John"));
        assertThat(teacher.getLastName(), equalTo("Doe"));
        assertThat(teacher.getDepartment(), sameInstance(scienceDepartment));
        assertThat(teacher.getSupervisor(), sameInstance(headTeacher));

        assertThat(teacher.getPosition(), PositionMatcher.of()
                .withPayType(WEEKLY_PAY)
                .withTitle("Teacher"));
    }

    @Test
    public void shouldFindByScienceDepartment() {
        // Given

        // When
        final List<Employee> result = this.testSubject.findByDepartmentDescription(SCIENCE_DEPARTMENT);

        // Then
        assertThat(result, hasSize(2));
    }

    @Test
    public void shouldFindNothingWhenWrongDepartment() {
        // Given

        // When
        final List<Employee> result = this.testSubject.findByDepartmentDescription(SCIENCE_DEPARTMENT + "xxx");

        // Then
        assertThat(result, empty());
    }

    @Test
    public void shouldFindByEducationLevel() {
        // Given

        // When
        final List<Employee> result = this.testSubject.findByEducationLevel(DEGREE_EDUCATION);

        // Then
        assertThat(result, hasSize(1));

        final Employee teacher = result.get(0);
        assertThat(teacher.getId(), equalTo(3));
    }

    @Test
    public void shouldFindByPayType() {
        // Given

        // When
        final List<Employee> result = this.testSubject.findByPositionPayType(WEEKLY_PAY);

        // Then
        assertThat(result, hasSize(1));

        final Employee teacher = result.get(0);
        assertThat(teacher.getId(), equalTo(3));
    }

    @Test
    public void shouldFindEmployeeWhenUsingAndExpression() {
        // Given

        // When
        final List<Employee> result = this.testSubject.findByDepartmentDescriptionAndEducationLevelAndPositionPayType(
                SCIENCE_DEPARTMENT,
                DEGREE_EDUCATION,
                WEEKLY_PAY);

        // Then
        assertThat(result, hasSize(1));

        final Employee teacher = result.get(0);
        assertThat(teacher.getId(), equalTo(3));
    }

    @Test
    public void shouldFindMultipleEmployeesWhenUsingOrExpression() {
        // Given

        // When
        final List<Employee> result = this.testSubject.findByDepartmentDescriptionOrEducationLevelOrPositionPayType(
                Optional.empty(),
                Optional.empty(),
                Optional.of(WEEKLY_PAY));

        // Then
        assertThat(result, hasSize(2));

        final Employee employeeWithNulls = result.get(0);
        assertThat(employeeWithNulls.getId(), equalTo(1));
        assertThat(employeeWithNulls.getDepartment(), nullValue(Department.class));
        assertThat(employeeWithNulls.getPosition(), nullValue(Position.class));

        final Employee teacher = result.get(1);
        assertThat(teacher.getId(), equalTo(3));
    }

    @Test
    public void shouldFindEmployeeWhenUsingLikeExpression() {
        // Given

        // When
        final List<Employee> result = this.testSubject.findByDepartmentDescriptionLikeAndEducationLevelLikeAndPositionPayTypeLike(
                "%",
                "%",
                WEEKLY_PAY);

        // Then
        assertThat(result, hasSize(1));

        final Employee teacher = result.get(0);
        assertThat(teacher.getId(), equalTo(3));
    }

    @Test
    public void shouldFindEmployeeWhenUsingQueryByExample_AllFields() {
        // Given
        final Department exampleDepartment = new Department();
        exampleDepartment.setDescription(SCIENCE_DEPARTMENT);

        final Position examplePosition = new Position();
        examplePosition.setPayType(WEEKLY_PAY);

        final Employee probe = new Employee();
        probe.setDepartment(exampleDepartment);
        probe.setEducationLevel(DEGREE_EDUCATION);
        probe.setPosition(examplePosition);

        // When
        final List<Employee> result = this.testSubject.findAll(Example.of(probe, Employee.IGNORE_IDS));

        // Then
        assertThat(result, hasSize(1));

        final Employee teacher = result.get(0);
        assertThat(teacher.getId(), equalTo(3));
    }

    @Test
    public void shouldFindEmployeeWhenUsingQueryByExample_SomeFields() {
        // Given
        final Position examplePosition = new Position();
        examplePosition.setPayType(WEEKLY_PAY);

        final Employee probe = new Employee();
        probe.setEducationLevel(DEGREE_EDUCATION);
        probe.setPosition(examplePosition);

        // When
        final List<Employee> result = this.testSubject.findAll(Example.of(probe, Employee.IGNORE_IDS));

        // Then
        assertThat(result, hasSize(1));

        final Employee teacher = result.get(0);
        assertThat(teacher.getId(), equalTo(3));
    }

}
