package com.example.api.controller

import com.example.common.exceptions.ErrorCode

data class ApiResponseDTO<E>(
    val success: Boolean,
    val message: String,
    val data: E?
) {
    companion object {
        fun <E> success(result: E): ApiResponseDTO<E> {
            return ApiResponseDTO(
                success = true,
                message = "success",
                data = result
            )
        }

        fun <E> error(errorCode: ErrorCode): ApiResponseDTO<E> {
            return ApiResponseDTO(
                success = false,
                message = errorCode.message,
                data = null
            )
        }

        fun <E> error(errorCode: ErrorCode, message: String): ApiResponseDTO<E> {
            return ApiResponseDTO(
                success = false,
                message = message,
                data = null
            )
        }
    }
}
