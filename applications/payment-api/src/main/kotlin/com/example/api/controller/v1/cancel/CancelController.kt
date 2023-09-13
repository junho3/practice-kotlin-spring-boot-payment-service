package com.example.api.controller.v1.cancel

import com.example.api.controller.ApiResponseDTO
import com.example.api.controller.v1.cancel.request.CancelRequest
import com.example.api.controller.v1.cancel.response.CancelResponse
import com.example.domain.transaction.port.`in`.CancelUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Validated
@RestController
class CancelController(
    private val cancelUseCase: CancelUseCase
) {
    @PostMapping("/v1/payment/cancel")
    fun payment(
        @Valid @RequestBody
        cancelRequest: CancelRequest
    ): ApiResponseDTO<CancelResponse> {
        val result = cancelUseCase.cancel(cancelRequest.toCommand())
        return ApiResponseDTO.success(CancelResponse.of(result))
    }
}
