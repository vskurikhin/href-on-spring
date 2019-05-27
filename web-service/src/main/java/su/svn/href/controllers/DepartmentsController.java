package su.svn.href.controllers;

import org.springframework.web.bind.annotation.RequestParam;
import su.svn.href.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import static su.svn.href.controllers.Constants.REST_API;
import static su.svn.href.controllers.Constants.REST_UPDATE;
import static su.svn.href.controllers.Constants.REST_V1_DEPARTMENTS;

@Controller
public class DepartmentsController
{
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentsController(DepartmentRepository departmentRepository)
    {
        this.departmentRepository = departmentRepository;
    }

    @RequestMapping("/departments")
    public String departments(final Model model)
    {
        return "departments";
    }

    @RequestMapping("/department")
    public String department(@RequestParam long id, final Model model)
    {
        model.addAttribute("departmentName", su.svn.href.regulars.Constants.DEPARTMENTNAME);
        model.addAttribute("managerId", su.svn.href.regulars.Constants.MANAGERID);
        model.addAttribute("locationId", su.svn.href.regulars.Constants.LOCATIONID);
        model.addAttribute("department", departmentRepository.findById(id));
        model.addAttribute("restPath",  REST_API + REST_V1_DEPARTMENTS + REST_UPDATE);

        return "department";
    }
}