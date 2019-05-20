package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import su.svn.href.models.dto.EmployeeDto;
import su.svn.href.repository.EmployeeRepository;

import static su.svn.href.controllers.Constants.REST_API;
import static su.svn.href.controllers.Constants.REST_UPDATE;
import static su.svn.href.controllers.Constants.REST_V1_EMPLOYEES;

@Controller
@EnableReactiveMethodSecurity
public class EmployeesController
{
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeesController(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping("/employees")
    public String employees()
    {
        return "employees";
    }

    @RequestMapping("/employee")
    @PreAuthorize("isAuthenticated()")
    public String employee(@RequestParam long id, final Model model)
    {
        Mono<EmployeeDto> employeeDto = employeeRepository.findById(id);
        model.addAttribute("employee", employeeDto);
        model.addAttribute("restPath",  REST_API + REST_V1_EMPLOYEES + REST_UPDATE);

        return "employee";
    }
}