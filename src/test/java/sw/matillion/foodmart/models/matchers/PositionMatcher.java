package sw.matillion.foodmart.models.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import sw.matillion.foodmart.models.Position;

public class PositionMatcher extends TypeSafeDiagnosingMatcher<Position> {

    public static PositionMatcher of() {
        return new PositionMatcher();
    }

    private String expectedPayType;

    private String expectedTitle;

    public PositionMatcher withPayType(String type) {
        this.expectedPayType = type;
        return this;
    }

    public PositionMatcher withTitle(String title) {
        this.expectedTitle = title;
        return this;
    }

    @Override
    public void describeTo(Description description) {
        // TODO - Add matcher description
    }

    @Override
    protected boolean matchesSafely(Position item, Description mismatchDescription) {
        if (expectedPayType != null && !this.expectedPayType.equals(item.getPayType())) {
            mismatchDescription.appendText("payType was");
            mismatchDescription.appendValue(item.getPayType());
            return false;
        }

        if (expectedTitle != null && !this.expectedTitle.equals(item.getTitle())) {
            mismatchDescription.appendText("title was");
            mismatchDescription.appendValue(item.getTitle());
            return false;
        }

        return true;
    }

}
