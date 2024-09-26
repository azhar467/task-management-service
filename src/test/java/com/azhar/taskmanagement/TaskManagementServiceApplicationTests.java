package com.azhar.taskmanagement;

import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskManagementServiceApplicationTests {

	@Test
	void testMapper() {
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Azhar");
	}

}
