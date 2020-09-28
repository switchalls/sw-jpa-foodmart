package sw.jpa.foodmart;

import java.io.PrintStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import sw.jpa.foodmart.models.Employee;
import sw.jpa.foodmart.services.EmployeeSearcher;
import sw.jpa.foodmart.services.EmployeeWriter;
import sw.jpa.foodmart.services.EmployeeSearcher.Type;

@SpringBootApplication
@ComponentScan
public class SpringBootConsoleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConsoleApplication.class, args);
    }

    @Autowired
    private EmployeeSearcher employeeSearcher;

    @Autowired
    private EmployeeWriter employeeWriter;

    @Override
    public void run(String... args) throws Exception {
        final Type searchType = getSearchType(args);

        if (searchType == Type.UNKNOWN) {
            printUsage(System.out);

        } else {
            final List<Employee> employees = this.employeeSearcher.find(searchType, args[1]);
            this.employeeWriter.writeEmployees(System.out, employees);
        }
    }

    private Type getSearchType(String[] args) {
        if (args.length == 2) {
            if (args[0].startsWith("--")) {
                return Type.findBySearchName(args[0].substring(2));
            }
        }

        return Type.UNKNOWN;
    }

    private void printUsage(PrintStream out) {
        out.println("Usage: [option] value");
        out.println("Options:");
        out.println("--pay, eg. Monthly");
        out.println("--education, eg. Graduate Degree");
        out.println("--department, eg. HQ General Management");
    }
}
