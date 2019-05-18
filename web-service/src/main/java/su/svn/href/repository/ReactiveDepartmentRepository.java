package su.svn.href.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.configs.ServicesProperties;
import su.svn.href.models.Department;
import su.svn.href.models.dto.DepartmentDto;

import java.time.Duration;
import java.util.Properties;

import static su.svn.href.controllers.Constants.*;

@Repository
public class ReactiveDepartmentRepository implements DepartmentRepository
{
    private final WebClient webClient;

    @Autowired
    public ReactiveDepartmentRepository(WebClient.Builder wcBuilder, ServicesProperties sp)
    {
        Properties departments = sp.getDepartments();
        String host = departments.getProperty("host");
        String port = departments.getProperty("port");

        this.webClient = wcBuilder
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .baseUrl("http://" + host + ':' + port + REST_API + REST_V1_DEPARTMENTS)
            .build();
    }

    @Override
    public Mono<Long> count()
    {
        return webClient
            .get()
            .uri(REST_COUNT)
            .retrieve()
            .bodyToMono(Long.class);
    }

    @Override
    public Flux<Department> findAll(int page, int size)
    {
        return webClient
            .get()
            .uri(REST_RANGE + "?page=" + page  + "&size=" + size + "&sort=id")
            .retrieve()
            .bodyToFlux(Department.class)
            .delayElements(Duration.ofMillis(10));
    }

    @Override
    public Mono<DepartmentDto> findById(long id)
    {
        return webClient
            .get()
            .uri("/" + id)
            .retrieve()
            .bodyToMono(DepartmentDto.class);
    }

    @Override
    public Mono<ClientResponse> update(String field, Department department)
    {
        return webClient
            .put()
            .uri(REST_UPDATE + "?field=" + field)
            .body(BodyInserters.fromObject(department))
            .exchange();
    }
}
