package com.tirabassi.javamoviesbattle.Init;

import com.tirabassi.javamoviesbattle.domain.entities.Movie;
import com.tirabassi.javamoviesbattle.domain.entities.Rank;
import com.tirabassi.javamoviesbattle.domain.entities.User;

import java.util.*;

public class RepositoryInit {

    public static Optional<User> getUserValid(String login){
        var user = new User();
        user.setLogin(login);

        return Optional.of(user);
    }

    public static Optional<Rank> getRankValid(String login, boolean inGame){
        var user = new Rank();
        user.setUser(getUserValid(login).get());
        user.setInGame(inGame);

        return Optional.of(user);
    }

    public static List<Movie> getMovies(){

        var list = new ArrayList<Movie>();
        list.add(getMovie());
        list.add(getMovie());

        return list;
    }

    public static Movie getMovie(){
        var movie = new Movie();
        movie.setTitle(String.format("Movie - %s", new Date().toString()));
        movie.setYear("2022");
        movie.setRating("7.5");

        return movie;
    }
}
