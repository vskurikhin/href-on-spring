package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import su.svn.href.dao.EmployeeDao;
import su.svn.href.dao.EmployeeFullDao;
import su.svn.href.models.Employee;
import su.svn.href.models.dto.EmployeeDto;

@Service("employeeCaseFinder")
public class EmployeeCaseFinderImpl implements EmployeeFinder
{
    private final EmployeeDao employeeDao;

    private final EmployeeFullDao employeeFullDao;

    @Autowired
    public EmployeeCaseFinderImpl(EmployeeDao employeeDao, EmployeeFullDao employeeFullDao)
    {
        this.employeeDao = employeeDao;
        this.employeeFullDao = employeeFullDao;
    }

    @Override
    public Flux<Employee> findAllEmployees(int offset, int limit, String sort)
    {
        switch (sort.toUpperCase()) {
            case "ID":
                return employeeDao.findAllOrderById(offset, limit);
            case "FIRST_NAME":
                return employeeDao.findAllOrderByFirstName(offset, limit);
            case "LAST_NAME":
                return employeeDao.findAllOrderByLastName(offset, limit);
            case "EMAIL":
                return employeeDao.findAllOrderByEmail(offset, limit);
            case "PHONE_NUMBER":
                return employeeDao.findAllOrderByPhoneNumber(offset, limit);
            case "HIRE_DATE":
                return employeeDao.findAllOrderByHireDate(offset, limit);
            case "SALARY":
                return employeeDao.findAllOrderBySalary(offset, limit);
            case "COMMISSION_PCT":
                return employeeDao.findAllOrderByCommissionPct(offset, limit);
            default:
                return employeeDao.findAll(offset, limit);
        }
    }

    @Override
    public Flux<EmployeeDto> findAllFullEmployees(int offset, int limit, String sort)
    {
        switch (sort.toUpperCase()) {
            case "ID":
                return employeeFullDao.findAll(offset, limit, "e.employee_id");
            case "FIRST_NAME":
                return employeeFullDao.findAll(offset, limit, "e.first_name");
            case "LAST_NAME":
                return employeeFullDao.findAll(offset, limit, "e.last_name");
            case "EMAIL":
                return employeeFullDao.findAll(offset, limit, "e.email");
            case "PHONE_NUMBER":
                return employeeFullDao.findAll(offset, limit, "e.phone_number");
            case "HIRE_DATE":
                return employeeFullDao.findAll(offset, limit, "e.hire_date");
            case "SALARY":
                return employeeFullDao.findAll(offset, limit, "e.salary");
            case "COMMISSION_PCT":
                return employeeFullDao.findAll(offset, limit, "e.commission_pct");
            default:
                return employeeFullDao.findAll(offset, limit);
        }
    }
}
