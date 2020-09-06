package sw.matilion.foodmart.dao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import sw.matilion.foodmart.models.Department;

@DatabaseSetup("/database/department.xml")
public class DepartmentRepositoryTest extends AbstractJpaRepositoryTest {

    @Autowired
    private DepartmentRepository testSubject;

    @Test
    public void shouldFindAll() {
        // Given
        final List<Department> result = testSubject.findAll();

        // When
        assertThat(result, hasSize(2));

        // Then
        assertThat(result.get(0).getId(), equalTo(1));
        assertThat(result.get(0).getDescription(), nullValue(String.class));

        assertThat(result.get(1).getId(), equalTo(2));
        assertThat(result.get(1).getDescription(), equalTo("test_department"));
    }

}
