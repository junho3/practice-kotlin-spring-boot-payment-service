package com.example.domain.balance.model

import com.example.domain.card.model.EncryptCardData
import com.example.common.enums.PaymentType
import com.example.domain.transaction.model.PaymentId
import java.time.LocalDateTime

data class PaymentBalance(
    val paymentId: PaymentId,
    val paymentAmount: Long,
    val vatAmount: Long,
    val paymentBalanceAmount: Long,
    val vatBalanceAmount: Long,
    val encryptCardData: EncryptCardData,
    val cardInstallmentMonth: Int,
    val paymentType: PaymentType,
    val createdAt: LocalDateTime? = LocalDateTime.now()
)
