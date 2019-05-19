package su.svn.href.services;

import org.springframework.stereotype.Service;
import su.svn.href.models.Department;
import su.svn.href.models.UpdateValue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class DepartmentMapUpdaterImpl implements DepartmentMapUpdater
{
    private Department.Builder getBuilder(UpdateValue<Long> v)
    {
        return Department.builder().setId(v.getPk());
    }

    private Map<String, Function<UpdateValue<Long>, Department>> caseMap()
    {
        return new HashMap<String, Function<UpdateValue<Long>, Department>>()
        {{
            put("DEPARTMENTNAME", v -> getBuilder(v).setDepartmentName(v.getValue()).build());
            put("MANAGERID",      v -> getBuilder(v).setManagerId(Long.parseLong(v.getValue())).build());
            put("LOCATIONID",     v -> getBuilder(v).setLocationId(Long.parseLong(v.getValue())).build());
        }};
    }

    @Override
    public Department updateDepartment(UpdateValue<Long> update)
    {
        return caseMap().getOrDefault(update.getName().toUpperCase(), (v) -> null).apply(update);
    }
}
