package sw.jpa.foodmart.models;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.domain.ExampleMatcher;

@Entity
@Table(name = "employee")
public class Employee {

    /**
     * query-by-example matcher.
     *
     * <p>
     * Ignore identifiers and null values.
     * </p>
     */
    public static final ExampleMatcher IGNORE_IDS = ExampleMatcher.matching()
            .withIgnorePaths("id", "departmentId", "positionId", "department.id", "position.id")
            .withIgnoreNullValues(); // default

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "education_level")
    private String educationLevel;

    @ManyToOne()
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne()
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id", nullable = true)
    private Employee supervisor;

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String level) {
        this.educationLevel = level;
    }

    @Nullable
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department d) {
        this.department = d;
    }

    @Nullable
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position p) {
        this.position = p;
    }

    @Nullable
    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(@Nullable Employee e) {
        this.supervisor = e;
    }

}
