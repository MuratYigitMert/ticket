package com.cinema.ticket.controller;



// import com.cinema.ticket.service.IRoleService;
// import com.cinema.ticket.service.IUserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
//    private final IUserService userService;
//    private final IRoleService roleService;

//    @GetMapping
//    ResponseEntity<Page<UserResponse>> getAllUsers(Pageable pageable) {
//        Page<User> users = userService.getAllUsers(pageable);
//        Page<UserResponse> response = users.map(DtoConverter::toDto);
//        return ResponseEntity.ok(response);
//    }
//    @GetMapping("/{id}")
//    ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
//        return ResponseEntity.ok(DtoConverter.toDto(userService.getUserbyId(id)));
//    }
//    @PutMapping("/changeUserRole")
//    ResponseEntity<UserResponse> changeUserRole(@RequestBody RoleChangeRequest request) {
//        User user = userService.changeUserRole(request.getId(), request.getRole());
//
//        return ResponseEntity.ok(DtoConverter.toDto(user));
//    }
//    @DeleteMapping("/{id}")
//    ResponseEntity<String> deleteUserById(@PathVariable int id) {
//     userService.deleteUserbyId(id);
//     return ResponseEntity.ok("User deleted successfully");
//    }
//    @PutMapping("/{id}")
//    ResponseEntity<UserResponse> modifyUser(@PathVariable int id, @RequestBody UserRequest request) {
//        Role role= roleService.findRoleById(request.getRoleId());
//        User user= DtoConverter.toEntity(request,role);
//
//        return ResponseEntity.ok(DtoConverter.toDto(userService.modifyUser(id, user)));
//    }
//    @PostMapping
//    ResponseEntity<UserResponse> addUser(@RequestBody UserRequest request) {
//        Role role= roleService.findRoleById(request.getRoleId());
//        User user= DtoConverter.toEntity(request,role);
//        User user1 = userService.addUser(user);
//        return ResponseEntity.ok(DtoConverter.toDto(user1));
//    }


}
