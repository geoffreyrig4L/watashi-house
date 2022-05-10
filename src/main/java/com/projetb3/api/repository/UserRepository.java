package com.projetb3.api.repository;

import com.projetb3.api.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    @Query(value = "select id from utilisateurs u WHERE u.email = :email AND u.mdp = :mdp", nativeQuery = true)
    User findByLogins(@Param("email") String email, @Param("mdp") String mdp);
}
