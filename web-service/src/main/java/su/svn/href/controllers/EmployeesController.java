package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import su.svn.href.repository.EmployeeRepository;

import static su.svn.href.controllers.Constants.REST_API;
import static su.svn.href.controllers.Constants.REST_UPDATE;
import static su.svn.href.controllers.Constants.REST_V1_EMPLOYEES;

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
    public String employees()
    {
        return "employees";
    }

    @RequestMapping("/employee")
    public String employee(@RequestParam long id, final Model model)
    {
        model.addAttribute("firstName", su.svn.href.regulars.Constants.FIRSTNAME);
        model.addAttribute("lastName", su.svn.href.regulars.Constants.LASTNAME);
        model.addAttribute("email", su.svn.href.regulars.Constants.EMAIL);
        model.addAttribute("phoneNumber", su.svn.href.regulars.Constants.PHONENUMBER);
        model.addAttribute("hireDate", su.svn.href.regulars.Constants.HIREDATE);
        model.addAttribute("salary", su.svn.href.regulars.Constants.SALARY);
        model.addAttribute("commissionPct", su.svn.href.regulars.Constants.COMMISSIONPCT);
        model.addAttribute("managerId", su.svn.href.regulars.Constants.MANAGERID);
        model.addAttribute("streetAddress", su.svn.href.regulars.Constants.STREETADDRESS);
        model.addAttribute("streetAddress", su.svn.href.regulars.Constants.STREETADDRESS);
        model.addAttribute("streetAddress", su.svn.href.regulars.Constants.STREETADDRESS);
        model.addAttribute("employee", employeeRepository.findById(id));
        model.addAttribute("restPath",  REST_API + REST_V1_EMPLOYEES + REST_UPDATE);

        return "employee";
    }
}