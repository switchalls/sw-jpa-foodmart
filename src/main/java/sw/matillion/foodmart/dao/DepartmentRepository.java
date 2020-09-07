package sw.matillion.foodmart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sw.matillion.foodmart.models.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
