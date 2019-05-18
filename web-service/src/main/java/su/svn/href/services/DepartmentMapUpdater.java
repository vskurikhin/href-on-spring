package su.svn.href.services;

import su.svn.href.models.Department;
import su.svn.href.models.Location;
import su.svn.href.models.UpdateValue;

public interface DepartmentMapUpdater
{
    Department updateDepartment(UpdateValue<Long> update);
}
