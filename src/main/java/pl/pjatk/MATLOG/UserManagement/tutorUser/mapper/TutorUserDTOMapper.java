package pl.pjatk.MATLOG.UserManagement.tutorUser.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.tutorUser.dto.PrivateLessonTutorUserDTO;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTOMapper;

@Component
public class TutorUserDTOMapper implements UserDTOMapper {

    private final UserPasswordValidator userPasswordValidator;

    public TutorUserDTOMapper(UserPasswordValidator userPasswordValidator) {
        this.userPasswordValidator = userPasswordValidator;
    }

    @Override
    public TutorUser createUser(UserDTO userDTO) {
        return TutorUser.builder()
                .withFirstName(userDTO.firstName())
                .withLastName(userDTO.lastName())
                .withEmailAddress(userDTO.emailAddress())
                .withPassword(userDTO.password(), userPasswordValidator)
                .withDateOfBirth(userDTO.dateOfBirth())
                .build();
    }

    public TutorUser mapToDomain(PrivateLessonTutorUserDTO privateLessonTutorUserDTO) {
        return TutorUser.builder()
                .withId(privateLessonTutorUserDTO.id())
                .build();
    }
}
