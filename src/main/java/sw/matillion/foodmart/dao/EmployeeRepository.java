package sw.matillion.foodmart.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sw.matillion.foodmart.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByDepartmentDescription(String name);

    List<Employee> findByEducationLevel(String level);

    List<Employee> findByPositionPayType(String type);

    // when all fields
    List<Employee> findByDepartmentDescriptionAndEducationLevelAndPositionPayType(
            String name,
            String level,
            String type);

    // when any field, but Optional.empty() matches null
    List<Employee> findByDepartmentDescriptionOrEducationLevelOrPositionPayType(
            Optional<String> name,
            Optional<String> level,
            Optional<String> type);

    // use '%' when no value provided, but LIKE is slow! ; use Spring's find-by-example instead
    List<Employee> findByDepartmentDescriptionLikeAndEducationLevelLikeAndPositionPayTypeLike(
            String name,
            String level,
            String type);

}
