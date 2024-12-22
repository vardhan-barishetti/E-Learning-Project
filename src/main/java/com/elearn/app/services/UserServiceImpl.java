package com.elearn.app.services;

import com.elearn.app.config.AppConstants;
import com.elearn.app.dtos.UserDto;
import com.elearn.app.entities.Role;
import com.elearn.app.exceptions.ResourceNotFoundException;
import com.elearn.app.repositories.RoleRepo;
import com.elearn.app.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import com.elearn.app.dtos.UserDto;
import com.elearn.app.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;



    private ModelMapper modelMapper;


    private RoleRepo roleRepo;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto create(UserDto dto) {

        User user = modelMapper.map(dto, User.class);

        user.setUserId(UUID.randomUUID().toString());
        user.setCreateAt(new Date());
        user.setEmailVerified(false);
        user.setSmsVerified(false);
        user.setProfilePath(AppConstants.DEFAULT_PROFILE_PIC_PATH);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepo.findByRoleName(AppConstants.ROLE_GUEST).orElseThrow(() -> new ResourceNotFoundException("Role not found!!"));
        user.assignRole(role);

        User savedUser = userRepo.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto geById(String userId) {

        return modelMapper.map(userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with give email id ")), UserDto.class);
    }
}
