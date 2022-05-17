package com.returners.movies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.returners.movies.model.User;
import com.returners.movies.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTests {

    @Mock
    private UserServiceImpl mockUserServiceImpl;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvcController;


    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvcController = MockMvcBuilders.standaloneSetup(userController).build();
        mapper = new ObjectMapper();
    }

    public List<User> initializeUsers(){
        List<User> users = new ArrayList<>();
        User user = new User(1L, "paul", "test123",30,"paul@gmail.com", "Paul", "0131 496 0626");
        users.add(user);
        user = new User(2L, "jane", "test123",13,"jane@gmail.com", "Jane", "0161 496 0626");
        users.add(user);
        user = new User(3L, "bridget", "test123",18,"bridget@gmail.com", "Bridget", "0116 496 0626");
        users.add(user);
        return users;
    }

    @Test
    public void testGetAllUsersReturnsUsers() throws Exception {

        List<User> users = initializeUsers();
        when(mockUserServiceImpl.getAllUsers()).thenReturn(users);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/users/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value("Paul"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].name").value("Jane"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].name").value("Bridget"));
    }

    @Test
    public void testGetUsersBySearch() throws Exception {


        User user = new User(1L, "paul", "test123",30,"paul@gmail.com", "Paul", "0131 496 0626");

        when(mockUserServiceImpl.findUserById(user.getId())).thenReturn(user);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/users/" + user.getId()))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1L))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("Paul"));
    }

    @Test
    public void testDeleteMappingDeleteAUser() throws Exception {
        Long id = 1L;
        User user = new User(1L,"test","test123",25,"test@gmail.com","Mary","0161 496 0636");
        when(mockUserServiceImpl.deleteUserById(id)).thenReturn(true);
        ResultActions response = this.mockMvcController.perform( MockMvcRequestBuilders.delete("/users/delete/" + id));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddUser() {
        User user = getUser();


    }

    private User getUser() {
        return new User(1L,"test","test123",25,"test@gmail.com","Mary","0161 496 0636");
    }

}
