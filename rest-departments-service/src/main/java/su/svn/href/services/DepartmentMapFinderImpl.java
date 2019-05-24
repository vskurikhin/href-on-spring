package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.DepartmentDao;
import su.svn.href.dao.DepartmentFullDao;
import su.svn.href.models.Department;
import su.svn.href.models.dto.DepartmentDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@Service("departmentMapFinder")
public class DepartmentMapFinderImpl implements DepartmentFinder
{
    private final DepartmentDao departmentDao;

    private final DepartmentFullDao departmentFullDao;

    private final BiFunction<Integer, Integer, Flux<Department> > defaultDepartmentFinderCase;

    private final BiFunction<Integer, Integer, Mono<List<DepartmentDto> > > defaultFullDepartmentFinderCase;

    @Autowired
    public DepartmentMapFinderImpl(DepartmentDao departmentDao, DepartmentFullDao departmentFullDao)
    {
        this.departmentDao = departmentDao;
        this.departmentFullDao = departmentFullDao;
        this.defaultDepartmentFinderCase = departmentDao::findAll;
        this.defaultFullDepartmentFinderCase = departmentFullDao::findAll;
    }

    private Map<String, BiFunction<Integer, Integer, Flux<Department> > > caseMapDepartmentFinders()
    {
        return new HashMap<String, BiFunction<Integer, Integer, Flux<Department> > >()
        {{
            put("ID",   departmentDao::findAllOrderById);
            put("NAME", departmentDao::findAllOrderByDepartmentName);
        }};
    }

    @Override
    public Flux<Department> findAllDepartments(int offset, int limit, String sort)
    {
        return caseMapDepartmentFinders()
            .getOrDefault(sort, defaultDepartmentFinderCase)
            .apply(offset, limit);
    }

    private Map<String, BiFunction<Integer, Integer, Mono<List<DepartmentDto> > > > caseMapFullDepartmentFinders()
    {
        return new HashMap <String, BiFunction<Integer, Integer, Mono<List<DepartmentDto> > > >()
        {{
            put("ID",   (offset, limit) -> departmentFullDao.findAll(offset, limit, "d.department_id"));
            put("NAME", (offset, limit) -> departmentFullDao.findAll(offset, limit, "department_name"));
        }};
    }

    @Override
    public Mono<List<DepartmentDto>> findAllFullDepartments(int offset, int limit, String sort)
    {
        return caseMapFullDepartmentFinders()
            .getOrDefault(sort, defaultFullDepartmentFinderCase)
            .apply(offset, limit);
    }
}
