package com.example.my_store__pet_project.repository;

import com.example.my_store__pet_project.enums.UserRole;
import com.example.my_store__pet_project.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {

    Optional<Users> findUsersByName(String name);

    boolean existsUsersByName(String name);

    Optional<List<Users>> findAllByRole(UserRole role);
}
