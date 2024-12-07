package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.config.jwtService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class SpringSecurityNewApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private jwtService jwts;
	
	
	@Test
	@Order(1)
	@DisplayName("Creating Table")
	public void testCreateTable() throws Exception
	{
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
												.post("/auths/add")
												.contentType(MediaType.APPLICATION_JSON)
												.content("{\r\n"
														+ "    \"name\" : \"qqq\",\r\n"
														+ "    \"email\" : \"qqq@gmail.com\",\r\n"
														+ "    \"roles\" : \"ADMIN\",\r\n"
														+ "    \"passwords\" : \"123\"\r\n"
														+ "}");
		
		MvcResult result = mockMvc.perform(builder).andReturn();
		
		MockHttpServletResponse resp = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), resp.getStatus());
		assertNotNull(resp.getContentAsString());
		
	}
	
	@Test
	@Order(2)
	@DisplayName("Login")
	public void testLogin() throws Exception
	{
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
												.post("/auths/logins")
												.contentType(MediaType.APPLICATION_JSON)
												.content("{\r\n"
														+ "    \"userName\" : \"qqq\",\r\n"
														+ "    \"password\" : \"123\"\r\n"
														+ "}");
		
		MvcResult result = mockMvc.perform(builder).andReturn();
		
		MockHttpServletResponse resp = result.getResponse();
		System.out.println(resp.getContentAsString());
		assertEquals(HttpStatus.OK.value(), resp.getStatus());
		assertNotNull(resp.getContentAsString());
//		if(!resp.getContentAsString().contains("Table created successfully!"))
//		{
//			fail("May not be created");
//		}
		
	}
	
	@Test
	@Order(3)
	@DisplayName("Getall")
	public void testGetall() throws Exception
	{
		
		String token = jwts.generateToken("qqq");
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
												.get("/auths/getall")
												.header("Authorization", "Bearer "+token)
												.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(builder).andReturn();
		
		MockHttpServletResponse resp = result.getResponse();
		System.out.println(resp.getContentAsString());
		assertEquals(HttpStatus.OK.value(), resp.getStatus());
		assertNotNull(resp.getContentAsString());
//		if(!resp.getContentAsString().contains("Table created successfully!"))
//		{
//			fail("May not be created");
//		}
		
	}

}
