package com.tirabassi.javamoviesbattle.domain.webservices.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tirabassi.javamoviesbattle.domain.models.obdm.ImdbResponseModel;
import com.tirabassi.javamoviesbattle.domain.models.obdm.OmdbResponseModel;
import com.tirabassi.javamoviesbattle.domain.webservices.OmdbWebservice;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OmdbWebserviceImpl implements OmdbWebservice {

    private final HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();


    @Override
    public OmdbResponseModel searchMovies(String type, String name, Integer page) throws IOException, InterruptedException {

        var url = String.format("http://www.omdbapi.com/?apikey=598db42f&type=%s&s=%s&page=%s", type, name, page);

        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        var mapper = new ObjectMapper();
        var obdmResponse = mapper.readValue(response.body(), new TypeReference<OmdbResponseModel>() {});

        return obdmResponse;
    }

    @Override
    public ImdbResponseModel findMovieByIMDB(String imdb) throws IOException, InterruptedException {
        var url = String.format("http://www.omdbapi.com/?apikey=598db42f&plot=short&i=%s", imdb);

        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        var mapper = new ObjectMapper();
        var imdbResponse = mapper.readValue(response.body(), new TypeReference<ImdbResponseModel>() {});

        return imdbResponse;
    }
}
