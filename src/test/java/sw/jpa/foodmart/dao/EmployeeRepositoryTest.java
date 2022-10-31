package sw.jpa.foodmart.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import sw.jpa.foodmart.models.Department;
import sw.jpa.foodmart.models.Employee;
import sw.jpa.foodmart.models.Position;
import sw.jpa.foodmart.models.matchers.DepartmentMatcher;
import sw.jpa.foodmart.models.matchers.PositionMatcher;

@DatabaseSetup("/database/employee.xml")
class EmployeeRepositoryTest extends AbstractJpaRepositoryTest {

    // TODO - @ExpectedDatabase only detects deleted rows when transactions are disabled ??!! ; see setupDatabaseForExpectedDatabase
    // TODO - Create EmployeeMatcher

    private static final String DEGREE_EDUCATION = "degree";
    private static final String SCIENCE_DEPARTMENT = "Science";
    private static final String WEEKLY_PAY = "weekly";

    @Autowired
    private DepartmentRepository departmentDao;

    @Autowired
    private PositionRepository positionDao;

    @Autowired
    private EmployeeRepository testSubject;

    @Test
    void shouldFindAll() {
        // Given

        // When
        final List<Employee> result = testSubject.findAll();

        // Then
        assertThat(result, hasSize(3));

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
    void shouldFindByScienceDepartment() {
        // Given

        // When
        final List<Employee> result = this.testSubject.findByDepartmentDescription(SCIENCE_DEPARTMENT);

        // Then
        assertThat(result, hasSize(2));
    }

    @Test
    void shouldFindNothingWhenWrongDepartment() {
        // Given

        // When
        final List<Employee> result = this.testSubject.findByDepartmentDescription(SCIENCE_DEPARTMENT + "xxx");

        // Then
        assertThat(result, empty());
    }

    @Test
    void shouldFindByEducationLevel() {
        // Given

        // When
        final List<Employee> result = this.testSubject.findByEducationLevel(DEGREE_EDUCATION);

        // Then
        assertThat(result, hasSize(1));

        final Employee teacher = result.get(0);
        assertThat(teacher.getId(), equalTo(3));
    }

    @Test
    void shouldFindByPayType() {
        // Given

        // When
        final List<Employee> result = this.testSubject.findByPositionPayType(WEEKLY_PAY);

        // Then
        assertThat(result, hasSize(1));

        final Employee teacher = result.get(0);
        assertThat(teacher.getId(), equalTo(3));
    }

    @Test
    void shouldFindEmployeeWhenUsingAndExpression() {
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
    void shouldFindMultipleEmployeesWhenUsingOrExpression() {
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
    void shouldFindEmployeeWhenUsingLikeExpression() {
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
    void shouldFindEmployeeWhenUsingQueryByExample_AllFields() {
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
    void shouldFindEmployeeWhenUsingQueryByExample_SomeFields() {
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

    @ExpectedDatabase(value = "/database/employee-after-delete.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Transactional(value = TxType.NEVER)
    @Test
    void shouldDeleteEmployee() {
        // Given

        // When
        testSubject.deleteById(1);
    }

    @ExpectedDatabase(value = "/database/employee-after-insert.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Transactional(value = TxType.NEVER)
    @Test
    void shouldInsertEmployee_noSupervisor() {
        // Given

        // When
        this.testSubject.save(aJohnSmith());

        // Then
        this.setupDatabaseForExpectedDatabase();
    }

    @ExpectedDatabase(value = "/database/employee-after-insert-supervisor.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Transactional(value = TxType.NEVER)
    @Test
    void shouldInsertEmployee_withSupervisor() {
        // Given
        final Employee newEmployee = aJohnSmith();
        newEmployee.setSupervisor(testSubject.getById(3));

        // When
        this.testSubject.save(newEmployee);

        // Then
        this.setupDatabaseForExpectedDatabase();
    }

    private void setupDatabaseForExpectedDatabase() {
        // dbUnit @ExpectedDatabase does not handle auto generated id fields.
        // You omit the id fields on the XML and use DatabaseAssertionMode.NON_STRICT
        //
        // But, cannot match employee.id="1" like this because it has no fields

        testSubject.deleteById(1);
    }

    private Employee aJohnSmith() {
        final List<Department> departments = departmentDao.findByDescription(SCIENCE_DEPARTMENT);
        assertThat(departments, hasSize(1));

        final List<Position> positions = positionDao.findByPayType(WEEKLY_PAY);
        assertThat(positions, hasSize(1));

        final Employee newEmployee = new Employee();
        newEmployee.setFullName("John Smith");
        newEmployee.setFirstName("John");
        newEmployee.setLastName("Smith");
        newEmployee.setEducationLevel(DEGREE_EDUCATION);
        newEmployee.setDepartment(departments.get(0));
        newEmployee.setPosition(positions.get(0));

        return newEmployee;
    }

}
