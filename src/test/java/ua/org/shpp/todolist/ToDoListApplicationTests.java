package ua.org.shpp.todolist;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.org.shpp.todolist.entity.UserEntity;
import ua.org.shpp.todolist.exception.UserNotFoundException;
import ua.org.shpp.todolist.repository.TaskRepository;
import ua.org.shpp.todolist.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ToDoListApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:integration-test.properties")
class ToDoListApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

//    @BeforeEach
//    void setUp() throws Exception {
//        mvc.perform(post("/users/sign-up")
//                        .contentType("application/json")
//                        .content("""
//                                 {
//                                 "username": "bob",
//                                 "password": "bob"
//                                 }
//                                 """))
//                .andExpect(status().isOk());
//        mvc.perform(post("/users/sign-up")
//                        .contentType("application/json")
//                        .content("""
//                                 {
//                                 "username": "admin",
//                                 "password": "admin"
//                                 }
//                                 """))
//                .andExpect(status().isOk());
//    }
//
//    @AfterEach
//    void tearDown() throws Exception {
//        mvc.perform(delete("/users/bob")
//                .contentType("application/json")).andExpect(status().isOk());
//        mvc.perform(delete("/users/admin")
//                .contentType("application/json")).andExpect(status().isOk());
//    }

    @Test
    void signUpUserTest() throws Exception {
        mvc.perform(post("/users/sign-up")
                        .contentType("application/json")
                        .content("""
                                 {
                                 "username": "bob",
                                 "password": "bob"
                                 }
                                 """))
                .andExpect(status().isOk());
        mvc.perform(post("/users/sign-up")
                        .contentType("application/json")
                        .content("""
                                 {
                                 "username": "admin",
                                 "password": "admin"
                                 }
                                 """))
                .andExpect(status().isOk());
        UserEntity userEntity = userRepository.findByUsername("bob").orElseThrow(UserNotFoundException::new);
        Assertions.assertEquals(userEntity.getPassword(),"bob");
        Assertions.assertEquals(userEntity.getRoles(),"USER");
        UserEntity adminEntity = userRepository.findByUsername("admin").orElseThrow(UserNotFoundException::new);
        Assertions.assertEquals(adminEntity.getPassword(),"admin");
        Assertions.assertEquals(adminEntity.getRoles(),"ADMIN,USER");
    }

//    @WithMockUser(username = "bob", password = "bob")
//    @Test
//    void createUserTaskTest() throws Exception {
//        mvc.perform(post("/users/sign-up")
//                        .contentType("application/json")
//                        .content("""
//                                 {
//                                 "username": "bob",
//                                 "password": "bob"
//                                 }
//                                 """))
//                .andExpect(status().isOk());
//        mvc.perform(post("/users/{username}/tasks","bob")
//                        .contentType("application/json")
//                        .content("""
//                                 {
//                                "status": "PLANNED",
//                                "description": "string",
//                                "deadline": "2024-01-14T16:51:03.932Z"
//                                 }
//                                 """))
//                .andExpect(status().isOk());
//    }

}
