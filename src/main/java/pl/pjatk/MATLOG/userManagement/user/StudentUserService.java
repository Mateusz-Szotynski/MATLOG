package pl.pjatk.MATLOG.UserManagement.user;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserAlreadyExistException;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserInvalidEmailAddressException;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.StudentUserRepository;

@Service
public class StudentUserService implements UserService {

    private final StudentUserRepository studentUserRepository;
    private final UserRepositoryService userRepositoryService;
    private final StudentUserMapperFactory studentUserMapperFactory;
    private final UserPasswordValidator userPasswordValidator;
    private final PasswordEncoder passwordEncoder;

    public StudentUserService(StudentUserRepository studentUserRepository,
                              UserRepositoryService userRepositoryService,
                              StudentUserMapperFactory studentUserMapperFactory,
                              UserPasswordValidator userPasswordValidator,
                              PasswordEncoder passwordEncoder) {
        this.studentUserRepository = studentUserRepository;
        this.userRepositoryService = userRepositoryService;
        this.studentUserMapperFactory = studentUserMapperFactory;
        this.userPasswordValidator = userPasswordValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserByEmailAddress(String emailAddress) throws AuthenticationException, UserInvalidEmailAddressException {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new UserInvalidEmailAddressException();
        }
        User user = userRepositoryService.findUserByEmailAddress(emailAddress);
        if (user == null) {
            throw new AuthenticationException("User with that email address does not exist.");
        }
        return user;
    }

    @Override
    public void registerUser(UserDTO userDTO) throws IllegalArgumentException, UserAlreadyExistException {
        if (userDTO == null) {
            throw new IllegalArgumentException("Please provide valid UserDTO");
        }
        User user = userRepositoryService.findUserByEmailAddress(userDTO.emailAddress());
        if (user != null) {
            throw new UserAlreadyExistException();
        }
        User domainUser = studentUserMapperFactory
                .getUserDTOMapper()
                .createUser(userDTO);

        domainUser.changePassword(passwordEncoder.encode(userDTO.password()), userPasswordValidator);

        StudentUserDAO student = studentUserMapperFactory
                .getUserDAOMapper()
                .createUserDAO(domainUser);

        studentUserRepository.save(student);
    }
}
