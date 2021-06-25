package com.vicras.recuester;

import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.vicras.exception.RequestExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author viktar hraskou
 */
@Slf4j
@Component
public class DestinationRequester {

    @Value("${services.destination.destinations.pets.name}")
    private String destinationServiceName;

    private Destination destinationService;

    @PostConstruct
    private void getDestination(){
        log.info("received destination service name: " + destinationServiceName);
        destinationService = DestinationAccessor.getDestination(destinationServiceName);
        log.info("created Destination: " + destinationService);
    }

    public HttpResponse doSearch(String endpoint) {
        var httpClient = HttpClientAccessor.getHttpClient(destinationService.asHttp());
        return executeRequest(httpClient, endpoint);
    }

    private HttpResponse executeRequest(HttpClient httpClient, String endpoint) {
        try {
            return httpClient.execute(new HttpGet(endpoint));
        } catch (IOException e) {
            throw new RequestExecutionException(e.getMessage());
        }
    }


}
