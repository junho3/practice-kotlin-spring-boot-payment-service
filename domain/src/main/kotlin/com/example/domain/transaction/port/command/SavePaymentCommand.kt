package com.example.domain.transaction.port.command

import com.example.domain.card.model.Card
import com.example.domain.card.model.CardInstallmentMonth
import com.example.domain.card.model.EncryptCardData
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.TransactionId
import com.example.domain.transaction.model.VatAmount

data class SavePaymentCommand(
    val transactionId: TransactionId,
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
            paymentId: PaymentId,
            paymentCommand: PaymentCommand,
            card: Card,
            encryptCardData: EncryptCardData
        ): SavePaymentCommand = with(paymentCommand) {
            return SavePaymentCommand(
                transactionId = transactionId,
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
