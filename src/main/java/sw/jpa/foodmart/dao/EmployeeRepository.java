package sw.jpa.foodmart.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sw.jpa.foodmart.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByDepartmentDescription(String description);

    List<Employee> findByEducationLevel(String level);

    List<Employee> findByPositionPayType(String payType);

    // when all fields
    List<Employee> findByDepartmentDescriptionAndEducationLevelAndPositionPayType(
            String description,
            String level,
            String payType);

    // when any field, but Optional.empty() matches null
    List<Employee> findByDepartmentDescriptionOrEducationLevelOrPositionPayType(
            Optional<String> description,
            Optional<String> level,
            Optional<String> payType);

    // use '%' when no value provided, but LIKE is slow! ; use Spring's find-by-example instead
    List<Employee> findByDepartmentDescriptionLikeAndEducationLevelLikeAndPositionPayTypeLike(
            String description,
            String level,
            String payType);

}
