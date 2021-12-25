package com.example.tacocloud.security;

import com.example.tacocloud.dao.UserRepository;
import com.example.tacocloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;


    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  repository.findUserByName(username);
        if(user == null)
            throw new UsernameNotFoundException(username+" not found.");
        return user;
    }
}
