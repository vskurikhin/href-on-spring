package su.svn.href.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import su.svn.href.models.dto.DepartmentDto;

import java.time.Duration;

@Repository
public class ReactiveDepartmentRepository implements DepartmentRepository
{
    private final WebClient webClient;

    @Autowired
    public ReactiveDepartmentRepository(WebClient.Builder wcBuilder)
    {
        this.webClient = wcBuilder
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .baseUrl("http://localhost:8002/departments")
            .build();
    }

    @Override
    public Flux<DepartmentDto> findAll()
    {
        return webClient
            .get()
            .uri("/range-full?page=2&size=10&sort=id")
            .retrieve()
            .bodyToFlux(DepartmentDto.class)
            .delayElements(Duration.ofMillis(100));
    }
}
