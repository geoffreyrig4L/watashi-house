package com.projetb3.api.repository;

import com.projetb3.api.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    @Query(value = "select * from utilisateurs u WHERE u.email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value="select * from utilisateurs u WHERE u.nom = :nom", nativeQuery = true)
    List<User> findAllByName(@Param("nom") String nom);
}
