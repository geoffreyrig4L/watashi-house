package com.projetb3.api.controller;

import com.projetb3.api.model.Piece;
import com.projetb3.api.service.PieceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/pieces")
public class PieceController {

    private PieceService pieceService;

    public PieceController(PieceService pieceService) {
        this.pieceService = pieceService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Piece>> getAllPieces() {
        Iterable<Piece> listePieces = pieceService.getAllPieces();
        return ResponseEntity.ok(listePieces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Piece> getPiece(@PathVariable("id") final int id) {
        Optional<Piece> piece = pieceService.getPiece(id);
        if (piece.isPresent()) {
            return ResponseEntity.ok(piece.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createPiece(@RequestBody Piece piece) {
        pieceService.savePiece(piece);
        return ResponseEntity.ok().body("La catégorie a été créée.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePiece(@PathVariable("id") final int id) {
        Optional<Piece> optPiece = pieceService.getPiece(id);
        if (optPiece.isPresent()) {
            pieceService.deletePiece(id);
            return ResponseEntity.ok().body("La catégorie a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePiece(@PathVariable("id") final int id, @RequestBody Piece modification) {
        Optional<Piece> optPiece = pieceService.getPiece(id);
        if (optPiece.isPresent()) {
            Piece current = optPiece.get();
            if (modification.getNom() != null) {
                current.setNom(modification.getNom());
            }
            pieceService.savePiece(current);
            return ResponseEntity.ok().body("La catégorie " + current.getId() + " a été modifiée.");
        }
        return ResponseEntity.notFound().build();
    }

}

