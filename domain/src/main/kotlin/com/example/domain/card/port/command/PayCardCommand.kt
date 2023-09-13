package com.example.domain.card.port.command

import com.example.domain.card.model.CardExpiredDate
import com.example.domain.card.model.CardInstallmentMonth
import com.example.domain.card.model.CardNo
import com.example.domain.card.model.CardValidationCode
import com.example.domain.card.model.EncryptCardData
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.VatAmount
import com.example.domain.transaction.port.command.PaymentCommand

data class PayCardCommand(
    val paymentId: PaymentId,
    val cardNo: CardNo,
    val cardInstallmentMonth: CardInstallmentMonth,
    val cardExpiredDate: CardExpiredDate,
    val cardValidationCode: CardValidationCode,
    val paymentAmount: PaymentAmount,
    val vatAmount: VatAmount,
    val encryptCardData: EncryptCardData
) {
    companion object {
        fun of(
            paymentId: PaymentId,
            paymentCommand: PaymentCommand,
            encryptCardData: EncryptCardData
        ): PayCardCommand = with(paymentCommand) {
            return PayCardCommand(
                paymentId = paymentId,
                cardNo = cardNo,
                cardInstallmentMonth = cardInstallmentMonth,
                cardExpiredDate = cardExpiredDate,
                cardValidationCode = cardValidationCode,
                paymentAmount = paymentAmount,
                vatAmount = vatAmount,
                encryptCardData = encryptCardData
            )
        }
    }
}
