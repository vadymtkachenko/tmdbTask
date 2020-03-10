package com.spintech.testtask.service.impl;

import com.spintech.testtask.entity.User;
import com.spintech.testtask.entity.UserActors;
import com.spintech.testtask.entity.UserMarkedShow;
import com.spintech.testtask.entity.form.ActorForm;
import com.spintech.testtask.entity.form.MarkedShowForm;
import com.spintech.testtask.repository.UserActorsRepository;
import com.spintech.testtask.repository.UserMarkedShowRepository;
import com.spintech.testtask.repository.UserRepository;
import com.spintech.testtask.service.UserService;
import com.spintech.testtask.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserActorsRepository userActorsRepository;

    @Autowired
    private UserMarkedShowRepository userMarkedShowRepostitory;

    @Autowired
    private TmdbApi tmdbApi;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registerUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (Objects.nonNull(user)) {
            return null;
        }
        user = User.builder().email(email).password(passwordEncoder.encode(password)).build();
        return userRepository.save(user);
    }

    @Override
    public User findUser(String email, String password) {
        User foundUser = getUser(email, password);
        if (Objects.nonNull(foundUser)) {
            getFavouriteActors(foundUser);
            getMarkedShows(foundUser);
            return foundUser;
        }
        return null;
    }

    public User addUserActors(ActorForm actorForm) {
        User foundUser = getUser(actorForm.getEmail(), actorForm.getPassword());
        List<UserActors> userActorsList = new ArrayList<>();
        if (Objects.nonNull(foundUser)) {
            foundUser.setFavoriteActors(new HashSet<>());
            actorForm.getFavoriteActorsIds().forEach(
                    actorId -> {
                        String actorInfo = tmdbApi.getPersonById(actorId);
                        if (Objects.nonNull(actorInfo)) {
                            userActorsList.add(UserActors.builder().user(foundUser.getId()).actor(actorId).build());
                            foundUser.getFavoriteActors().add(actorInfo);
                        }
                    });
            userActorsRepository.saveAll(userActorsList);
            getMarkedShows(foundUser);
            return foundUser;
        }
        return null;
    }

    @Override
    public User deleteUserActors(ActorForm actorForm) {
        List<UserActors> userActorsList = new ArrayList<>();
        User foundUser = getUser(actorForm.getEmail(), actorForm.getPassword());
        if (Objects.nonNull(foundUser)) {
            actorForm.getFavoriteActorsIds().forEach(
                    actorId ->
                            userActorsList.add(UserActors.builder().user(foundUser.getId()).actor(actorId).build())
            );
            userActorsRepository.deleteAll(userActorsList);
            getFavouriteActors(foundUser);
            getMarkedShows(foundUser);
            return foundUser;
        }
        return null;
    }

    @Override
    public User markWatchedShows(MarkedShowForm markedShowForm) {
        List<UserMarkedShow> userMarkedShows = new ArrayList<>();
        User foundUser = getUser(markedShowForm.getEmail(), markedShowForm.getPassword());
        if (Objects.nonNull(foundUser)) {
            foundUser.setMarkedShows(new HashSet<>());
            markedShowForm.getWatchedShows().forEach(
                    markedShow -> {
                        String showInfo = tmdbApi.getShowById(markedShow);
                        if (Objects.nonNull(showInfo)) {
                            userMarkedShows.add(UserMarkedShow.builder().user(foundUser.getId()).show(markedShow).build());
                            foundUser.getMarkedShows().add(showInfo);
                        }
                    });
            userMarkedShowRepostitory.saveAll(userMarkedShows);
            getFavouriteActors(foundUser);
            return foundUser;
        }
        return null;
    }

    @Override
    public User unmarkWatchedShows(MarkedShowForm unmarkedShowForm) {
        List<UserMarkedShow> userMarkedShows = new ArrayList<>();
        User foundUser = getUser(unmarkedShowForm.getEmail(), unmarkedShowForm.getPassword());
        if (Objects.nonNull(foundUser)) {
            foundUser.setMarkedShows(new HashSet<>());
            unmarkedShowForm.getWatchedShows().forEach(
                    unmarkedShow ->
                            userMarkedShows.add(UserMarkedShow.builder().user(foundUser.getId()).show(unmarkedShow).build())
            );
            userMarkedShowRepostitory.deleteAll(userMarkedShows);
            getMarkedShows(foundUser);
            getFavouriteActors(foundUser);
            return foundUser;
        }
        return null;
    }

    private void getFavouriteActors(User foundUser) {
        List<UserActors> userActors = userActorsRepository.findByUser(foundUser.getId());
        foundUser.setFavoriteActors(new HashSet<>());
        userActors.forEach(actors -> {
            String actor = tmdbApi.getPersonById(actors.getActor());
            if (Objects.nonNull(actor)) {
                foundUser.getFavoriteActors().add(actor);
            }
        });
    }

    private void getMarkedShows(User foundUser) {
        List<UserMarkedShow> userMarkedShows = userMarkedShowRepostitory.findByUser(foundUser.getId());
        foundUser.setMarkedShows(new HashSet<>());
        userMarkedShows.forEach(userMarkedShow -> {
            String show = tmdbApi.getPersonById(userMarkedShow.getShow());
            if (Objects.nonNull(show)) {
                foundUser.getMarkedShows().add(show);
            }
        });
    }

    private User getUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (Objects.isNull(user) || !passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        return user;

    }
}