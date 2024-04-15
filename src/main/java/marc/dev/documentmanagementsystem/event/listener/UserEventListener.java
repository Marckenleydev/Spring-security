package marc.dev.documentmanagementsystem.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marc.dev.documentmanagementsystem.event.UserEvent;
import marc.dev.documentmanagementsystem.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventListener {
    private final EmailService emailService;

    @EventListener
    public void onUserEvent(UserEvent event){
        switch (event.getType()){
            case REGISTRATION
                    -> emailService.sendNewAccountEmail(
                            event.getUser().getFirstName(), event.getUser().getEmail(), (String) event.getData().get("key"));
            // Log registration event details

            case RESETPASSWORD
                    -> emailService.sendPasswordResetEmail(
                            event.getUser().getFirstName(), event.getUser().getEmail(), (String) event.getData().get("key"));
            default -> {}
        }
    }
}
