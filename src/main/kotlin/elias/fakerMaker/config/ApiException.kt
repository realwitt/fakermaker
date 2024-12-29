package elias.fakerMaker.config

import org.springframework.http.HttpStatus

// config/exception/ApiException.kt
sealed class ApiException(
    val status: HttpStatus,
    override val message: String,
    val errors: List<String> = emptyList()
) : RuntimeException(message) {
    class BadRequest(message: String, errors: List<String> = emptyList()) :
        ApiException(HttpStatus.BAD_REQUEST, message, errors)

    class NotFound(message: String) :
        ApiException(HttpStatus.NOT_FOUND, message)

    class InvalidData(message: String, errors: List<String> = emptyList()) :
        ApiException(HttpStatus.UNPROCESSABLE_ENTITY, message, errors)

    class InternalError(message: String = "Something is dreadfully wrong.") :
        ApiException(HttpStatus.INTERNAL_SERVER_ERROR, message)
}
