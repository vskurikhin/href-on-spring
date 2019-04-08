package su.svn.href.controller;

import su.svn.href.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

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
    public String regions(final Model model)
    {
        IReactiveDataDriverContextVariable reactiveDataDrivenMode = new ReactiveDataDriverContextVariable(
            departmentRepository.findAll(), 1
        );
        model.addAttribute("departments", reactiveDataDrivenMode);

        return "departments";
    }
}