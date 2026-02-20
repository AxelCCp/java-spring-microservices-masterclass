package com.indi.user.model.service;

import com.indi.user.model.dto.AddressDto;
import com.indi.user.model.dto.UserRequest;
import com.indi.user.model.dto.UserResponse;
import com.indi.user.model.entity.Address;

import com.indi.user.model.entity.User;
import com.indi.user.model.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> fetchAll() {
        return this.userRepository.findAll().stream().map(u -> this.mapToUserResponse(u)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UserResponse> getUser(String id) {
        //return this.userList.stream().filter(u -> u.getId().equals(id)).findFirst();
        return this.userRepository.findById(id).map(u -> mapToUserResponse(u));
    }

    @Transactional
    public void create(@RequestBody UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);
        this.userRepository.save(user);
    }

    @Transactional
    public Boolean update(String id, UserRequest userRequest) {
        return this.userRepository.findById(id).map(u -> {
            updateUserFromRequest(u, userRequest);
            this.userRepository.save(u);
            return true;
        }).orElse(false);

        /*return this.userRepository.findById(id).map(u -> {
            u.setFirstname(user.getFirstname());
            u.setLastname(user.getLastname());
            this.userRepository.save(u);
            return  true;
        }).orElse(false);*/

        /*return userList.stream().filter(u -> u.getId().equals(id)).findFirst().map(u -> {
            u.setFirstname(user.getFirstname());
            u.setLastname(user.getLastname());
            return  true;
        }).orElse(false);*/
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstname(user.getFirstname());
        userResponse.setLastname(user.getLastname());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());
        if(user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setZipcode(user.getAddress().getZipcode());
            userResponse.setAddress(addressDto);
        }
        return userResponse;
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }
    }
}
