package marc.dev.documentmanagementsystem.enumaration.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import marc.dev.documentmanagementsystem.enumaration.Authority;

import java.util.stream.Stream;
@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Authority, String> {

    @Override
    public String convertToDatabaseColumn(Authority authority) {
        if(authority == null){
            return null;
        }
        return authority.getValue();

    }

    @Override
    public Authority convertToEntityAttribute(String code) {

        if (code == null) {
            return null;
        }
        return Stream.of(Authority.values())
                .filter(authority -> authority.getValue().equals(code))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }

}
