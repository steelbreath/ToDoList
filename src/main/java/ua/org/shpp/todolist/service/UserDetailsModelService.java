package ua.org.shpp.todolist.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.org.shpp.todolist.entity.UserEntity;
import ua.org.shpp.todolist.model.UserDetailsModel;
import ua.org.shpp.todolist.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsModelService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsModelService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.map(UserDetailsModel::new).orElseThrow(() -> new UsernameNotFoundException("user.invalid.username"));
    }
}
