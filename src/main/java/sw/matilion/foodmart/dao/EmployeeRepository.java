package sw.matilion.foodmart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sw.matilion.foodmart.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByDepartmentDescription(String desc);

    List<Employee> findByEducationLevel(String type);

    List<Employee> findByPositionPayType(String type);

}
