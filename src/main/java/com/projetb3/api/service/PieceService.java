package com.projetb3.api.service;

import com.projetb3.api.model.Piece;
import com.projetb3.api.repository.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PieceService {

    @Autowired //injection automatique des données
    private PieceRepository pieceRepository;

    public Optional<Piece> getPiece(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return pieceRepository.findById(id);
    }

    public Iterable<Piece> getAllPieces() {
        return pieceRepository.findAll();
    }

    public void deletePiece(final int id) {
        pieceRepository.deleteById(id);
    }

    public Piece savePiece(Piece piece) {
        Piece savedPiece = pieceRepository.save(piece);
        return savedPiece;
    }
}
