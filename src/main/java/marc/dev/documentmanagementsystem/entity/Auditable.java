package marc.dev.documentmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import marc.dev.documentmanagementsystem.exception.ApiException;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;
import marc.dev.documentmanagementsystem.domain.RequestContext;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true )
public abstract class Auditable {
    @Id
    @SequenceGenerator(name="primary_key_seq", sequenceName = "primary_key_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_seq")
    @Column(name ="id", updatable = false)
    private Long id;
    private String referenceId = new AlternativeJdkIdGenerator().generateId().toString();
    @NotNull
    private Long createdBy;
    @NotNull
    private Long updatedBy;
    @CreatedDate
    @NotNull
    @Column(name = "created_At",updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @CreatedDate
    @Column(name = "updated_At", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void beforePersist() {
        var userId = 0L; //RequestContext.getUserId();
//        if(userId == null){
//            throw  new ApiException("Can not persist entity user ID in Request Context for this thread");
//
//        }
        setCreatedAt(LocalDateTime.now());
        setCreatedBy(userId);
        setUpdatedBy(userId);
        setUpdatedAt(LocalDateTime.now());

    }

    @PreUpdate
    public void beforeUpdate() {
        var userId = 0L; //RequestContext.getUserId();
//        if(userId == null){
//            throw  new ApiException("Can not update entity user ID in Request Context for this thread");
//
//        }

        setUpdatedBy(userId);
        setUpdatedAt(LocalDateTime.now());

    }
}
