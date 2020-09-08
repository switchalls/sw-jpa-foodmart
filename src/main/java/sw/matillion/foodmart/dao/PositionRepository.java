package sw.matillion.foodmart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sw.matillion.foodmart.models.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    List<Position> findByPayType(String type);

}
