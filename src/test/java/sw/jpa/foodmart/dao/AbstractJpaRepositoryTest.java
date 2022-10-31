package sw.jpa.foodmart.dao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
 * @author stewartw
 */
@EnableJpaRepositories("sw.jpa.foodmart.dao")
@EntityScan("sw.jpa.foodmart.models")
@PropertySource("classpath:jpa.properties")
@DataJpaTest
@TestExecutionListeners({
    DbUnitTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class })
@ExtendWith( SpringExtension.class)
abstract class AbstractJpaRepositoryTest {
}