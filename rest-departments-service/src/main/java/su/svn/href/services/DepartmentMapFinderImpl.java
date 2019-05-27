package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.regulars.Constants;
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
    private final BiFunction<Integer, Integer, Flux<Department> > defaultDepartmentFinderCase;

    private final BiFunction<Integer, Integer, Mono<List<DepartmentDto> > > defaultFullDepartmentFinderCase;

    private final Map<String, BiFunction<Integer, Integer, Flux<Department>>> caseMapDepartmentFinders;

    private final Map<String, BiFunction<Integer, Integer, Mono<List<DepartmentDto> > > > caseMapFullDepartmentFinders;

    @Autowired
    public DepartmentMapFinderImpl(DepartmentDao departmentDao, DepartmentFullDao departmentFullDao)
    {
        this.defaultDepartmentFinderCase = departmentDao::findAll;
        this.defaultFullDepartmentFinderCase = departmentFullDao::findAll;
        this.caseMapDepartmentFinders =
            new HashMap<String, BiFunction<Integer, Integer, Flux<Department>>>()
            {{
                put(Constants.ID,   departmentDao::findAllOrderById);
                put(Constants.NAME, departmentDao::findAllOrderByDepartmentName);
            }};
        this.caseMapFullDepartmentFinders =
            new HashMap<String, BiFunction<Integer, Integer, Mono<List<DepartmentDto>>>>()
            {{
                put(Constants.ID, (offset, limit) -> departmentFullDao.findAll(offset, limit, "d.department_id"));
                put(Constants.NAME, (offset, limit) -> departmentFullDao.findAll(offset, limit, "department_name"));
            }};
    }

    @Override
    public Flux<Department> findAllDepartments(int offset, int limit, String sort)
    {
        return caseMapDepartmentFinders
            .getOrDefault(sort, defaultDepartmentFinderCase)
            .apply(offset, limit);
    }

    @Override
    public Mono<List<DepartmentDto>> findAllFullDepartments(int offset, int limit, String sort)
    {
        return caseMapFullDepartmentFinders
            .getOrDefault(sort, defaultFullDepartmentFinderCase)
            .apply(offset, limit);
    }
}
