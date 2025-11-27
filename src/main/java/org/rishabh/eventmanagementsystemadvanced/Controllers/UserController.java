package org.rishabh.eventmanagementsystemadvanced.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.Role;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ApisResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PagedResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.UserDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.UserRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User Management", description = "APIs endPoints to  managing Users Operations")
public class UserController {
    private final UserService userService;


    @PostMapping("/register")
    @Operation(
            summary = "Register new User",
            description = "Creates a new user and sends activation token to Email"
    )


    @ApiResponse(responseCode = "200", description = "User registered successfully",
            content = @Content(schema = @Schema(implementation = UserDto.class)))
    @ApiResponse(responseCode = "400", description = "Validation failed")
    @ApiResponse(responseCode = "409", description = "Email already exists")

    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserRequest user) {
        UserDto user1 = userService.createUser(user);
        return ResponseEntity.ok(user1);
    }

    @Operation(
            summary = "Activate user account",
            description = "Activate account using token received on email during registration"
    )

    @ApiResponse(responseCode = "200", description = "User activated successfully")
    @ApiResponse(responseCode = "404", description = "Invalid or expired token")

    @GetMapping("/activation")
    public ResponseEntity<String> activateUser(@RequestParam("token") String token) {
        boolean isActivated = userService.activateUser(token);
        if (isActivated) {
            return ResponseEntity.ok("your account has been activated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activation Token Not Found or Expired");
        }
    }


    @PutMapping("/update/{id}")
    @Operation(
            summary = "Update user details",
            description = "Update user profile including name, mobile, etc."
    )

    @ApiResponse(responseCode = "200", description = "User updated successfully",
            content = @Content(schema = @Schema(implementation = UserDto.class)))
    @ApiResponse(responseCode = "401", description = "User not activated")
    @ApiResponse(responseCode = "404", description = "User not found")

    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        boolean accountActivated = userService.isAccountActivated(userRequest.getEmail());
        if (!accountActivated) {
            throw new UsernameNotFoundException("User not found with id " + id);
        } else {
            UserDto updated = userService.updateUser(id, userRequest);
            return ResponseEntity.ok(updated);
        }
    }

    @GetMapping("/single/{id}")

    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")

    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Get all users (Admin only)",
            description = "Admin can fetch paginated list of all users"
    )

    @ApiResponse(responseCode = "200", description = "Fetched user list successfully")
    @ApiResponse(responseCode = "403", description = "Unauthorized access")

    public ResponseEntity<PagedResponse<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String sortDir
    ) {
        PagedResponse<UserDto> user = userService.getAllUsers(page, size, sortBy, sortDir);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/filter")

    @Operation(summary = "Filter users by role")
    @ApiResponse(responseCode = "200", description = "Filtered users retrieved")

    public ResponseEntity<PagedResponse<UserDto>> getAllUsersByRoles(
            @RequestParam Role role,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String sortDir
    ) {

        PagedResponse<UserDto> usersByRole = userService.getAllUsersByRole(role, page, size, sortBy, sortDir);
        return ResponseEntity.ok(usersByRole);
    }

    @DeleteMapping("/del/{id}")
    @Operation(summary = "Delete user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<ApisResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ApisResponse apiResponse = new ApisResponse();
        apiResponse.setMessage("User deleted successfully");
        return ResponseEntity.ok(apiResponse);

    }

}
