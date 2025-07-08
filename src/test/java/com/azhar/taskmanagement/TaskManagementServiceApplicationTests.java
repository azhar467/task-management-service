package com.azhar.taskmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class TaskManagementServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void main() {
        TaskManagementServiceApplication.main(new String[] {});
    }
}
