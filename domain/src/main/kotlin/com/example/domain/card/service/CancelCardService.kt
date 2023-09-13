package com.example.domain.card.service

import com.example.domain.card.model.Card
import com.example.domain.card.model.CardTransactionData
import com.example.domain.card.port.command.CancelCardCommand
import com.example.domain.card.port.`in`.CancelCardUseCase
import com.example.domain.card.port.out.CancelCardPort
import com.example.common.enums.PaymentType
import org.springframework.stereotype.Service

@Service
class CancelCardService(
    private val cancelCardPort: CancelCardPort
) : CancelCardUseCase {
    override fun cancel(command: CancelCardCommand): Card = with(command) {
        return cancelCardPort.cancel(
            Card(
                cardTransactionData = CardTransactionData.of(
                    paymentType = PaymentType.CANCEL,
                    paymentId = paymentId,
                    cardNo = encryptCardData.decryptCardNo(),
                    cardInstallmentMonth = cardInstallmentMonth,
                    cardExpiredDate = encryptCardData.decryptCardExpiredDate(),
                    cardValidationCode = encryptCardData.decryptCardValidationCode(),
                    paymentAmount = paymentAmount,
                    vatAmount = vatAmount,
                    payPaymentId = payPaymentId,
                    encryptCardData = encryptCardData
                )
            )
        )
    }
}
