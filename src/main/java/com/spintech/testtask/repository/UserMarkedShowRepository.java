package com.spintech.testtask.repository;

import com.spintech.testtask.entity.UserMarkedShow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserMarkedShowRepository extends CrudRepository<UserMarkedShow, Long> {
    List<UserMarkedShow> findByUser(Long userId);
}
