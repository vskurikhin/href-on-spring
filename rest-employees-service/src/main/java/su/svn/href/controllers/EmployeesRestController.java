package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import su.svn.href.dao.EmployeeDao;
import su.svn.href.models.Employee;

@RestController()
@RequestMapping(value = "/employees")
public class EmployeesRestController
{
    private EmployeeDao employeeDao;

    public EmployeesRestController(EmployeeDao employeeDao)
    {
        this.employeeDao = employeeDao;
    }

    @GetMapping("/all")
    public Flux<Employee> readEmployees()
    {
        return employeeDao.findAll();
    }
}
