package ru.kyeeego.pikit.modules.auth.adapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.kyeeego.pikit.utils.UserRequests;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("tests")
@Sql(value = {"classpath:set-up-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

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

    @Test
    public void invalidPassword() throws Exception {
        mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        UserRequests.json(UserRequests.InvalidPasswordUser)
                )
                .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void badRequest() throws Exception {
        mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        UserRequests.json(UserRequests.BadRequestUser)
                )
                .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void noSuchUser() throws Exception {
        mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        UserRequests.json(UserRequests.InvalidEmailUser)
                )
                .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}