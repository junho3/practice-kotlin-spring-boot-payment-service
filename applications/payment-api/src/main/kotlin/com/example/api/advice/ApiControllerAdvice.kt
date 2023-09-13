package com.example.api.advice

import com.example.api.controller.ApiResponseDTO
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {
    companion object {
        val log = KotlinLogging.logger {}
    }

    @ExceptionHandler(Exception::class)
    fun handleRunTimeException(e: Exception): ResponseEntity<ApiResponseDTO<Any>> {
        log.error("Exception : {}", e.message, e)
        return ResponseEntity(ApiResponseDTO.error(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponseDTO<Any>> {
        log.error("MethodArgumentNotValidException : {}", e.message, e)

        return ResponseEntity(
            ApiResponseDTO.error(
                ErrorCode.INVALID_INPUT_VALUE,
                e.bindingResult.fieldErrors.map { it.defaultMessage }.joinToString("|")
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ApiResponseDTO<Any>> {
        log.error("BusinessException : {}", e.message, e)
        return ResponseEntity(ApiResponseDTO.error(e.errorCode), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
