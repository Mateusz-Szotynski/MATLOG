package pl.pjatk.MATLOG.userManagement.tutorUser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.Domain.Enums.Role;
import pl.pjatk.MATLOG.Domain.Enums.SchoolSubject;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.TutorUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.util.Collection;

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
        return ResponseEntity.ok(tutorUserService.getTutorUserProfile(emailAddress));
    }

    @PutMapping("/add/specializations/{id}")
    public ResponseEntity<Boolean> addSpecializations(@PathVariable String id,
                                                     @RequestBody Collection<SchoolSubject> specialization) {
        return ResponseEntity.ok(tutorUserService.addSpecializations(id, specialization));
    }

    @PutMapping("/add/specialization/{id}")
    public ResponseEntity<Boolean> addSpecialization(@PathVariable String id,
                                                     @RequestBody SchoolSubject specialization) {
        return ResponseEntity.ok(tutorUserService.addSpecialization(id, specialization));
    }
}
