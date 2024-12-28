import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "request_logs")
class APIRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val timestamp: LocalDateTime = LocalDateTime.now(),

    // todo: track unique visitors without using IP
    val ipAddress: String,

    val path: String,

    val method: String,

    @Column(length = 1024)
    val requestBody: String? = null,

    val statusCode: Int? = null,

    val duration: Long? = null,  // request duration in milliseconds

    @Column(name = "user_agent")
    val userAgent: String? = null
)