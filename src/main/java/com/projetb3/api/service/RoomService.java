package com.projetb3.api.service;

import com.projetb3.api.model.Room;
import com.projetb3.api.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Optional<Room> get(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return roomRepository.findById(id);
    }

    public Iterable<Room> getAll() {
        return roomRepository.findAll();
    }

    public void delete(final int id) {
        roomRepository.deleteById(id);
    }

    public Room save(Room room) {
        Room savedRoom = roomRepository.save(room);
        return savedRoom;
    }
}
