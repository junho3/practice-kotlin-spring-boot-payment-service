package com.example.api.controller.v1.find

import com.example.domain.balance.port.`in`.FindTransactionBalanceUseCase
import com.example.api.controller.ApiResponseDTO
import com.example.api.controller.v1.find.response.FindTransactionBalanceResponse
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.TransactionId
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Validated
@RestController
class FindTransactionBalanceController(
    private val findTransactionBalanceUseCase: FindTransactionBalanceUseCase
) {
    @GetMapping("/v1/transactions/{transactionId}")
    fun findByTransactionId(
        @Valid @PathVariable
        transactionId: TransactionId
    ): ApiResponseDTO<FindTransactionBalanceResponse> {
        val transactionBalance = findTransactionBalanceUseCase.findByTransactionId(transactionId)
        return ApiResponseDTO.success(FindTransactionBalanceResponse.from(transactionBalance))
    }

    @GetMapping("v1/transactions/{transactionId}/payments/{paymentId}")
    fun findByTransactionIdAndPaymentId(
        @Valid @PathVariable
        transactionId: TransactionId,
        @Valid @PathVariable
        paymentId: PaymentId
    ): ApiResponseDTO<FindTransactionBalanceResponse> {
        val transactionBalance = findTransactionBalanceUseCase.findByTransactionIdAndPaymentId(transactionId, paymentId)
        return ApiResponseDTO.success(FindTransactionBalanceResponse.from(transactionBalance))
    }
}
