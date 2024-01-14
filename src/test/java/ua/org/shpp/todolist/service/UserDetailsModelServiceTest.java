package ua.org.shpp.todolist.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.org.shpp.todolist.entity.UserEntity;
import ua.org.shpp.todolist.model.UserDetailsModel;
import ua.org.shpp.todolist.repository.UserRepository;

import java.util.Optional;

import static ua.org.shpp.todolist.ClassesForTest.USER_ENTITY;

@ExtendWith(MockitoExtension.class)
class UserDetailsModelServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDetailsModelService userDetailsModelService;

    @Test
    void loadUserByUsername() {
        userDetailsModelService = new UserDetailsModelService(userRepository);
        UserDetailsModel userDetailsModel = new UserDetailsModel(USER_ENTITY);
        Mockito.when(userRepository.findByUsername("bob")).thenReturn(Optional.of(USER_ENTITY))
                .thenReturn(Optional.of(USER_ENTITY))
                .thenReturn(Optional.of(USER_ENTITY))
                .thenReturn(Optional.empty());
        Assertions.assertEquals(userDetailsModel.getUsername(),
                userDetailsModelService.loadUserByUsername("bob").getUsername());
        Assertions.assertEquals(userDetailsModel.getPassword(),
                userDetailsModelService.loadUserByUsername("bob").getPassword());
        Assertions.assertEquals(userDetailsModel.getAuthorities(),
                userDetailsModelService.loadUserByUsername("bob").getAuthorities());
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userDetailsModelService.loadUserByUsername("bob"));
    }
}