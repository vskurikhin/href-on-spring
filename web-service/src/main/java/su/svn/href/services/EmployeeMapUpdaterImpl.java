package su.svn.href.services;

import org.springframework.stereotype.Service;
import su.svn.href.models.Employee;
import su.svn.href.models.UpdateValue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class EmployeeMapUpdaterImpl implements EmployeeMapUpdater
{
    private Employee.Builder getBuilder(UpdateValue<Long> v)
    {
        return Employee.builder().setId(v.getPk());
    }


    private Long convertToLong(String value)
    {
        return null; // TODO
    }

    private Double convertToDouble(String value)
    {
        return null; // TODO
    }

    private Date convertToDate(String value)
    {
        return null; // TODO
    }

    private Map<String, Function<UpdateValue<Long>, Employee>> caseMap()
    {
        return new HashMap<String, Function<UpdateValue<Long>, Employee>>()
        {{
            put("FIRSTNAME",     e -> getBuilder(e).setFirstName(e.getValue()).build());
            put("LASTNAME",      e -> getBuilder(e).setLastName(e.getValue()).build());
            put("EMAIL",         e -> getBuilder(e).setEmail(e.getValue()).build());
            put("PHONENUMBER",   e -> getBuilder(e).setPhoneNumber(e.getValue()).build());
            put("HIREDATE",      e -> getBuilder(e).setHireDate(convertToDate(e.getValue())).build());
            put("JOBID",         e -> getBuilder(e).setJobId(e.getValue()).build());
            put("SALARY",        e -> getBuilder(e).setSalary(convertToDouble(e.getValue())).build());
            put("COMMISSIONPCT", e -> getBuilder(e).setCommissionPct(convertToDouble(e.getValue())).build());
            put("MANAGERID",     e -> getBuilder(e).setManagerId(convertToLong(e.getValue())).build());
            put("DEPARTMENTID",  e -> getBuilder(e).setDepartmentId(convertToLong(e.getValue())).build());
        }};
    }

    @Override
    public Employee updateEmployee(UpdateValue<Long> update)
    {
        return caseMap().getOrDefault(update.getName().toUpperCase(), (v) -> null).apply(update);
    }
}
