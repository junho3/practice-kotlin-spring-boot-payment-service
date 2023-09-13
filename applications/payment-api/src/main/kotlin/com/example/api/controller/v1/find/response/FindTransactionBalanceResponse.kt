package com.example.api.controller.v1.find.response

import com.example.domain.balance.port.result.FindTransactionBalanceResult
import java.time.LocalDateTime

data class FindTransactionBalanceResponse(
    val transactionId: String,
    val transactionType: String,
    val paymentTransactionAmount: Long,
    val paymentVatAmount: Long,
    val cancelTransactionAmount: Long,
    val cancelVatAmount: Long,
    val payments: List<FindPaymentBalanceResponse>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(
            findTransactionBalanceResult: FindTransactionBalanceResult
        ): FindTransactionBalanceResponse = with(findTransactionBalanceResult) {
            return FindTransactionBalanceResponse(
                transactionId = transactionId.value,
                transactionType = transactionType.description,
                paymentTransactionAmount = paymentTransactionAmount,
                paymentVatAmount = paymentVatAmount,
                cancelTransactionAmount = cancelTransactionAmount,
                cancelVatAmount = cancelVatAmount,
                payments = payments.map { FindPaymentBalanceResponse.from(it) },
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}
