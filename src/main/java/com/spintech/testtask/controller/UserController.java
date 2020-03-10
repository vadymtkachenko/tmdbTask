package com.spintech.testtask.controller;

import com.spintech.testtask.entity.User;
import com.spintech.testtask.entity.form.ActorForm;
import com.spintech.testtask.entity.form.MarkedShowForm;
import com.spintech.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = POST)
    public ResponseEntity registerUser(@RequestParam String email,
                                       @RequestParam String password) {
        User user = userService.registerUser(email, password);
        return getResponse(user);
    }


    @PutMapping(value = "/actor", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addFavoriteActor(@RequestBody ActorForm addActorForm) {
        User user = userService.addUserActors(addActorForm);
        return getResponse(user);
    }

    @DeleteMapping(value = "/actor", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteFavoriteActor(@RequestBody ActorForm addActorForm) {
        User user = userService.deleteUserActors(addActorForm);
        return getResponse(user);
    }


    @GetMapping(value = "/{email}")
    public ResponseEntity getByEmail(@PathVariable String email,
                                  @RequestParam String password) {
        User user = userService.findUser(email, password);
        return getResponse(user);

    }

    @PutMapping(value = "/show", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity markWatchedShows(@RequestBody MarkedShowForm markedShowForm) {
        User user = userService.markWatchedShows(markedShowForm);
        return getResponse(user);
    }

    @DeleteMapping(value = "/show", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity unmarkWatchedShows(@RequestBody MarkedShowForm unmarkedShowForm) {
        User user = userService.unmarkWatchedShows(unmarkedShowForm);
        return getResponse(user);
    }

    private ResponseEntity getResponse(User user) {
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}
