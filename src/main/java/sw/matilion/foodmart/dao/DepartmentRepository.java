package sw.matilion.foodmart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sw.matilion.foodmart.models.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
