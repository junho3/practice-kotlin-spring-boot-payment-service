package com.example.api.controller.v1.payment

import com.example.api.controller.ApiResponseDTO
import com.example.api.controller.v1.payment.request.PaymentRequest
import com.example.api.controller.v1.payment.response.PaymentResponse
import com.example.domain.transaction.port.`in`.PaymentUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Validated
@RestController
class PaymentController(
    private val paymentUseCase: PaymentUseCase
) {
    @PostMapping("/v1/payment")
    fun payment(
        @Valid @RequestBody
        paymentRequest: PaymentRequest
    ): ApiResponseDTO<PaymentResponse> {
        val result = paymentUseCase.payment(paymentRequest.toCommand())
        return ApiResponseDTO.success(PaymentResponse.of(result))
    }
}
