package sw.jpa.foodmart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sw.jpa.foodmart.models.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    List<Department> findByDescription(String desc);

}
