package com.eduardo.hotelaria.service;

import com.eduardo.hotelaria.dto.UserDTO;
import com.eduardo.hotelaria.entity.User;
import com.eduardo.hotelaria.exception.NotFoundException;
import com.eduardo.hotelaria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            return users.stream()
                    .map(User::toDTO)
                    .collect(Collectors.toList());
        } else {
            throw new NotFoundException("No users found");
        }
    }
    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(User::toDTO);
    }
    @Transactional(rollbackFor = Exception.class)
    public UserDTO createNewUser(UserDTO dto){
        User user = new User(dto.getName(), dto.getPassword(), dto.getRoles());
        User saveUser = userRepository.save(user);
        return saveUser.toDTO();
    }
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException("User with id: " + id + " does not exist");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public UserDTO updateUser(Long id, UserDTO dto) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()){
            User existingUser = optionalUser.get();
            existingUser.setName(dto.getName());
            existingUser.setPassword(dto.getPassword());
            existingUser.setRoles(dto.getRoles());

            User newUser = userRepository.save(existingUser);
            return newUser.toDTO();
        } else {
            throw new NotFoundException("User with id: " + id + "not found");
        }
    }
}