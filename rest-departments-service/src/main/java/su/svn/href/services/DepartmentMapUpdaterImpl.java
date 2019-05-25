package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import su.svn.href.dao.DepartmentDao;
import su.svn.href.models.Department;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static su.svn.href.regulars.Constants.DEPARTMENTNAME;
import static su.svn.href.regulars.Constants.LOCATIONID;
import static su.svn.href.regulars.Constants.MANAGERID;

@Service("departmentMapUpdater")
public class DepartmentMapUpdaterImpl implements DepartmentUpdater
{
    private final DepartmentDao departmentDao;

    private final Map<String, Function<Department, Mono<Integer> > > caseMap;

    @Autowired
    public DepartmentMapUpdaterImpl(DepartmentDao departmentDao)
    {
        this.departmentDao = departmentDao;
        this.caseMap =
            new HashMap<String, Function<Department, Mono<Integer>>>()
            {{
                put(DEPARTMENTNAME, d -> departmentDao.updateDepartmentName(d.getId(), d.getDepartmentName()));
                put(MANAGERID,      d -> departmentDao.updateManagerId(d.getId(), d.getManagerId()));
                put(LOCATIONID,     d -> departmentDao.updateLocationId(d.getId(), d.getLocationId()));
            }};
    }

    @Override
    public Mono<Integer> updateDepartment(String field, Department department)
    {
        return caseMap.getOrDefault(field.toUpperCase(), l -> Mono.empty()).apply(department);
    }
}
