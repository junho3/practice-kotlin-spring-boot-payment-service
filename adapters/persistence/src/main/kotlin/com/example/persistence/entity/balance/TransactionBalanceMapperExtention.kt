package com.example.persistence.entity.balance

import com.example.domain.balance.model.FindTransactionBalance
import com.example.domain.balance.model.PaymentBalance
import com.example.domain.balance.model.SaveTransactionBalance
import com.example.domain.card.model.EncryptCardData
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.TransactionId

fun TransactionBalanceEntity.toFindDomain(): FindTransactionBalance = FindTransactionBalance(
    transactionId = TransactionId(this.transactionId),
    transactionType = this.transactionType,
    paymentTransactionAmount = this.paymentTransactionAmount,
    paymentVatAmount = this.paymentVatAmount,
    cancelTransactionAmount = this.cancelTransactionAmount,
    cancelVatAmount = this.cancelVatAmount,
    paymentBalances = this.paymentBalancesEntities.map { it.toDomain() },
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun TransactionBalanceEntity.toFindDomain(paymentId: PaymentId): FindTransactionBalance = FindTransactionBalance(
    transactionId = TransactionId(this.transactionId),
    transactionType = this.transactionType,
    paymentTransactionAmount = this.paymentTransactionAmount,
    paymentVatAmount = this.paymentVatAmount,
    cancelTransactionAmount = this.cancelTransactionAmount,
    cancelVatAmount = this.cancelVatAmount,
    paymentBalances = listOf(this.paymentBalancesEntities.first { it.paymentId == paymentId.value }.toDomain()),
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun TransactionBalanceEntity.toSaveDomain(paymentId: PaymentId): SaveTransactionBalance = SaveTransactionBalance(
    transactionId = TransactionId(this.transactionId),
    transactionType = this.transactionType,
    paymentTransactionAmount = this.paymentTransactionAmount,
    paymentVatAmount = this.paymentVatAmount,
    cancelTransactionAmount = this.cancelTransactionAmount,
    cancelVatAmount = this.cancelVatAmount,
    paymentBalance = this.paymentBalancesEntities.first { it.paymentId == paymentId.value }.toDomain()
)

fun SaveTransactionBalance.toEntity(): TransactionBalanceEntity {
    return TransactionBalanceEntity(
        transactionId = transactionId.value,
        transactionType = transactionType,
        paymentTransactionAmount = paymentTransactionAmount,
        paymentVatAmount = paymentVatAmount,
        cancelTransactionAmount = cancelTransactionAmount,
        cancelVatAmount = cancelVatAmount
    )
        .addPaymentBalance(paymentBalance.toEntity())
}

fun PaymentBalance.toEntity(): PaymentBalanceEntity = PaymentBalanceEntity(
    paymentId = this.paymentId.value,
    paymentAmount = this.paymentAmount,
    vatAmount = this.vatAmount,
    paymentBalanceAmount = this.paymentBalanceAmount,
    vatBalanceAmount = this.vatBalanceAmount,
    encryptCardData = this.encryptCardData.value,
    cardInstallmentMonth = this.cardInstallmentMonth,
    paymentType = this.paymentType
)

fun PaymentBalanceEntity.toDomain(): PaymentBalance = PaymentBalance(
    paymentId = PaymentId(this.paymentId),
    paymentAmount = this.paymentAmount,
    vatAmount = this.vatAmount,
    paymentBalanceAmount = this.paymentBalanceAmount,
    vatBalanceAmount = this.vatBalanceAmount,
    encryptCardData = EncryptCardData(this.encryptCardData),
    cardInstallmentMonth = cardInstallmentMonth,
    paymentType = this.paymentType
)
