package su.svn.href.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
public class EmployeeDaoImpl implements EmployeeUpdateDao
{
    @Override
    public Mono<Integer> updateFirstName(Long id, String firstName)
    {
        return null; // TODO
    }

    @Override
    public Mono<Integer> updateLastName(Long id, String lastName)
    {
        return null; // TODO
    }

    @Override
    public Mono<Integer> updateEmail(Long id, String email)
    {
        return null; // TODO
    }

    @Override
    public Mono<Integer> updatePhoneNumber(Long id, String phoneNumber)
    {
        return null; // TODO
    }

    @Override
    public Mono<Integer> updateHireDate(Long id, Date hireDate)
    {
        return null; // TODO
    }

    @Override
    public Mono<Integer> updateJobId(Long id, String jobId)
    {
        return null; // TODO
    }

    @Override
    public Mono<Integer> updateSalary(Long id, Double salary)
    {
        return null; // TODO
    }

    @Override
    public Mono<Integer> updateCommissionPct(Long id, Double commissionPct)
    {
        return null; // TODO
    }

    @Override
    public Mono<Integer> updateManagerId(Long id, Long managerId)
    {
        return null; // TODO
    }

    @Override
    public Mono<Integer> updateDepartmentId(Long id, Long departmentId)
    {
        return null; // TODO
    }
}
