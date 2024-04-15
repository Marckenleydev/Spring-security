package marc.dev.documentmanagementsystem.service;

import marc.dev.documentmanagementsystem.repository.CredentialRepository;
import marc.dev.documentmanagementsystem.repository.UserRepository;
import marc.dev.documentmanagementsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CredentialRepository credentialRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;
}
