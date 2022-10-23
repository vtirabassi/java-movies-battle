package com.tirabassi.javamoviesbattle.domain.services.impl;

import com.tirabassi.javamoviesbattle.domain.entities.Rank;
import com.tirabassi.javamoviesbattle.domain.repositories.MovieRepository;
import com.tirabassi.javamoviesbattle.domain.repositories.RankRepository;
import com.tirabassi.javamoviesbattle.domain.repositories.UserRepository;
import com.tirabassi.javamoviesbattle.domain.services.GameService;
import com.tirabassi.javamoviesbattle.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    RankRepository rankRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public void starter(String login) {

        var user = userRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException("Not Found"));

        var rank = rankRepository.findByLogin(login)
                .orElse(new Rank());

        if (rank.isInGame())
            throw new BusinessException("Player already in game");

        rank.setUser(user);
        rank.setInGame(true);
        rankRepository.save(rank);
    }

    @Transactional
    @Override
    public void stop(String login) {
        var rank = rankRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException("Not Found"));

        if (!rank.isInGame())
            throw new BusinessException("Player is not in game");

        rank.setInGame(false);
        rankRepository.save(rank);
    }
}
