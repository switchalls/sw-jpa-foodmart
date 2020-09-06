package sw.matilion.foodmart;

import java.io.PrintStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sw.matilion.foodmart.dao.EmployeeRepository;

@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConsoleApplication.class, args);
    }

    @Autowired
    private EmployeeRepository employeeDao;

    @Override
    public void run(String... args) throws Exception {
        if (args.length != 2) {
            printUsage(System.out);
            return;
        }

        System.out.println("Running...");
    }

    private void printUsage(PrintStream out) {
        out.println("Usage: [option] value");
        out.println("Options:");
        out.println("--paytype, eg. Monthly");
        out.println("--education, eg. Graduate Degree");
        out.println("--department, eg. HQ General Management");
    }
}
