package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import su.svn.href.dao.EmployeeDao;
import su.svn.href.models.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service("employeeMapUpdater")
public class EmployeeMapUpdaterImpl implements EmployeeMapUpdater
{
    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeMapUpdaterImpl(EmployeeDao employeeDao)
    {
        this.employeeDao = employeeDao;
    }

    private Map<String, Function<Employee, Mono<Integer> > > caseMap()
    {
        return new HashMap<String, Function<Employee, Mono<Integer>>>()
        {{
            put("FIRSTNAME",     e -> employeeDao.updateFirstName(e.getId(), e.getFirstName()));
            put("LASTNAME",      e -> employeeDao.updateLastName(e.getId(), e.getLastName()));
            put("EMAIL",         e -> employeeDao.updateEmail(e.getId(), e.getEmail()));
            put("PHONENUMBER",   e -> employeeDao.updatePhoneNumber(e.getId(), e.getPhoneNumber()));
            put("HIREDATE",      e -> employeeDao.updateHireDate(e.getId(), e.getHireDate()));
            put("JOBID",         e -> employeeDao.updateJobId(e.getId(), e.getJobId()));
            put("SALARY",        e -> employeeDao.updateSalary(e.getId(), e.getSalary()));
            put("COMMISSIONPCT", e -> employeeDao.updateCommissionPct(e.getId(), e.getCommissionPct()));
            put("MANAGERID",     e -> employeeDao.updateManagerId(e.getId(), e.getManagerId()));
            put("DEPARTMENTID",  e -> employeeDao.updateDepartmentId(e.getId(), e.getDepartmentId()));
        }};
    }

    @Override
    public Mono<Integer> updateEmployee(String field, Employee employee)
    {
        return caseMap().getOrDefault(field.toUpperCase(), l -> Mono.empty()).apply(employee);
    }
}
