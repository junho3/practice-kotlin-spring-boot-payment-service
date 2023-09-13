package com.example.persistence.entity.transaction

import com.example.domain.card.model.CardInstallmentMonth
import com.example.domain.card.model.CardTransactionData
import com.example.domain.card.model.CardTransactionId
import com.example.domain.card.model.EncryptCardData
import com.example.domain.transaction.model.FindTransaction
import com.example.domain.transaction.model.Payment
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.PaymentCard
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.SaveTransaction
import com.example.domain.transaction.model.TransactionId
import com.example.domain.transaction.model.VatAmount

fun TransactionEntity.toFindDomain(): FindTransaction = FindTransaction(
    transactionId = TransactionId(this.transactionId),
    payments = this.paymentEntities.map { it.toDomain() }
)

fun TransactionEntity.toSaveDomain(paymentId: PaymentId): SaveTransaction = SaveTransaction(
    transactionId = TransactionId(this.transactionId),
    payment = this.paymentEntities.first { it.paymentId == paymentId.value }.toDomain()
)

fun SaveTransaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        transactionId = transactionId.value
    ).addPayment(payment.toEntity())
}

fun Payment.toEntity(): PaymentEntity {
    return PaymentEntity(
        paymentId = this.paymentId.value,
        paymentAmount = this.paymentAmount.value,
        vatAmount = this.vatAmount.value,
        paymentType = this.paymentType,
        paymentStatus = this.paymentStatus
    )
        .addPaymentCard(
            PaymentCardEntity(
                cardTransactionId = this.paymentCard.cardTransactionId.value,
                cardTransactionData = this.paymentCard.cardTransactionData.value,
                encryptCardData = this.paymentCard.encryptCardData.value,
                cardInstallmentMonth = this.paymentCard.cardInstallmentMonth.value
            )
        )
}

fun PaymentEntity.toDomain(): Payment {
    return Payment(
        paymentId = PaymentId(this.paymentId),
        paymentAmount = PaymentAmount(this.paymentAmount),
        vatAmount = VatAmount(this.vatAmount),
        paymentType = this.paymentType,
        paymentStatus = this.paymentStatus,
        paymentCard = PaymentCard(
            cardTransactionId = CardTransactionId(this.paymentCard!!.cardTransactionId),
            cardTransactionData = CardTransactionData(this.paymentCard!!.cardTransactionData),
            encryptCardData = EncryptCardData(this.paymentCard!!.encryptCardData),
            cardInstallmentMonth = CardInstallmentMonth(this.paymentCard!!.cardInstallmentMonth)
        )
    )
}
