package sw.jpa.foodmart.models;

import java.math.BigDecimal;
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
@Table(name = "position")
public class Position {

    @Id
    @Column(name = "position_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "position_title")
    private String title;

    @Column(name = "pay_type")
    private String payType;

    @Column(name = "min_scale")
    private BigDecimal minScale;

    @Column(name = "max_scale")
    private BigDecimal maxScale;

    @Column(name = "management_role")
    private String managementRole;

    @OneToMany(mappedBy = "position")
    private List<Employee> employees;

    public int getId() {
        return id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getPayType() {
        return payType;
    }

    @Nullable
    public BigDecimal getMinScale() {
        return minScale;
    }

    @Nullable
    public BigDecimal getMaxScale() {
        return maxScale;
    }

    @Nullable
    public String getManagementRole() {
        return managementRole;
    }

    public List<Employee> getEmployees() {
        return this.employees;
    }

    public void setPayType(String type) {
        this.payType = type;
    }

}
