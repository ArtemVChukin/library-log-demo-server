package ru.alfastrah.library.log.demoserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SomeService someService;

    @Test
    void healthTest() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }

    @Test
    void stringBodyTest() throws Exception {
        String param = "param";
        String body = "body";
        String response = param + " " + body;
        mockMvc.perform(post("/string")
                .contentType(APPLICATION_JSON)
                .param("param", param)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    void holderBodyTest() throws Exception {
        String param = "param";
        Holder body = new Holder("body");
        Holder response = new Holder(param + " " + body.getValue());
        mockMvc.perform(post("/holder")
                .contentType(APPLICATION_JSON)
                .param("param", param)
                .content(mapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(response)));
    }

    @Test
    void holderServiceTest() throws Exception {
        String param = "param";
        Holder body = new Holder("body");
        Holder response = new Holder(param + " " + body.getValue());
        when(someService.transform(any(Holder.class))).thenReturn(response);
        mockMvc.perform(post("/service")
                .contentType(APPLICATION_JSON)
                .param("param", param)
                .content(mapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(response)));
    }
}
