package sw.matilion.foodmart.models;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Integer minScale;

    @Column(name = "max_scale")
    private Integer maxScale;

    @Column(name = "management_role")
    private String managementRole;

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
    public Integer getMinScale() {
        return minScale;
    }

    @Nullable
    public Integer getMaxScale() {
        return maxScale;
    }

    @Nullable
    public String getManagementRole() {
        return managementRole;
    }

}
