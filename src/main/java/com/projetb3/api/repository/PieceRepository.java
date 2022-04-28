package com.projetb3.api.repository;

import com.projetb3.api.model.Piece;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRepository extends CrudRepository<Piece,Integer> {
}
