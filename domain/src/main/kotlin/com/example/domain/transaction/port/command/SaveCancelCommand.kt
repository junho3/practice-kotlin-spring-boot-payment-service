package com.example.domain.transaction.port.command

import com.example.domain.card.model.Card
import com.example.domain.card.model.CardInstallmentMonth
import com.example.domain.card.model.EncryptCardData
import com.example.common.enums.TransactionType
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.TransactionId
import com.example.domain.transaction.model.VatAmount

data class SaveCancelCommand(
    val transactionId: TransactionId,
    val transactionType: TransactionType,
    val paymentId: PaymentId,
    val paymentAmount: PaymentAmount,
    val vatAmount: VatAmount,
    val card: Card,
    val cardInstallmentMonth: CardInstallmentMonth,
    val encryptCardData: EncryptCardData
) {
    companion object {
        fun of(
            transactionId: TransactionId,
            transactionType: TransactionType,
            paymentId: PaymentId,
            cancelCommand: CancelCommand,
            card: Card,
            encryptCardData: EncryptCardData
        ): SaveCancelCommand = with(cancelCommand) {
            return SaveCancelCommand(
                transactionId = transactionId,
                transactionType = transactionType,
                paymentId = paymentId,
                paymentAmount = paymentAmount,
                vatAmount = vatAmount,
                card = card,
                cardInstallmentMonth = cardInstallmentMonth,
                encryptCardData = encryptCardData
            )
        }
    }
}
