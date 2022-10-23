package com.tirabassi.javamoviesbattle.controllers;

import com.tirabassi.javamoviesbattle.domain.entities.Rank;
import com.tirabassi.javamoviesbattle.domain.mappers.RankMapper;
import com.tirabassi.javamoviesbattle.domain.models.RankModel;
import com.tirabassi.javamoviesbattle.domain.repositories.RankRepository;
import com.tirabassi.javamoviesbattle.domain.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/ranks")
public class RankController {

    @Autowired
    RankRepository rankRepository;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<RankModel> rank() {

        var ranks = rankRepository.findAll();
        var models = ranks.stream().map(rank -> RankMapper.toModel(rank))
                .collect(Collectors.toList());

        Collections.sort(models, Comparator.comparing(RankModel::getRank));

        return models;
    }
}
