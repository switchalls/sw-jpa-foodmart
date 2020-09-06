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

    List<Employee> findByDepartmentDescriptionAndEducationLevelAndPositionPayType(
            Optional<String> name,
            Optional<String> level,
            Optional<String> type);

    List<Employee> findByDepartmentDescriptionOrEducationLevelOrPositionPayType(
            Optional<String> name,
            Optional<String> level,
            Optional<String> type);

    List<Employee> findByDepartmentDescriptionLikeAndEducationLevelLikeAndPositionPayTypeLike(
            String name,
            String level,
            String type);

}
