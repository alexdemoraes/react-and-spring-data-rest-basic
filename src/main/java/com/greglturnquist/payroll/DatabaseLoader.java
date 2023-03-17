package com.greglturnquist.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employees;

    @Autowired
    private ManagerRepository managers;

    @Override
    public void run(String... strings) throws Exception {
        var greg = managers.save(new Manager("greg", "turnquist", "ROLE_MANAGER"));
        var oliver = managers.save(new Manager("oliver", "gierke", "ROLE_MANAGER"));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("greg", "doesn't matter",
                        AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

        employees.save(new Employee("Frodo", "Baggins", "ring bearer", greg));
        employees.save(new Employee("Bilbo", "Baggins", "burglar", greg));
        employees.save(new Employee("Gandalf", "the Grey", "wizard", greg));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("oliver", "doesn't matter",
                        AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

        employees.save(new Employee("Samwise", "Gamgee", "gardener", oliver));
        employees.save(new Employee("Merry", "Brandybuck", "pony rider", oliver));
        employees.save(new Employee("Peregrin", "Took", "pipe smoker", oliver));

        SecurityContextHolder.clearContext();
    }

}
