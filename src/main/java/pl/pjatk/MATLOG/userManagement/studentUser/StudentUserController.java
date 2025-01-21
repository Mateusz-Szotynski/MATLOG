package pl.pjatk.MATLOG.userManagement.studentUser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.domain.enums.Role;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserChangePasswordDTO;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

@RestController
@RequestMapping("/student/user/controller")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentUserController {

    private final StudentUserService studentUserService;

    public StudentUserController(StudentUserService studentUserService) {
        this.studentUserService = studentUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        if (userRegistrationDTO.role().equals(Role.STUDENT)) {
            studentUserService.registerUser(userRegistrationDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Student user " + userRegistrationDTO.emailAddress() + " has been registered");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Tried to create " + userRegistrationDTO.role() + " as StudentUser");
    }

    @GetMapping("/get/profile/{id}")
    public ResponseEntity<StudentUserProfileDTO> getStudentProfile(@PathVariable String id) {
        return ResponseEntity.ok(studentUserService.getStudentProfile(id));
    }

    @PutMapping("/change/password")
    public ResponseEntity<Void> changePassword(@RequestBody StudentUserChangePasswordDTO studentUserChangePasswordDTO) {
        studentUserService.changePassword(studentUserChangePasswordDTO);
        return ResponseEntity.accepted().build();
    }
}
