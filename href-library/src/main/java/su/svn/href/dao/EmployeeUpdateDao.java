package su.svn.href.dao;

import reactor.core.publisher.Mono;

import java.util.Date;

public interface EmployeeUpdateDao
{
    Mono<Integer> updateFirstName(Long id, String firstName);

    Mono<Integer> updateLastName(Long id, String lastName);

    Mono<Integer> updateEmail(Long id, String email);

    Mono<Integer> updatePhoneNumber(Long id, String phoneNumber);

    Mono<Integer> updateHireDate(Long id, Date hireDate);

    Mono<Integer> updateJobId(Long id, String jobId);

    Mono<Integer> updateSalary(Long id, Double salary);

    Mono<Integer> updateCommissionPct(Long id, Double commissionPct);

    Mono<Integer> updateManagerId(Long id, Long managerId);

    Mono<Integer> updateDepartmentId (Long id, Long departmentId);
}

