package sw.jpa.foodmart.models;

import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "department_description")
    private String description;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    public int getId() {
        return this.id;
    }

    @Nullable
    public String getDescription() {
        return this.description;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

}
