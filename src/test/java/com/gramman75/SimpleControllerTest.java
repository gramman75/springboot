package com.gramman75;

import org.hamcrest.Matchers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.oxm.Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;


@RunWith(SpringRunner.class)
// @WebMvcTest /* Web과 관련된 Bean만 등록을 함. @Component는 등록되지 않음. */
@SpringBootTest /*모든 Bean을 등록함. */
@AutoConfigureMockMvc /* @WebMvcTest를 사용하지 않을 경우 MockMvc등록을 위한 Annotation */ 
public class SimpleControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	Marshaller marshaller;

	@Test
	public void hello() throws Exception {
		
		Person person = new Person();
		person.setName("gramman75");
		
		this.mockMvc.perform(get("/hello").param("name", "gramman75"))
           .andDo(print())
           .andExpect(content().string("hello gramman75"));
	}
	
	@Test 
	public void helloIndex() throws Exception {
		this.mockMvc.perform(get("/mobile/index.html"))
		            .andExpect(status().isOk())
		            .andExpect(content().string(Matchers.containsString("hello mobile")));
	}
	
	@Test
	public void messageTest() throws Exception {
		this.mockMvc.perform(get("/message").content("hello"))
		            .andDo(print())
		            .andExpect(status().isOk())
		            .andExpect(content().string("hello"));
		            
	}
	
	@Test
	public void jsonMessageTest() throws Exception {
		
		Person person = new Person();
		person.setId(2019l);
		person.setName("kim moon geun");
		
		String jsonMessage = objectMapper.writeValueAsString(person);
		
		this.mockMvc.perform(get("/jsonMessage")
				            .content(jsonMessage)
				            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) /*어떤 Converter를 사용할지 결정하는 기준.*/
				            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
		            .andDo(print())
		            .andExpect(status().isOk())
		            .andExpect(content().string(jsonMessage))
				    .andExpect(jsonPath("$.id").value(2019))
				 	.andExpect(jsonPath("$.name").value("kim moon geun"));
		
	}

	@Test
	public void xmlMessageTest() throws Exception {

		Person person = new Person();
		person.setId(2019l);
		person.setName("kim moon geun");

		StringWriter stringWriter = new StringWriter();
		Result result = new StreamResult(stringWriter);
		marshaller.marshal(person, result );

		String xmlMessage = stringWriter.toString();

		this.mockMvc.perform(get("/jsonMessage")
				.content(xmlMessage)
				.contentType(MediaType.APPLICATION_XML) /*어떤 Converter를 사용할지 결정하는 기준.*/
				.accept(MediaType.APPLICATION_XHTML_XML))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(xmlMessage))
				.andExpect(xpath("person/id").string("2019"))
				.andExpect(xpath("person/name").string("kim moon geun"));

	}



}

