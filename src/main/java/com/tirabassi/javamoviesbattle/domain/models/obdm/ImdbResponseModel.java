package com.tirabassi.javamoviesbattle.domain.models.obdm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class ImdbResponseModel {

    @JsonProperty("imdbRating")
    private String imdbRating;

}
