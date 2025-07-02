package com.cinema.ticket.service.impl;


import com.cinema.ticket.exception.ResourceNotFoundException;
import com.cinema.ticket.entity.Role;
import com.cinema.ticket.entity.User;
import com.cinema.ticket.repository.RoleRepo;
import com.cinema.ticket.repository.UserRepo;
import com.cinema.ticket.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public Page<User> getAllUsers(Pageable pageable){
        return userRepo.findAll(pageable);

    }
    @Override
    public User getUserbyId(int id){
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    @Override
    public User changeUserRole(int id, String role){
        User user= userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Role role1=roleRepo.findByName(role).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        user.setRole(role1);
        return userRepo.save(user);
    }
    @Override
    public void deleteUserbyId(int id){
        userRepo.deleteById(id);
    }
    @Override
    public User modifyUser(int id, User user){
        User user1=userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        Role role = roleRepo.findById(user.getRole().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        user1.setRole(role);
        user1.setUsername(user.getUsername());
        return userRepo.save(user1);
    }
    @Override
    public User addUser(User user){
        return userRepo.save(user);
    }


}
