package edu.fra.uas;

import static org.mockito.Mockito.when;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.fra.uas.message.model.Message;
import edu.fra.uas.message.service.MessageService;

@SpringBootTest
public class HttpMockBeanTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@MockBean
	private MessageService messageServiceMock;
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                       .build();
		when(messageServiceMock.listAllMessagesFromTo("harry", "otto"))
             .thenReturn(List.of(new Message(), new Message()));
	}
	
	@Test
	public void getMessages() throws Exception {
		mockMvc.perform(
					get("/messages")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.param("from", "bob")
					.param("to", "donald")
				)
		.andExpect(status().isOk());
	}
	
	@Test
	public void newMessage() throws Exception {
		mockMvc.perform(
					post("/add")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.param("from", "bob")
					.param("to", "donald")
					.param("content", "payload")
				)
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("messages?from=bob&to=donald"));
	}
	
	@Test
	public void getMessagesInMoreDetails() throws Exception {
		mockMvc.perform(
				get("/messages")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.param("from", "harry")
				.param("to", "otto")
			)
		.andExpect(status().isOk())
		.andExpect(view().name("messaging"))
		.andExpect(model().attribute("listAllMessages", hasSize(2)))
		.andExpect(model().attribute("toUser", "otto"))
		.andDo(print());
	}
	
}
