package nl.fontys.s3.erp.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.DTOs.UserDTOs.CreateUserRequest;
import nl.fontys.s3.erp.business.DTOs.UserDTOs.CreateUserResponse;
import nl.fontys.s3.erp.business.DTOs.UserDTOs.GetUsersResponse;
import nl.fontys.s3.erp.business.DTOs.UserDTOs.UpdateUserRequest;
import nl.fontys.s3.erp.business.UserUseCases.*;
import nl.fontys.s3.erp.domain.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @GetMapping()
    public ResponseEntity<GetUsersResponse> getUsers() {
        return ResponseEntity.ok(getAllUsersUseCase.getAllUsers());
    }

    @PostMapping()
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        deleteUserUseCase.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") final long id) {
        final User user = getUserUseCase.getUser(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(value = "id") final long id, @RequestBody @Valid UpdateUserRequest request) {
        request.setUserId(id);
        updateUserUseCase.updateUser(request);
        return ResponseEntity.noContent().build();
    }

}
