package elias.fakerMaker.types

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ApiError(
    val status: HttpStatus,
    val message: String,
    val path: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val errors: List<String> = emptyList(),
    val requestId: String? = null  // Useful for logging/debugging
)
