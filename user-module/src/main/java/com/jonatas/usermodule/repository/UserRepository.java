package com.jonatas.usermodule.repository;

import com.jonatas.usermodule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
