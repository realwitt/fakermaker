package elias.fakerMaker.types.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Column

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class Auditable(
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    var createdDate: Instant? = null,

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    var lastModifiedDate: Instant? = null
)