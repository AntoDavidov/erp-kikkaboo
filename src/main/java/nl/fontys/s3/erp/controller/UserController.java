package nl.fontys.s3.erp.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.dtos.userdto.*;
import nl.fontys.s3.erp.business.userusecases.*;
import nl.fontys.s3.erp.domain.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final CreateUserForEmployeeUseCase createUserForEmployeeUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @GetMapping()
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER') or hasRole('SPECIALIST')")
    public ResponseEntity<GetUsersResponse> getUsers() {
        return ResponseEntity.ok(getAllUsersUseCase.getAllUsers());
    }

    @PostMapping()
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER')")
    public ResponseEntity<CreateUserForEmployeeResponse> createUser(@RequestBody @Valid CreateUserForEmployeeRequest request) {
        CreateUserForEmployeeResponse response = createUserForEmployeeUseCase.createUserForEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER')")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        deleteUserUseCase.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER')")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") final long id) {
        final User user = getUserUseCase.getUser(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);
    }
}
