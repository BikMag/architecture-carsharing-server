package ru.bikmag.carsharing.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testAccessWithUserRole() throws Exception {
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAccessWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isUnauthorized());
    }
}
