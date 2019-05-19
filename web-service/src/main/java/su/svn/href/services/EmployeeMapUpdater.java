package su.svn.href.services;

import su.svn.href.models.Employee;
import su.svn.href.models.UpdateValue;

public interface EmployeeMapUpdater
{
    Employee updateEmployee(UpdateValue<Long> update);
}
