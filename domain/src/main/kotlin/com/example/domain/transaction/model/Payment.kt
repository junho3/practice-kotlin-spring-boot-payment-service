package com.example.domain.transaction.model

import com.example.common.enums.PaymentStatus
import com.example.common.enums.PaymentType

data class Payment(
    val paymentId: PaymentId,
    val paymentAmount: PaymentAmount,
    val vatAmount: VatAmount,
    val paymentType: PaymentType,
    val paymentStatus: PaymentStatus,
    val paymentCard: PaymentCard
)
