package com.returners.movies.repository;

import com.returners.movies.model.Movie;
import com.returners.movies.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById(Long id);
    User findByUserName(String username);

}
