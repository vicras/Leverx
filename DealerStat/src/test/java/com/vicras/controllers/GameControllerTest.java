package com.vicras.controllers;

import com.vicras.config.WebConfig;
import com.vicras.repository.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
@Sql(scripts = "game-db-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "game-db-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestExecutionListeners( {
        DependencyInjectionTestExecutionListener.class,
        SqlScriptsTestExecutionListener.class
})
public class GameControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private GameRepository gameRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }


    @Test
    public void testGetGame() throws Exception {
        this.mockMvc.perform(get("/game"))
                .andDo(print())
                .andExpect(content().string(containsString("[{\"id\":1,\"title\":\"CALL OF DUTY\"}]")));
    }

    @Test
    public void addGameTest() throws Exception {
        String content = "{ \"title\": \"STALKER\" } ";
        this.mockMvc.perform((post("/game"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertNotNull(gameRepository.findByTitle("STALKER").get());
    }

}
