package com.projetb3.api.repository;

import com.projetb3.api.model.CarteDePaiement;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteDePaiementRepository extends PagingAndSortingRepository<CarteDePaiement,Integer> {
        /*CrudRepository ou PagingAndSortingRepository fournit des méthodes automatiquement pour manipuler une entité comme
            | findById(id)
            | findAll()
            | deleteById(id)
            | save()

            Paging permet en + de gérer ses index = faire une pagination
    */
}