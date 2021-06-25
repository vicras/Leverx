package com.vicras.recuester;

import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.vicras.exception.RequestExecutionException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author viktar hraskou
 */
@Component
public class DestinationRequester {

    @Value("${services.destination.destinations.pets.name}")
    private String destinationService;

    public HttpResponse doSearch(String endpoint) {
        var homePetsDestination = DestinationAccessor.getDestination(destinationService);
        var httpClient = HttpClientAccessor.getHttpClient(homePetsDestination.asHttp());

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
