package com.vicras.service.impl;

import com.vicras.exception.RequestExecutionException;
import com.vicras.service.DestinationService;
import com.vicras.recuester.DestinationRequester;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author viktar hraskou
 */
@Controller
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRequester requester;

    @Value("${services.destination.endpoints.person}")
    private String personEndpoint;

    @Value("${services.destination.endpoints.cat}")
    private String catEndpoint;

    @Value("${services.destination.endpoints.dog}")
    private String dogEndpoint;

    @Override
    public ResponseEntity<String> getAllPersons() {
        return executeRequestForUri(personEndpoint);
    }

    @Override
    public ResponseEntity<String> getAllDogs() {
        return executeRequestForUri(dogEndpoint);
    }

    @Override
    public ResponseEntity<String> getAllCats() {
        return executeRequestForUri(catEndpoint);
    }

    private ResponseEntity<String> executeRequestForUri(String endpoint) {
        var httpResponse = requester.doSearch(endpoint);
        return getResponseEntityFromHttpResponse(httpResponse);
    }

    private ResponseEntity<String> getResponseEntityFromHttpResponse(HttpResponse httpResponse) {
        try {
            return ResponseEntity.ok(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RequestExecutionException(e.getMessage());
        }
    }
}
