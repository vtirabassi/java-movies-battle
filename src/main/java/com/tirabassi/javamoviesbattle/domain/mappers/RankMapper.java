package com.tirabassi.javamoviesbattle.domain.mappers;

import com.tirabassi.javamoviesbattle.domain.entities.Rank;
import com.tirabassi.javamoviesbattle.domain.models.MovieModel;
import com.tirabassi.javamoviesbattle.domain.models.RankModel;
import com.tirabassi.javamoviesbattle.domain.models.obdm.ImdbResponseModel;
import com.tirabassi.javamoviesbattle.domain.models.obdm.SearchModel;
import org.springframework.stereotype.Component;

@Component
public class RankMapper {

    public static RankModel toModel(Rank rank){

        var model = new RankModel();
        model.setUser(rank.getUser().getLogin());
        model.setRank(rank.getGamesPlayed() * rank.getAnswerCorrects());

        return model;
    }
}
