package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import su.svn.href.dao.EmployeeDao;
import su.svn.href.dao.EmployeeFullDao;
import su.svn.href.models.Employee;
import su.svn.href.models.dto.EmployeeDto;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Service("employeeMapFinder")
public class EmployeeMapFinderImpl implements EmployeeFinder
{
    private final EmployeeDao employeeDao;

    private final EmployeeFullDao employeeFullDao;

    private final BiFunction<Integer, Integer, Flux<Employee> > defaultEmployeeFinderCase;

    private final BiFunction<Integer, Integer, Flux<EmployeeDto> > defaultFullEmployeeFinderCase;

    @Autowired
    public EmployeeMapFinderImpl(EmployeeDao employeeDao, EmployeeFullDao employeeFullDao)
    {
        this.employeeDao = employeeDao;
        this.employeeFullDao = employeeFullDao;
        this.defaultEmployeeFinderCase = employeeDao::findAll;
        this.defaultFullEmployeeFinderCase = employeeFullDao::findAll;
    }

    private Map<String, BiFunction<Integer, Integer, Flux<Employee> > > caseMapEmployeeFinders()
    {
        return new HashMap<String, BiFunction<Integer, Integer, Flux<Employee> > >()
        {{
            put("ID",             employeeDao::findAllOrderById);
            put("FIRST_NAME",     employeeDao::findAllOrderByFirstName);
            put("LAST_NAME",      employeeDao::findAllOrderByLastName);
            put("EMAIL",          employeeDao::findAllOrderByEmail);
            put("PHONE_NUMBER",   employeeDao::findAllOrderByPhoneNumber);
            put("HIRE_DATE",      employeeDao::findAllOrderByHireDate);
            put("SALARY",         employeeDao::findAllOrderBySalary);
            put("COMMISSION_PCT", employeeDao::findAllOrderByCommissionPct);
        }};
    }

    @Override
    public Flux<Employee> findAllEmployees(int offset, int limit, String sort)
    {
        return caseMapEmployeeFinders()
            .getOrDefault(sort, defaultEmployeeFinderCase)
            .apply(offset, limit);
    }

    private Map<String, BiFunction<Integer, Integer, Flux<EmployeeDto> > > caseMapFullEmployeeFinders()
    {
        return new HashMap<String, BiFunction<Integer, Integer, Flux<EmployeeDto> > >()
        {{
            put("ID",             (offset, limit) -> employeeFullDao.findAll(offset, limit, "e.employee_id"));
            put("FIRST_NAME",     (offset, limit) -> employeeFullDao.findAll(offset, limit, "e.first_name"));
            put("LAST_NAME",      (offset, limit) -> employeeFullDao.findAll(offset, limit, "e.last_name"));
            put("EMAIL",          (offset, limit) -> employeeFullDao.findAll(offset, limit, "e.email"));
            put("PHONE_NUMBER",   (offset, limit) -> employeeFullDao.findAll(offset, limit, "e.phone_number"));
            put("HIRE_DATE",      (offset, limit) -> employeeFullDao.findAll(offset, limit, "e.hire_date"));
            put("SALARY",         (offset, limit) -> employeeFullDao.findAll(offset, limit, "e.salary"));
            put("COMMISSION_PCT", (offset, limit) -> employeeFullDao.findAll(offset, limit, "e.commission_pct"));
        }};
    }

    @Override
    public Flux<EmployeeDto> findAllFullEmployees(int offset, int limit, String sort)
    {
        return caseMapFullEmployeeFinders()
            .getOrDefault(sort, defaultFullEmployeeFinderCase)
            .apply(offset, limit);
    }
}
