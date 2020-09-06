package sw.matillion.foodmart.models.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import sw.matillion.foodmart.models.Department;

public class DepartmentMatcher extends TypeSafeDiagnosingMatcher<Department> {

    public static DepartmentMatcher of() {
        return new DepartmentMatcher();
    }

    private String expectedDescription;

    public DepartmentMatcher withDescription(String desc) {
        this.expectedDescription = desc;
        return this;
    }

    @Override
    public void describeTo(Description description) {
        // TODO - Add matcher description
    }

    @Override
    protected boolean matchesSafely(Department item, Description mismatchDescription) {
        if (expectedDescription != null && !this.expectedDescription.equals(item.getDescription())) {
            mismatchDescription.appendText("description was");
            mismatchDescription.appendValue(item.getDescription());
            return false;
        }

        return true;
    }

}
