package com.projetb3.api.repository;

import com.projetb3.api.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room,Integer> {
}
