package com.example.tacocloud.dao;

import com.example.tacocloud.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByName(String name);
}
