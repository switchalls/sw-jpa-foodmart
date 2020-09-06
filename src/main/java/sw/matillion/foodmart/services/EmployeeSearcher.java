package sw.matillion.foodmart.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sw.matillion.foodmart.dao.EmployeeRepository;
import sw.matillion.foodmart.models.Employee;

@Component
public class EmployeeSearcher {

    public enum Type {
        DEPARTMENT_SEARCH("department"), EDUCATION_LEVEL_SEARCH("education"), PAY_TYPE_SEARCH("pay"), UNKNOWN("?");

        private final String searchName;

        Type(String searchName) {
            this.searchName = searchName;
        }

        public static Type findBySearchName(String name) {
            for (final Type t : Type.values()) {
                if (t.searchName.equals(name)) {
                    return t;
                }
            }

            return Type.UNKNOWN;
        }
    }

    private final EmployeeRepository employeeDao;

    @Autowired
    public EmployeeSearcher(EmployeeRepository employeeDao) {
        this.employeeDao = employeeDao;
    }

    public List<Employee> find(Type searchType, String value) {
        final List<Employee> employees;

        switch (searchType) {
            case DEPARTMENT_SEARCH:
                employees = this.findByDepartment(value);
                break;

            case EDUCATION_LEVEL_SEARCH:
                employees = this.findByEducationLevel(value);
                break;

            case PAY_TYPE_SEARCH:
                employees = this.findByPayType(value);
                break;

            default:
                employees = Collections.emptyList();
        }

        return employees;
    }

    public List<Employee> findByDepartment(String desc) {
        return this.employeeDao.findByDepartmentDescription(desc);
    }

    public List<Employee> findByPayType(String type) {
        return this.employeeDao.findByPositionPayType(type);
    }

    public List<Employee> findByEducationLevel(String level) {
        return this.employeeDao.findByEducationLevel(level);
    }

}
