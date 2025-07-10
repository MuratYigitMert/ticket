package com.cinema.ticket.service.impl;


import com.cinema.ticket.dto.DtoConverter;
import com.cinema.ticket.dto.UserRequest;
import com.cinema.ticket.exception.ResourceNotFoundException;
import com.cinema.ticket.entity.Role;
import com.cinema.ticket.entity.User;
import com.cinema.ticket.repository.RoleRepo;
import com.cinema.ticket.repository.UserRepo;
import com.cinema.ticket.service.IRoleService;
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
    private final IRoleService roleService;

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
    public User modifyUser(int id, UserRequest request){
        User user1=userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user1.setEmail(request.getEmail());
        user1.setPassword(request.getPassword());
        user1.setUsername(request.getUsername());
        Role role = roleRepo.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        user1.setRole(role);

        return userRepo.save(user1);
    }
    @Override
    public User addUser(UserRequest request) {
        Role role = roleService.findRoleById(request.getRoleId());
        User user = DtoConverter.toEntity(request, role);
        return userRepo.save(user);
    }


}
