package marc.dev.documentmanagementsystem.repository;

import marc.dev.documentmanagementsystem.entity.ConfirmationEntity;
import marc.dev.documentmanagementsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationRepository extends JpaRepository<ConfirmationEntity, Long> {
    Optional<ConfirmationEntity> findByToken(String token);
    Optional<ConfirmationEntity> findByUserEntity(UserEntity userEntity);
}
