package com.vicras.controllers;

import com.vicras.config.SecurityConfig;
import com.vicras.config.WebConfig;
import com.vicras.entity.ApprovedStatus;
import com.vicras.entity.Comment;
import com.vicras.repository.CommentRepository;
import com.vicras.repository.GameObjectRepository;
import com.vicras.repository.UserCodeRepository;
import com.vicras.repository.UserRepository;
import org.hamcrest.Matchers;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class, SecurityConfig.class})
@WebAppConfiguration
@Sql(scripts = {"user-db-init.sql", "comment-db-init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "db-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CommentControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameObjectRepository gameObjectRepository;


    private MockMvc mockMvc;

    private static final String commentToAdd = "{\n" +
            "    \"id\": \"\",\n" +
            "    \"createdAt\": \"\",\n" +
            "    \"updatedAt\": \"\",\n" +
            "    \"mark\": \"4\",\n" +
            "    \"message\": \"test comment\",\n" +
            "    \"approvedStatus\": \"SENT\",\n" +
            "    \"destinationUserId\": \"2\"\n" +
            "}";

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void commentForUser() throws Exception {
        mockMvc.perform(get("/comment/user/{id}", 2))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void commentForUserBest() throws Exception {
        mockMvc.perform(get("/comment/user/{id}/best", 2))
                .andDo(print())
                .andExpect(jsonPath("$[0].id", Matchers.is(4)))
                .andExpect(jsonPath("$[1].id", Matchers.is(3)));
    }

    @Test
    public void commentForUserBestWithAmount() throws Exception {
        mockMvc.perform(get("/comment/user/{id}/best?amount={amount}", 2, 1))
                .andDo(print())
                .andExpect(jsonPath("$[0].id", Matchers.is(4)))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void commentForUserWorst() throws Exception {
        mockMvc.perform(get("/comment/user/{id}/worst", 2))
                .andDo(print())
                .andExpect(jsonPath("$[1].id", Matchers.is(4)))
                .andExpect(jsonPath("$[0].id", Matchers.is(3)));
    }

    @Test
    public void getCommentForNotExistingUser() throws Exception {
        mockMvc.perform(get("/comment/user/{id}", 22))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getExistingCommentWithId() throws Exception {
        mockMvc.perform(get("/comment/{id}", 2))
                .andDo(print())
                .andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.message", Matchers.is("message 2 SENT with mark 3 for user 2")));
    }

    @Test
    public void getNotExistingCommentWithId() throws Exception {
        mockMvc.perform(get("/comment/{id}", 22))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void addCommentForUser() throws Exception {
        mockMvc.perform(post("/comment/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(commentToAdd))
                .andDo(print())
                .andExpect(status().isOk());
        Optional<Comment> comment = commentRepository.findByMessage("test comment");
        Assertions.assertNotNull(comment.get());
        Assertions.assertEquals(comment.get().getMark(), 4);
    }

    @Test
    public void addCommentForNotExistingUser() throws Exception {
        mockMvc.perform(post("/comment/user/{id}", 22)
                .contentType(MediaType.APPLICATION_JSON)
                .content(commentToAdd))
                .andDo(print())
                .andExpect(status().isNotFound());
        Optional<Comment> comment = commentRepository.findByMessage("test comment");
        Assertions.assertTrue(comment.isEmpty());
    }

    @Test
    public void addCommentForAdmin() throws Exception {
        mockMvc.perform(post("/comment/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(commentToAdd))
                .andDo(print())
                .andExpect(status().isNotFound());
        Optional<Comment> comment = commentRepository.findByMessage("test comment");
        Assertions.assertTrue(comment.isEmpty());
    }
    @Test
    @WithUserDetails("admin@gmail.com")
    public void getCommentsForApprove() throws Exception {
        this.mockMvc.perform(get("/comment/for_approve"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithUserDetails("admin@gmail.com")
    public void approveObjects() throws Exception {
        this.mockMvc.perform((post("/comment/approve"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("[ 1 ]"))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertEquals(
                commentRepository.findAllByApprovedStatusIn(List.of(ApprovedStatus.APPROVED)).size(),
                3);
        Assertions.assertEquals(
                commentRepository.findAllByApprovedStatusIn(List.of(ApprovedStatus.SENT)).size(),
                1);
    }

    @Test
    @WithUserDetails("admin@gmail.com")
    public void declineObjects() throws Exception {
        this.mockMvc.perform((post("/comment/decline"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("[ 2 ]"))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertEquals(
                commentRepository.findAllByApprovedStatusIn(List.of(ApprovedStatus.DECLINE)).size(),
                3);
        Assertions.assertEquals(
                commentRepository.findAllByApprovedStatusIn(List.of(ApprovedStatus.SENT)).size(),
                1);
    }

    @Test
    @WithUserDetails("admin@gmail.com")
    public void approveAlreadyDeclined() throws Exception {
        this.mockMvc.perform((post("/comment/decline"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("[ 5, 6, 22 ]"))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertEquals(
                commentRepository.findAllByApprovedStatusIn(List.of(ApprovedStatus.DECLINE)).size(),
                2);
    }

    @Test
    public void addCommentUserGameObject() throws Exception {
        var dto = "{\n" +
                "    \"user\": {\n" +
                "        \"id\": \"\",\n" +
                "        \"createdAt\": \"\",\n" +
                "        \"updatedAt\": \"\",\n" +
                "        \"email\": \"victor.graskov5@gmail.com\",\n" +
                "        \"firstName\": \"Viktor3\",\n" +
                "        \"lastName\": \"Graskov3\",\n" +
                "        \"password\": \"admin1\",\n" +
                "        \"role\": \"TRADER\"\n" +
                "    },\n" +
                "    \"objectDTOS\": [\n" +
                "        {\n" +
                "            \"id\": \"\",\n" +
                "            \"createdAt\": \"\",\n" +
                "            \"updatedAt\": \"\",\n" +
                "            \"title\": \"New game object22\",\n" +
                "            \"description\": \"new and beautiful22\",\n" +
                "            \"approvedStatus\": \"SENT\",\n" +
                "            \"gameKeys\": [\n" +
                "                4,\n" +
                "                7\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"commentDTO\": {\n" +
                "        \"id\": \"\",\n" +
                "        \"createdAt\": \"\",\n" +
                "        \"updatedAt\": \"\",\n" +
                "        \"mark\": \"4\",\n" +
                "        \"message\": \"test comment\",\n" +
                "        \"approvedStatus\": \"SENT\",\n" +
                "        \"destinationUserId\": \"\"\n" +
                "    }\n" +
                "}";
        mockMvc.perform(post("/comment/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dto))
                .andDo(print())
                .andExpect(status().isOk());
        Optional<Comment> comment = commentRepository.findByMessage("test comment");
        Assertions.assertEquals(comment.get().getApprovedStatus(), ApprovedStatus.SENT);
        Assertions.assertNotNull(gameObjectRepository.findByTitle("New game object22"));
        Assertions.assertNotNull(userRepository.findByEmail("victor.graskov5@gmail.com"));
    }

}
