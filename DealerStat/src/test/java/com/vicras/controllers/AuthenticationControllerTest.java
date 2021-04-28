package com.vicras.controllers;

import com.vicras.config.SecurityConfig;
import com.vicras.config.WebConfig;
import com.vicras.entity.EntityStatus;
import com.vicras.entity.Role;
import com.vicras.entity.User;
import com.vicras.repository.UserCodeRepository;
import com.vicras.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class, SecurityConfig.class})
@WebAppConfiguration
@Sql(scripts = {"user-db-init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "db-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthenticationControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCodeRepository userCodeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;



    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void correctLogin() throws Exception {
        mockMvc.perform(get("/auth/login")
                .content("{\"email\":\"admin@gmail.com\", \"password\":\"admin\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void badCredentials() throws Exception {
        mockMvc.perform(get("/auth/login")
                .content("{\"email\":\"name\", \"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void signUp() throws Exception {
        String USER_TO_ADD = "{\n" +
                "    \"id\" : \"\",\n" +
                "    \"createdAt\": \"\",\n" +
                "    \"updatedAt\": \"\",\n" +
                "    \"email\": \"victor.graskov@gmail.com\",\n" +
                "    \"firstName\" : \"Viktor\",\n" +
                "    \"lastName\": \"Graskov\",\n" +
                "    \"password\": \"admin\",\n" +
                "    \"role\": \"ADMIN\"\n" +
                "}\n" +
                "\n";
        mockMvc.perform(post("/auth/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(USER_TO_ADD))
                .andDo(print())
                .andExpect(status().isOk());


        Assertions.assertNotNull(userRepository.findByEmail("victor.graskov@gmail.com").get());
    }

    @Test
    public void signUpNotUniqueMail() throws Exception {
        String USER_TO_ADD = "{\n" +
                "    \"email\": \"admin@gmail.com\",\n" +
                "    \"firstName\" : \"Viktor\",\n" +
                "    \"lastName\": \"Graskov\",\n" +
                "    \"password\": \"admin\",\n" +
                "    \"role\": \"ADMIN\"\n" +
                "}\n" +
                "\n";
        mockMvc.perform(post("/auth/sign_up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(USER_TO_ADD))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void confirmUser() throws Exception {
        var code = "user code";
        var user = User.builder()
                .email("user")
                .password("pass")
                .firstName("first name")
                .lastName("last name")
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        userCodeRepository.save(code, user.getId());

        mockMvc.perform(get("/auth/confirm/{code}", code))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertEquals(userRepository.findByEmail("user").get().getEntityStatus(), EntityStatus.ACTIVE);
    }

    @Test
    public void confirmUserWithoutCode() throws Exception {
        var code = "user code";
        var user = User.builder()
                .email("user")
                .password("pass")
                .firstName("first name")
                .lastName("last name")
                .role(Role.ADMIN)
                .build();
        user.setEntityStatus(EntityStatus.INACTIVE);
        userRepository.save(user);

        mockMvc.perform(get("/auth/confirm/{code}", code))
                .andDo(print())
                .andExpect(status().isNotFound());
        Assertions.assertEquals(userRepository.findByEmail("user").get().getEntityStatus(), EntityStatus.INACTIVE);
    }

    @Test
    public void resetPassword() throws Exception {
        var code = "user code";
        var user = User.builder()
                .email("user")
                .password("pass")
                .firstName("first name")
                .lastName("last name")
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        userCodeRepository.save(code, user.getId());

        mockMvc.perform(post("/auth/reset_password", code)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"code\":\""+code+"\", \"password\": \"new pass\"}"))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertTrue(
        passwordEncoder.matches(
                "new pass",
                userRepository.findByEmail("user").get().getPassword()
        ));
    }

    @Test
    public void isCodeActive() throws Exception {
        var code = "code";
        userCodeRepository.save(code, 12L);
        mockMvc.perform(get("/auth/check_code?code={code}", code)
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Code is active"));
        userCodeRepository.deleteWithCode(code);
    }

    @Test
    public void isCodeInactive() throws Exception {
        var code = "code";
        mockMvc.perform(get("/auth/check_code?code={code}", code)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Code isn't active"));
    }

    //region game object
    @Test
    public void addGameObjectWithoutAuth() throws Exception {
        withoutAuth(post("/object"));
    }

    @Test
    public void updateGameObjectWithoutAuth() throws Exception {
        withoutAuth(put("/object"));
    }

    @Test
    public void deleteGameObjectWithoutAuth() throws Exception {
        withoutAuth(delete("/object/{id}", 2));
    }

    @Test
    public void getMyGameObjectWithoutAuth() throws Exception {
        withoutAuth(get("/object/my"));
    }

    @Test
    public void getGameObjectForApproveWithoutAuth() throws Exception {
        withoutAuth(get("/object/for_approve"));
    }

    @Test
    public void approveGameObjectWithoutAuth() throws Exception {
        withoutAuth(post("/object/approve"));
    }

    @Test
    public void declineGameObjectWithoutAuth() throws Exception {
        withoutAuth(post("/object/decline"));
    }

    //endregion

    //region comments

    @Test
    public void getCommentsForApproveWithoutAuth() throws Exception {
        withoutAuth(get("/comment/for_approve"));
    }

    @Test
    public void approveCommentsWithoutAuth() throws Exception {
        withoutAuth(post("/comment/approve"));
    }

    @Test
    public void declineCommentsWithoutAuth() throws Exception {
        withoutAuth(post("/comment/decline"));
    }

    //endregion


    private void withoutAuth(MockHttpServletRequestBuilder method) throws Exception {
        this.mockMvc.perform(method
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
//                .andExpect(redirectedUrl("http://localhost/login"));
    }


}
