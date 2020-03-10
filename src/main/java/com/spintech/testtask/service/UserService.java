package com.spintech.testtask.service;

import com.spintech.testtask.entity.form.ActorForm;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.entity.form.MarkedShowForm;


public interface UserService {
    User registerUser(String email, String password);
    User findUser(String email, String password);
    User addUserActors(ActorForm actorForm);
    User deleteUserActors(ActorForm actorForm);
    User markWatchedShows(MarkedShowForm markedShowForm);
    User unmarkWatchedShows(MarkedShowForm unmarkedShowForm);
}

