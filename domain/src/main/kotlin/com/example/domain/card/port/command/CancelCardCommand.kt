package com.example.domain.card.port.command

import com.example.domain.card.model.CardInstallmentMonth
import com.example.domain.card.model.EncryptCardData
import com.example.domain.transaction.model.FindTransaction
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.VatAmount
import com.example.domain.transaction.port.command.CancelCommand

data class CancelCardCommand(
    val paymentId: PaymentId,
    val cardInstallmentMonth: CardInstallmentMonth,
    val paymentAmount: PaymentAmount,
    val vatAmount: VatAmount,
    val payPaymentId: PaymentId,
    val encryptCardData: EncryptCardData
) {
    companion object {
        fun of(
            paymentId: PaymentId,
            cancelCommand: CancelCommand,
            transaction: FindTransaction
        ): CancelCardCommand = with(cancelCommand) {
            return CancelCardCommand(
                paymentId = paymentId,
                cardInstallmentMonth = cardInstallmentMonth,
                paymentAmount = paymentAmount,
                vatAmount = vatAmount,
                payPaymentId = transaction.payPaymentId,
                encryptCardData = transaction.paymentEncryptCardData
            )
        }
    }
}
