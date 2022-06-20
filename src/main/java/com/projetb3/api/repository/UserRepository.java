package com.projetb3.api.repository;

import com.projetb3.api.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    @Query(value = "select * from utilisateurs u WHERE u.email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value="select * from utilisateurs u WHERE u.nom = :nom", nativeQuery = true)
    List<User> findAllByName(@Param("nom") String nom);

    @Modifying
    @Query(value = "Insert into paniers values (null, 0, :id_user)", nativeQuery = true)
    @Transactional
    void createCartToUser(int id_user);

    @Modifying
    @Query(value = "Insert into favoris values (null, :id_user)", nativeQuery = true)
    @Transactional
    void createFavoritesToUser(int id_user);
}
