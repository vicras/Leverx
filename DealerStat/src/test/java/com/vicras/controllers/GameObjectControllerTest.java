package com.vicras.controllers;

import com.vicras.config.SecurityConfig;
import com.vicras.config.WebConfig;
import com.vicras.entity.*;
import com.vicras.repository.GameObjectRepository;
import com.vicras.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class, SecurityConfig.class})
@WebAppConfiguration
@Sql(scripts = {"user-db-init.sql", "object-db-init.sql", "game-db-init.sql", "game-object-db-init.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "db-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GameObjectControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private GameObjectRepository objectRepository;
    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    private final static String GAME_OBJECT = "{\n" +
            "    \"id\": \"\",\n" +
            "    \"createdAt\": \"\",\n" +
            "    \"updatedAt\": \"\",\n" +
            "    \"title\": \"Test game object\",\n" +
            "    \"description\": \"New test game object\",\n" +
            "    \"approvedStatus\": \"SENT\",\n" +
            "    \"gameKeys\": [ 1 ]\n" +
            "}";




    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void testGetAllActiveAndApprovedGameObjects() throws Exception {
        this.mockMvc.perform(get("/object"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[1].id", is(4)));
    }

    @Test
    @WithUserDetails("trader@gmail.com")
    public void addGameObject() throws Exception {
        this.mockMvc.perform((post("/object"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(GAME_OBJECT))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertNotNull(objectRepository.findByTitle("Test game object").get());
    }

    @Test
    @WithUserDetails("trader@gmail.com")
    public void updateGameObject() throws Exception {
        String GAME_OBJECT_TO_UPDATE = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"title\": \"Game object 1(updated)\",\n" +
                "    \"description\": \"SENT game object 1 owner 2 (updated)\",\n" +
                "    \"approvedStatus\": \"SENT\",\n" +
                "    \"gameKeys\": [ 1 ]\n" +
                "}";
        this.mockMvc.perform((put("/object"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(GAME_OBJECT_TO_UPDATE))
                .andDo(print())
                .andExpect(status().isOk());
        Optional<GameObject> object = objectRepository.findByTitle("Game object 1(updated)");
        Assertions.assertNotNull(object.get());
        Set<Long> games = object.get().getGames().stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
        Assertions.assertEquals(games, Set.of(1L));
        Assertions.assertEquals(object.get().getApprovedStatus(), ApprovedStatus.SENT);
        Assertions.assertEquals(object.get().getId(), 1);
    }

    @Test
    @WithUserDetails("trader@gmail.com")
    public void updateNotExistingGameObject() throws Exception {
        String GAME_OBJECT_TO_UPDATE = "{\n" +
                "    \"id\": \"111\",\n" +
                "    \"title\": \"Game object 1(updated)\",\n" +
                "    \"description\": \"SENT game object 1 owner 2 (updated)\",\n" +
                "    \"approvedStatus\": \"SENT\",\n" +
                "    \"gameKeys\": [ 1 ]\n" +
                "}";

        this.mockMvc.perform((put("/object"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(GAME_OBJECT_TO_UPDATE))
                .andDo(print())
                .andExpect(status().isOk());
        Optional<GameObject> object = objectRepository.findByTitle("Game object 1(updated)");
        Assertions.assertNotNull(object.get());
        Set<Long> games = object.get().getGames().stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
        Assertions.assertEquals(games, Set.of(1L));
        Assertions.assertEquals(object.get().getApprovedStatus(), ApprovedStatus.SENT);
    }

    @Test
    @WithUserDetails("trader@gmail.com")
    public void deleteGameObject() throws Exception {
        this.mockMvc.perform(delete("/object/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(objectRepository.findByTitle("Game object 1").get().getEntityStatus(), EntityStatus.DELETED);
        Optional<User> byEmail = userRepository.findByEmail("trader@gmail.com");
        List<GameObject> allByOwner = objectRepository.findAllByOwner(byEmail.get());
        Assertions.assertEquals(allByOwner.size(), 7 );
        long deletedSize = allByOwner.stream()
                .filter(e -> e.getEntityStatus() == EntityStatus.DELETED)
                .count();
        Assertions.assertEquals(deletedSize, 3);
    }

    @Test
    @WithUserDetails("trader@gmail.com")
    public void deleteNotExistingGameObject() throws Exception {
        this.mockMvc.perform(delete("/object/{id}", 111))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<User> byEmail = userRepository.findByEmail("trader@gmail.com");
        List<GameObject> allByOwner = objectRepository.findAllByOwner(byEmail.get());
        Assertions.assertEquals(allByOwner.size(), 7 );
        long deletedSize = allByOwner.stream()
                .filter(e -> e.getEntityStatus() == EntityStatus.DELETED)
                .count();
        Assertions.assertEquals(deletedSize, 2);
    }

    @Test
    @WithUserDetails("inactive_trader@gmail.com")
    public void deleteNotMyGameObject() throws Exception {
        this.mockMvc.perform(delete("/object/{id}", 1))
                .andDo(print())
                .andExpect(status().isForbidden());

        Assertions.assertEquals(objectRepository.findById(1L).get().getEntityStatus(), EntityStatus.ACTIVE);
        Optional<User> byEmail = userRepository.findByEmail("inactive_trader@gmail.com");
        List<GameObject> allByOwner = objectRepository.findAllByOwner(byEmail.get());
        Assertions.assertEquals(allByOwner.size(), 1 );
        long deletedSize = allByOwner.stream()
                .filter(e -> e.getEntityStatus() == EntityStatus.DELETED)
                .count();
        Assertions.assertEquals(deletedSize, 0);
    }

    @Test
    @WithUserDetails("trader@gmail.com")
    public void getMyGameObjects() throws Exception {
        this.mockMvc.perform(get("/object/my"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)));
    }

    @Test
    @WithUserDetails("admin@gmail.com")
    public void getObjectsForApprove() throws Exception {
        this.mockMvc.perform(get("/object/for_approve"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @WithUserDetails("admin@gmail.com")
    public void approveObjects() throws Exception {
        this.mockMvc.perform((post("/object/approve"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("[ 1 , 2 ]"))
                .andDo(print())
                .andExpect(status().isOk());
        List<GameObject> approvedObj = objectRepository.findAllByApprovedStatusIn(List.of(ApprovedStatus.APPROVED));
        Assertions.assertEquals(approvedObj.size(), 5);
        Assertions.assertEquals(
                objectRepository.findAllByApprovedStatusIn(List.of(ApprovedStatus.SENT)).size(),
                1);
    }

    @Test
    @WithUserDetails("admin@gmail.com")
    public void declineObjects() throws Exception {
        this.mockMvc.perform((post("/object/decline"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("[ 1 , 2 ]"))
                .andDo(print())
                .andExpect(status().isOk());
        List<GameObject> approvedObj = objectRepository.findAllByApprovedStatusIn(List.of(ApprovedStatus.APPROVED));
        Assertions.assertEquals(approvedObj.size(), 3);
        Assertions.assertEquals(
                objectRepository.findAllByApprovedStatusIn(List.of(ApprovedStatus.SENT)).size(),
                1);
    }

}
