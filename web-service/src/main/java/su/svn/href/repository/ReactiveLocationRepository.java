package su.svn.href.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import su.svn.href.configs.ServicesProperties;
import su.svn.href.models.dto.LocationDto;

import java.time.Duration;
import java.util.Properties;

import static su.svn.href.controllers.Constants.*;

@Repository
public class ReactiveLocationRepository implements LocationRepository
{
    private final WebClient webClient;

    @Autowired
    public ReactiveLocationRepository(WebClient.Builder wcBuilder, ServicesProperties sp)
    {
        Properties locations = sp.getLocations();
        String host = locations.getProperty("host");
        String port = locations.getProperty("port");

        this.webClient = wcBuilder
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .baseUrl("http://" + host + ':' + port + REST_API + REST_V1_LOCATIONS)
            .build();
    }

    @Override
    public Flux<LocationDto> findAll(int page, int size)
    {
        return webClient
            .get()
            .uri(REST_RANGE_FULL + "?page=" + page  + "&size=" + size + "&sort=id")
            .retrieve()
            .bodyToFlux(LocationDto.class)
            .delayElements(Duration.ofMillis(10));
    }
}
