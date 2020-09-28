package sw.jpa.foodmart.services;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import sw.jpa.foodmart.models.Employee;

@Component
public class EmployeeWriter {

    private static final List<Integer> COLUMN_POSITIONS = Arrays.asList(0, 31, 59, 72);

    private static final int LINE_WIDTH = 90;

    public void writeEmployees(PrintStream out, List<Employee> employees) {
        this.writeSeperatorLine(out, COLUMN_POSITIONS);

        System.out.printf(String.format("%30s | %25s | %10s | %s\n",
                "Name",
                "Department",
                "Pay",
                "Education"));

        this.writeSeperatorLine(out, COLUMN_POSITIONS);

        employees.forEach((e) -> this.writeEmployee(out, e));

        this.writeSeperatorLine(out, COLUMN_POSITIONS);
    }

    private void writeEmployee(PrintStream out, Employee e) {
        out.printf("%30s | %25s | %10s | %s\n",
                e.getFullName(),
                e.getDepartment().getDescription(),
                e.getPosition().getPayType(),
                e.getEducationLevel());
    }

    private void writeSeperatorLine(PrintStream out, List<Integer> dividers) {
        final StringBuffer sb = new StringBuffer();

        for (int i = 0; i < LINE_WIDTH; i++) {
            if (dividers.contains(i)) {
                sb.append('+');
            } else {
                sb.append('-');
            }
        }

        out.println(sb.toString());
    }
}
