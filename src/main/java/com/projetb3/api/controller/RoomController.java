package com.projetb3.api.controller;

import com.projetb3.api.model.Room;
import com.projetb3.api.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/rooms")
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
    public ResponseEntity<String> create(@RequestBody Room room) {
        roomService.save(room);
        return ResponseEntity.ok().body("La catégorie a été créée.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id) {
        Optional<Room> optRoom = roomService.get(id);
        if (optRoom.isPresent()) {
            roomService.delete(id);
            return ResponseEntity.ok().body("La catégorie a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id, @RequestBody Room modified) {
        Optional<Room> optRoom = roomService.get(id);
        if (optRoom.isPresent()) {
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

