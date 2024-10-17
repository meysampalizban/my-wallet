package ir.mywallet.controller;


import ir.mywallet.dto.Responses;
import ir.mywallet.model.Account;
import ir.mywallet.model.User;
import ir.mywallet.services.AccountService;
import ir.mywallet.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.*;

@WebMvcTest
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@BeforeEach
	public void setup(){
		User user=new User();
		user.setId(1);
		user.setEmail("meisam");
	}
	
	@Test
	public void saveEmployeeTest() throws Exception{
//		given(employeeService.saveEmployee(any(Employee.class))).willReturn(employee);
		

		

//		ResultActions response = mockMvc.perform(post("/api/employees")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(employee)));

//		response.andDo(print()).
//				andExpect(status().isCreated())
//				.andExpect(jsonPath("$.firstName",
//						is(employee.getFirstName())))
//				.andExpect(jsonPath("$.lastName",
//						is(employee.getLastName())))
//				.andExpect(jsonPath("$.email",
//						is(employee.getEmail())));
	}
	




}
