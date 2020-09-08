package sw.matillion.foodmart.dao;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;

/**
 * Basis for any test requiring JPA.
 *
 * <p>
 * Enables spring property injection ; see {@link DependencyInjectionTestExecutionListener}
 * </p>
 *
 * <p>
 * Data source automatically configured by {@code spring.datasource.*} properties in {@code jpa.properties}.
 * </p>
 *
 * <p>
 * Requires SpingBootConfiguration ; see {@link TestSpringBootConfiguration}
 * </p>
 *
 * @author stewartw
 */
@EnableJpaRepositories("sw.matillion.foodmart.dao")
@EntityScan("sw.matillion.foodmart.models")
@PropertySource("classpath:jpa.properties")
@DataJpaTest
@TestExecutionListeners({
    DbUnitTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class })
@RunWith(SpringRunner.class)
public abstract class AbstractJpaRepositoryTest {
}