package com.spintech.testtask.repository;

import com.spintech.testtask.entity.UserActors;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserActorsRepository extends CrudRepository<UserActors, Long> {

    List<UserActors> findByUser(Long userId);
}
