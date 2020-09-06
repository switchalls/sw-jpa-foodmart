package sw.matilion.foodmart.dao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import sw.matilion.foodmart.models.Position;

@DatabaseSetup("/database/position.xml")
public class PositionRepositoryTest extends AbstractJpaRepositoryTest {

    // TODO - Create PositionMatcher

    @Autowired
    private PositionRepository testSubject;

    @Test
    public void shouldFindAll() {
        // Given
        final List<Position> result = testSubject.findAll();

        // When
        assertThat(result, hasSize(2));

        // Then
        assertThat(result.get(0).getId(), equalTo(1));
        assertThat(result.get(0).getTitle(), nullValue(String.class));
        assertThat(result.get(0).getPayType(), nullValue(String.class));
        assertThat(result.get(0).getMinScale(), nullValue(Integer.class));
        assertThat(result.get(0).getMaxScale(), nullValue(Integer.class));
        assertThat(result.get(0).getManagementRole(), nullValue(String.class));

        assertThat(result.get(1).getId(), equalTo(2));
        assertThat(result.get(1).getTitle(), equalTo("test_title"));
        assertThat(result.get(1).getPayType(), equalTo("test_type"));
        assertThat(result.get(1).getMinScale(), equalTo(1));
        assertThat(result.get(1).getMaxScale(), equalTo(99));
        assertThat(result.get(1).getManagementRole(), equalTo("test_role"));
    }

}
