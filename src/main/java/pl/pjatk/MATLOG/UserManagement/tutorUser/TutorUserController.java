package pl.pjatk.MATLOG.UserManagement.tutorUser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.Domain.Enums.Role;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserRegistrationDTO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.dto.TutorUserProfileDTO;

@RestController
@RequestMapping("/tutor/user/controller")
public class TutorUserController {

    private final TutorUserService tutorUserService;

    public TutorUserController(TutorUserService tutorUserService) {
        this.tutorUserService = tutorUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDTO userDTO) {
        if (userDTO.role().equals(Role.TUTOR)) {
            tutorUserService.registerUser(userDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Tutor user " + userDTO.emailAddress() + " has been registered");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Tried to create " + userDTO.role() + " as TutorUser");
    }

    @GetMapping("/get/profile/{emailAddress}")
    public ResponseEntity<TutorUserProfileDTO> getTutorProfile(@PathVariable String emailAddress) {
        return ResponseEntity.ok(tutorUserService.getTutorProfile(emailAddress));
    }
}
