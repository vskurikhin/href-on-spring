package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import su.svn.href.dao.DepartmentDao;
import su.svn.href.models.Department;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service("departmentMapUpdater")
public class DepartmentMapUpdaterImpl implements DepartmentMapUpdater
{
    private DepartmentDao departmentDao;

    @Autowired
    public DepartmentMapUpdaterImpl(DepartmentDao departmentDao)
    {
        this.departmentDao = departmentDao;
    }

    private Map<String, Function<Department, Mono<Integer> > > caseMap()
    {
        return new HashMap<String, Function<Department, Mono<Integer>>>()
        {{
            put("DEPARTMENTNAME", d -> departmentDao.updateDepartmentName(d.getId(), d.getDepartmentName()));
            put("MANAGERID",      d -> departmentDao.updateManagerId(d.getId(), d.getManagerId()));
            put("LOCATIONID",     d -> departmentDao.updateLocationId(d.getId(), d.getLocationId()));
        }};
    }

    @Override
    public Mono<Integer> updateDepartment(String field, Department department)
    {
        return caseMap().getOrDefault(field.toUpperCase(), l -> Mono.empty()).apply(department);
    }
}
