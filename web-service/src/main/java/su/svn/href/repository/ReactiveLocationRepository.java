package su.svn.href.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import su.svn.href.models.dto.LocationDto;

import java.time.Duration;

@Repository
public class ReactiveLocationRepository implements LocationRepository
{
    private final WebClient webClient;

    @Autowired
    public ReactiveLocationRepository(WebClient.Builder wcBuilder)
    {
        this.webClient = wcBuilder
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .baseUrl("http://localhost:8001/locations")
            .build();
    }

    @Override
    public Flux<LocationDto> findAll()
    {
        return webClient
            .get()
            .uri("/range-full?page=1&size=10&sort=id")
            .retrieve()
            .bodyToFlux(LocationDto.class);
            // .delayElements(Duration.ofMillis(100));
    }
}
