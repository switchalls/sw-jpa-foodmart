package sw.matillion.foodmart.models;

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

    @Column(name = "department_id", nullable = true)
    private Integer departmentId;

    @Column(name = "position_id", nullable = true)
    private Integer positionId;

    @Column(name = "supervisor_id", nullable = true)
    private Integer supervisorId;

    @ManyToOne()
    @JoinColumn(name = "department_id", insertable = false, updatable = false, nullable = false)
    private Department department;

    @ManyToOne()
    @JoinColumn(name = "position_id", insertable = false, updatable = false, nullable = false)
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id", insertable = false, updatable = false, nullable = false)
    private Employee supervisor;

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    @Nullable
    public Department getDepartment() {
        return department;
    }

    @Nullable
    public Position getPosition() {
        return position;
    }

    @Nullable
    public Employee getSupervisor() {
        return supervisor;
    }

    public void setDepartment(@Nullable Department d) {
        if (d == null) {
            this.departmentId = 0;
        } else {
            this.departmentId = d.getId();
        }

        this.department = d;
    }

    public void setEducationLevel(String level) {
        this.educationLevel = level;
    }

    public void setPosition(@Nullable Position p) {
        if (p == null) {
            this.positionId = 0;
        } else {
            this.positionId = p.getId();
        }

        this.position = p;
    }

}
