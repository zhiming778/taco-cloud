package com.example.tacocloud.dao;


import com.example.tacocloud.entity.Taco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacoRepository
         extends CrudRepository<Taco, Long> {
    List<Taco> findAll();
}
