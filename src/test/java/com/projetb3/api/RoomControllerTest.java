package com.projetb3.api;

import com.projetb3.api.model.Room;
import com.projetb3.api.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RoomControllerTest implements H2TestJpaConfig {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public RoomRepository roomRepository;

    @BeforeEach
    void insertInH2(){
        saveRoomInH2("salle de bain");
        saveRoomInH2("salon");
        saveRoomInH2("chambre");
    }

    private void saveRoomInH2(String name) {
        Room room = new Room();
        room.setName(name);
        roomRepository.save(room);
    }

    @Test
    void should_get_all_rooms() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pieces"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is("salle de bain")))
                .andExpect(jsonPath("$.content[1].name", is("salon")))
                .andExpect(jsonPath("$.content[2].name", is("chambre")));
    }


    @Test
    void should_get_one_room() throws Exception{
        mockMvc.perform(get("/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",is("salon")));
    }

    @Test
    void should_not_get_one_room() throws Exception{
        mockMvc.perform(get("/rooms/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_room() throws Exception{
        mockMvc.perform(put("/rooms/2")
                        .content("{\"id\":2,\"name\":\"cuisine\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rooms/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",is("cuisine")));
    }

    @Test
    void should_not_put_one_room() throws Exception{
        mockMvc.perform(put("/rooms/50")
                        .content("{\"id\":2,\"name\":toilette\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_delete_one_room() throws Exception{
        mockMvc.perform(delete("/rooms/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_room() throws Exception{
        mockMvc.perform(delete("/rooms/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}