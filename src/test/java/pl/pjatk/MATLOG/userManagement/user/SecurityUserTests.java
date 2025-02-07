package pl.pjatk.MATLOG.userManagement.user;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.domain.User;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecurityUserTests {

    private final UserPasswordValidator userPasswordValidator = new StandardUserPasswordValidator();

    @Test
    void createSecurityUser() {
        User student = StudentUser.builder()
                .withFirstName("Anna")
                .withLastName("Walker")
                .withEmailAddress("test@example.com")
                .withPassword("!pAsswer2", userPasswordValidator)
                .withDateOfBirth(LocalDate.now().minusYears(23))
                .build();
        SecurityUser secUser = new SecurityUser(student);
        assertAll(() -> {
            assertEquals(student.getEmailAddress(), secUser.getUsername());
            assertEquals(student.getPassword(), secUser.getPassword());
            assertEquals(student.getAuthorities(), secUser.getAuthorities());
            assertEquals(student.isAccountNonLocked(), secUser.isAccountNonLocked());
        });
    }
}
