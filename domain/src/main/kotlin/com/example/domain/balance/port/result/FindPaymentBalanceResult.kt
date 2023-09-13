package com.example.domain.balance.port.result

import com.example.domain.balance.model.PaymentBalance
import com.example.common.enums.PaymentType
import com.example.domain.transaction.model.PaymentId
import java.time.LocalDateTime

data class FindPaymentBalanceResult(
    val paymentId: PaymentId,
    val cardNo: String,
    val cardValidationCode: Int,
    val cardExpiredDate: String,
    val cardInstallmentMonth: Int,
    val paymentType: PaymentType,
    val paymentAmount: Long,
    val vatAmount: Long,
    val paymentBalanceAmount: Long,
    val vatBalanceAmount: Long,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(paymentBalance: PaymentBalance): FindPaymentBalanceResult = with(paymentBalance) {
            return FindPaymentBalanceResult(
                paymentId = paymentId,
                cardNo = encryptCardData.decryptCardNo().masking(),
                cardValidationCode = encryptCardData.decryptCardValidationCode().value,
                cardExpiredDate = encryptCardData.decryptCardExpiredDate().value,
                cardInstallmentMonth = cardInstallmentMonth,
                paymentType = paymentType,
                paymentAmount = paymentAmount,
                vatAmount = vatAmount,
                paymentBalanceAmount = paymentBalanceAmount,
                vatBalanceAmount = vatBalanceAmount,
                createdAt = createdAt!!
            )
        }
    }
}
