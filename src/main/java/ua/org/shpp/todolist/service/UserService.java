package ua.org.shpp.todolist.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.org.shpp.todolist.dto.TaskDTO;
import ua.org.shpp.todolist.dto.UserConciseDTO;
import ua.org.shpp.todolist.dto.UserDTO;
import ua.org.shpp.todolist.entity.UserEntity;
import ua.org.shpp.todolist.exception.UserNotFoundException;
import ua.org.shpp.todolist.repository.UserRepository;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<UserDTO> signUp(UserConciseDTO userConciseDTO){
        UserEntity userEntity = modelMapper.map(userConciseDTO, UserEntity.class);
        if(userConciseDTO.getUsername().equals("admin")&&userConciseDTO.getPassword().equals("admin")){
            userEntity.setRoles("ADMIN,USER");
        }else {
            userEntity.setRoles("USER");
        }
        UserDTO userDTO = modelMapper.map(userRepository.save(userEntity), UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    public ResponseEntity<UserDTO> getUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok(modelMapper.map(userEntity, UserDTO.class));
    }

    public void deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    public ResponseEntity<UserDTO> updateUser(Long id, UserConciseDTO userConciseDTO) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userEntity.setUsername(userConciseDTO.getUsername());
        userEntity.setPassword(userConciseDTO.getPassword());
        UserDTO userDTO = modelMapper.map(userRepository.save(userEntity), UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        Page<UserEntity> page = userRepository.findAll(pageable);
        List<UserDTO> users = page.map(userEntity -> modelMapper.map(userEntity, UserDTO.class)).getContent();
        if (users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
