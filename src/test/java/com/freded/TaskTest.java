package com.freded;

import com.freded.control.TaskService;
import com.freded.control.UserService;
import com.freded.entity.TaskDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class TaskTest {

    @Inject
    TaskService taskService;

    @Inject
    UserService userService;

    @Test
    @TestSecurity(user = "test-user", roles = {"user"})
    @TestTransaction
    public void testAddTask() {
        TaskDTO task = new TaskDTO();
        task.setName("Test Task");
        task.setDescription("This is a test task");

        given()
                .contentType(ContentType.JSON)
                .body(task)
                .when()
                .post("/main/tasks")
                .then()
                .statusCode(200)
                .body("createdBy", is("test-user"))
                .body("name", is("Test Task"));
    }
}
