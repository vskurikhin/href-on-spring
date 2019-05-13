package su.svn.href.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import su.svn.href.configs.ServicesProperties;
import su.svn.href.models.dto.DepartmentDto;

import java.time.Duration;
import java.util.Properties;

import static su.svn.href.controllers.Constants.REST_API;
import static su.svn.href.controllers.Constants.REST_RANGE_FULL;
import static su.svn.href.controllers.Constants.REST_V1_DEPARTMENTS;

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
            // .baseUrl("http://localhost:8002/departments")
            .baseUrl("http://" + host + ':' + port + REST_API + REST_V1_DEPARTMENTS)
            .build();
    }

    @Override
    public Flux<DepartmentDto> findAll(int page, int size)
    {
        return webClient
            .get()
            .uri(REST_RANGE_FULL + "?page=" + page  + "&size=" + size + "&sort=id")
            .retrieve()
            .bodyToFlux(DepartmentDto.class)
            .delayElements(Duration.ofMillis(10));
    }
}
