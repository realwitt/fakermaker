package elias.fakerMaker.config

import elias.fakerMaker.types.ApiError
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebExchange

@RestControllerAdvice
class ApiGlobalExceptionHandler {
    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(ApiException::class)
    fun handleApiException(
        ex: ApiException,
        request: ServerWebExchange
    ): ResponseEntity<ApiError> {
        logger.error(ex) { "API error occurred: ${ex.message}" }

        return ResponseEntity
            .status(ex.status)
            .body(ApiError(
                status = ex.status,
                message = ex.message,
                path = request.request.path.value(),
                errors = ex.errors,
                requestId = request.request.headers.getFirst("X-Request-ID")
            ))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException,
        request: ServerWebExchange
    ): ResponseEntity<ApiError> {
        val errors = ex.bindingResult.fieldErrors.map {
            "${it.field}: ${it.defaultMessage}"
        }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiError(
                status = HttpStatus.BAD_REQUEST,
                message = "Validation failed",
                path = request.request.path.value(),
                errors = errors,
                requestId = request.request.headers.getFirst("X-Request-ID")
            ))
    }
}