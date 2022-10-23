package com.tirabassi.javamoviesbattle.controllers;

import com.tirabassi.javamoviesbattle.domain.mappers.RankMapper;
import com.tirabassi.javamoviesbattle.domain.models.RankModel;
import com.tirabassi.javamoviesbattle.domain.repositories.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

        return models
                .stream()
                .sorted(Comparator.comparing(RankModel::getRank).reversed())
                .collect(Collectors.toList());
    }
}
