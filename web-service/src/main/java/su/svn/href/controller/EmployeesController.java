package su.svn.href.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import su.svn.href.repository.EmployeeRepository;

@Controller
public class EmployeesController
{
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeesController(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping("/employees")
    public String employees(final Model model)
    {
        IReactiveDataDriverContextVariable reactiveDataDrivenMode = new ReactiveDataDriverContextVariable(
            employeeRepository.findAll(1, 10), 1
        );
        model.addAttribute("employees", reactiveDataDrivenMode);

        return "employees";
    }
}