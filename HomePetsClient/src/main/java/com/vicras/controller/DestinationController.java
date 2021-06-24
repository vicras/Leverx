package com.vicras.controller;

import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/destination")
public class DestinationController {

    @GetMapping("/persons")
    public ResponseEntity<String> getAllPersons() throws IOException {
        var homePetsDestination = DestinationAccessor.getDestination("homePetsDestination");
        var httpClient = HttpClientAccessor.getHttpClient(homePetsDestination.asHttp());

        HttpResponse res = httpClient.execute(new HttpGet("/person"));

        return ResponseEntity.ok(EntityUtils.toString(res.getEntity(), StandardCharsets.UTF_8));
    }


}
