package sw.jpa.foodmart.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import sw.jpa.foodmart.models.Employee;
import sw.jpa.foodmart.models.Position;

@DatabaseSetup("/database/position.xml")
public class PositionRepositoryTest extends AbstractJpaRepositoryTest {

    // TODO - Create PositionMatcher

    @Autowired
    private PositionRepository testSubject;

    @Test
    void shouldFindAll() {
        // Given

        // When
        final List<Position> result = testSubject.findAll();

        // Then
        assertThat(result, hasSize(2));

        assertThat(result.get(0).getId(), equalTo(1));
        assertThat(result.get(0).getTitle(), nullValue(String.class));
        assertThat(result.get(0).getPayType(), nullValue(String.class));
        assertThat(result.get(0).getMinScale(), nullValue(BigDecimal.class));
        assertThat(result.get(0).getMaxScale(), nullValue(BigDecimal.class));
        assertThat(result.get(0).getManagementRole(), nullValue(String.class));

        assertThat(result.get(1).getId(), equalTo(2));
        assertThat(result.get(1).getTitle(), equalTo("test_title"));
        assertThat(result.get(1).getPayType(), equalTo("test_type"));
        assertThat(result.get(1).getMinScale().doubleValue(), equalTo(1d));
        assertThat(result.get(1).getMaxScale().doubleValue(), equalTo(99d));
        assertThat(result.get(1).getManagementRole(), equalTo("test_role"));
    }

    @DatabaseSetup("/database/employee.xml")
    @Test
    void shouldListEmployees() {
        // Given
        final Position p = testSubject.getById(1);

        // When
        final List<Employee> result = p.getEmployees();

        // Then
        assertThat(result, hasSize(1));
    }

}
