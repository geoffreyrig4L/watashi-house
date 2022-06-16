package com.projetb3.api.controller;

import com.projetb3.api.model.Room;
import com.projetb3.api.security.AuthenticationWithJWT;
import com.projetb3.api.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.projetb3.api.security.AuthenticationWithJWT.verifySenderOfRequest;

@Controller
@RequestMapping("/pieces")
public class RoomController {

    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Room>> getAll() {
        Iterable<Room> roomsList = roomService.getAll();
        return ResponseEntity.ok(roomsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> get(@PathVariable("id") final int id) {
        Optional<Room> room = roomService.get(id);
        if (room.isPresent()) {
            return ResponseEntity.ok(room.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Room room, @RequestHeader("Authentication") final String token) {
        if(verifySenderOfRequest(token, Optional.empty())){
            roomService.save(room);
            return ResponseEntity.ok().body("La pièce a été créée.");
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        Optional<Room> optRoom = roomService.get(id);
        if (optRoom.isPresent() && verifySenderOfRequest(token, Optional.empty())) {
            roomService.delete(id);
            return ResponseEntity.ok().body("La pièce a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id, @RequestBody Room modified, @RequestHeader("Authentication") final String token) {
        Optional<Room> optRoom = roomService.get(id);
        if (optRoom.isPresent() && verifySenderOfRequest(token, Optional.empty())){
            Room current = optRoom.get();
            if (modified.getName() != null) {
                current.setName(modified.getName());
            }
            roomService.save(current);
            return ResponseEntity.ok().body("La pièce " + current.getId() + " a été modifiée.");
        }
        return ResponseEntity.notFound().build();
    }

}

