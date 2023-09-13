package com.example.domain.transaction.model

import com.example.domain.card.model.EncryptCardData
import com.example.common.enums.PaymentStatus
import com.example.common.enums.PaymentType

data class FindTransaction(
    override val transactionId: TransactionId,
    val payments: List<Payment>
) : Transaction(transactionId = transactionId) {
    private val payPayment: Payment
        get() = payments.first { it.paymentType == PaymentType.PAYMENT && it.paymentStatus == PaymentStatus.SUCCESS }

    val payPaymentId: PaymentId
        get() = payPayment.paymentId

    val paymentEncryptCardData: EncryptCardData
        get() = payPayment
            .paymentCard
            .encryptCardData

    val paymentAmount: Long
        get() = payments.filter { it.paymentType == PaymentType.PAYMENT }
            .filter { it.paymentStatus == PaymentStatus.SUCCESS }
            .sumOf { it.paymentAmount.value }

    val vatAmount: Long
        get() = payments.filter { it.paymentType == PaymentType.PAYMENT }
            .filter { it.paymentStatus == PaymentStatus.SUCCESS }
            .sumOf { it.vatAmount.value }

    val canceledPaymentAmount: Long
        get() = payments.filter { it.paymentType == PaymentType.CANCEL }
            .filter { it.paymentStatus == PaymentStatus.SUCCESS }
            .sumOf { it.paymentAmount.value }

    val canceledVatAmount: Long
        get() = payments.filter { it.paymentType == PaymentType.CANCEL }
            .filter { it.paymentStatus == PaymentStatus.SUCCESS }
            .sumOf { it.vatAmount.value }
}
