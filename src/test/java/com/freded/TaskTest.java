package com.freded;

import com.freded.control.service.TaskService;
import com.freded.control.service.UserService;
import com.freded.control.dto.TaskDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class TaskTest {

    static {
        // Set the default parser to JSON
        RestAssured.defaultParser = Parser.JSON;
    }

    @Inject
    TaskService taskService;

    @Inject
    UserService userService;

    private final String taskName = "Task";
    private String taskId;

    @Test
    @TestSecurity(user = "testUser", roles = {"user"})
    @TestTransaction
    public void testAddTask() {
        String tName = taskName + "-1";
        TaskDTO task1 = new TaskDTO();
        task1.setName(tName);
        task1.setDescription("This is a test task 1");


      taskId =  given()
                .contentType(ContentType.JSON)
                .body(task1)
                .when()
                .post("/main/tasks")
                .then()
                .statusCode(200)
                .body("createdBy", is("testUser"))
                .body("name", is(tName))
                .extract()
                .path("id");



    }


   /* @Test
    @TestSecurity(user = "testUser", roles = {"user"})
    @TestTransaction
    public void testUpdateTask() {
        String tName = taskName + "-updated-1";
        TaskDTO task1 = new TaskDTO();
        task1.setName(tName);
        task1.setDescription("This is a test task 1");

                given()
                .contentType(ContentType.JSON)
                .body(task1)
                .when()
                .put("/main/tasks/"+ taskId)
                .then()
                .statusCode(204)
                .body("createdBy", is("testUser"))
                .body("name", is(tName));




    }
    */




    @Test
    @TestTransaction
    @TestSecurity(user = "testAdmin", roles = {"admin"})
    public void testGetAllTask() {
        String tName1 = taskName + "-2-1";
        String tName2 = taskName + "-2-2";
        TaskDTO task1 = new TaskDTO();
        task1.setName(tName1);
        task1.setDescription("This is a test task 1");


        given()
                .contentType(ContentType.JSON)
                .body(task1)
                .when()
                .post("/main/tasks")
                .then();


        TaskDTO task2 = new TaskDTO();
        task2.setName(tName2);
        task2.setDescription("This is a test task 2");

        given()
                .contentType(ContentType.JSON)
                .body(task2)
                .when()
                .post("/main/tasks")
                .then();


        // Retrieve all tasks and verify
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/main/tasks?limit=100")
                .then()
                .statusCode(200)
                .body("size()", is(2)) // Ensure response contains exactly 2 tasks
                .body("[0].name", is(tName1)) // Validate first task
                .body("[0].description", is("This is a test task 1"))
                .body("[1].name", is(tName2)) // Validate second task
                .body("[1].description", is("This is a test task 2"));
    }
}
