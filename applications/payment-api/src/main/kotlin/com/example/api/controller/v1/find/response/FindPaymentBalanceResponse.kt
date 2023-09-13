package com.example.api.controller.v1.find.response

import com.example.domain.balance.port.result.FindPaymentBalanceResult
import java.time.LocalDateTime

data class FindPaymentBalanceResponse(
    val paymentId: String,
    val cardNo: String,
    val cardValidationCode: Int,
    val cardExpiredDate: String,
    val cardInstallmentMonth: Int,
    val paymentType: String,
    val paymentAmount: Long,
    val vatAmount: Long,
    val paymentBalanceAmount: Long,
    val vatBalanceAmount: Long,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(
            findPaymentBalanceResult: FindPaymentBalanceResult
        ): FindPaymentBalanceResponse = with(findPaymentBalanceResult) {
            return FindPaymentBalanceResponse(
                paymentId = paymentId.value,
                cardNo = cardNo,
                cardValidationCode = cardValidationCode,
                cardExpiredDate = cardExpiredDate,
                cardInstallmentMonth = cardInstallmentMonth,
                paymentType = paymentType.description,
                paymentAmount = paymentAmount,
                vatAmount = vatAmount,
                paymentBalanceAmount = paymentBalanceAmount,
                vatBalanceAmount = vatBalanceAmount,
                createdAt = createdAt
            )
        }
    }
}
