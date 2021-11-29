package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    /**
     * Check if the username is not taken
     * @param username username
     * @return true if it is available and false otherwise
     */
    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    /**
     * Register a new user
     * @param user user
     * @return UserId of the newly created user
     */
    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    /**
     * Find user by username
     * @param username username
     * @return User
     */
    public User getUser(String username) {
        return userMapper.getUser(username);
    }
}
