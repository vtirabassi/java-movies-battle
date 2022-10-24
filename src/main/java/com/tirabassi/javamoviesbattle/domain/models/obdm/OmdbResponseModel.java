package com.tirabassi.javamoviesbattle.domain.models.obdm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmdbResponseModel {

    @JsonProperty("Search")
    private List<SearchModel> search;

    @JsonProperty
    private String totalResults;

    @JsonProperty("Response")
    private String response;
}
