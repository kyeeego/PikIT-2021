package ru.kyeeego.pikit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.kyeeego.pikit.modules.auth.adapter.AuthController;
import ru.kyeeego.pikit.utils.UserRequests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// TODO: declare MyUserDetailsService somehow or delete all the tests whatsoever
@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
@TestPropertySource("/test.yml")
@Sql(value = {"set-up-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"clean-up.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthenticationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void correctCredentials() throws Exception {
        mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        UserRequests.json(UserRequests.ValidUser)
                )
                .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
