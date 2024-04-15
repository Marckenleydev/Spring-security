package marc.dev.documentmanagementsystem.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import marc.dev.documentmanagementsystem.entity.UserEntity;
import marc.dev.documentmanagementsystem.enumaration.converter.EventType;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
public class UserEvent {
    private UserEntity user;
    private EventType type;
    private Map<?, ?> data;
}
