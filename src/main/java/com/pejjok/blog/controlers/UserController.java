package com.pejjok.blog.controlers;

import com.pejjok.blog.domain.dtos.ChangeRoleRequest;
import com.pejjok.blog.domain.dtos.UserFullDto;
import com.pejjok.blog.domain.dtos.UserShortDto;
import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.mappers.UserMapper;
import com.pejjok.blog.security.BlogUserDetails;
import com.pejjok.blog.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserFullDto>> getUsers(){
        List<UserEntity> users = userService.getAllUsers();
        List<UserFullDto> usersDto = users.stream().map(userMapper::toDto).toList();
        return ResponseEntity.ok(usersDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserFullDto> changeRole(
            @PathVariable UUID id,
            @RequestBody @Valid ChangeRoleRequest changeRoleRequest,
            @AuthenticationPrincipal BlogUserDetails userDetails
            ){
        UserEntity loggedInUser = userDetails.getUser();
        UserEntity user = userService.changeRole(loggedInUser, id, changeRoleRequest);
        UserFullDto userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }
}
