package com.tirabassi.javamoviesbattle.Init;

import com.tirabassi.javamoviesbattle.domain.models.obdm.ImdbResponseModel;
import com.tirabassi.javamoviesbattle.domain.models.obdm.OmdbResponseModel;
import com.tirabassi.javamoviesbattle.domain.models.obdm.SearchModel;
import java.util.Arrays;
import java.util.List;

public class WebserviceInit {

    public static OmdbResponseModel getValidOmdb(){
        var omdb = new OmdbResponseModel();
        omdb.setSearch(getSearchValid());

        return omdb;
    }

    private static List<SearchModel> getSearchValid(){

        var search = new SearchModel();
        search.setImdbID("123");

        return Arrays.asList(search);
    }

    public static ImdbResponseModel getValidImdb(){
        var omdb = new ImdbResponseModel();
        omdb.setImdbRating("7.7");

        return omdb;
    }
}
