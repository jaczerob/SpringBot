package com.github.jaczerob.springbot.web.services;

import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jaczerob.springbot.web.dtos.UserInfo;
import com.github.jaczerob.springbot.web.entities.SummonerEntity;
import com.github.jaczerob.springbot.web.entities.UserEntity;
import com.github.jaczerob.springbot.web.repositories.UserRepository;

import net.dv8tion.jda.api.entities.User;

@Service
public class UserService {
    @Autowired private UserRepository users;

    public UserEntity create(User user, SummonerEntity summoner) {
        if (users.existsById(user.getIdLong())) throw new EntityExistsException(String.format("User %d exists", user.getIdLong()));

        UserEntity entity = new UserEntity(user, summoner);
        return users.save(entity);
    }

    public UserEntity create(User user) {
        if (users.existsById(user.getIdLong())) throw new EntityExistsException(String.format("User %d exists", user.getIdLong()));

        UserEntity entity = new UserEntity(user);
        return users.save(entity);
    }

    public void save(UserEntity user) {
        if (!users.existsById(user.getId())) throw new EntityNotFoundException(String.format("User %d does not exist", user.getId()));

        users.save(user);
    }

    public Optional<UserEntity> get(User user) {
        return users.findById(user.getIdLong());
    }

    public Optional<UserEntity> get(UserInfo user) {
        return users.findById(user.getId());
    }

    public boolean exists(UserInfo user) {
        return users.existsById(user.getId());
    }
}
