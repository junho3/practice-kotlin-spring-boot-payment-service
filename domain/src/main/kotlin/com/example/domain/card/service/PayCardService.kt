package com.example.domain.card.service

import com.example.domain.card.model.Card
import com.example.domain.card.model.CardTransactionData
import com.example.domain.card.port.command.PayCardCommand
import com.example.domain.card.port.`in`.PayCardUseCase
import com.example.domain.card.port.out.PayCardPort
import com.example.common.enums.PaymentType
import org.springframework.stereotype.Service

@Service
class PayCardService(
    private val payCardPort: PayCardPort
) : PayCardUseCase {
    override fun pay(command: PayCardCommand): Card = with(command) {
        return payCardPort.pay(
            Card(
                cardTransactionData = CardTransactionData.of(
                    paymentType = PaymentType.PAYMENT,
                    paymentId = paymentId,
                    cardNo = cardNo,
                    cardInstallmentMonth = cardInstallmentMonth,
                    cardExpiredDate = cardExpiredDate,
                    cardValidationCode = cardValidationCode,
                    paymentAmount = paymentAmount,
                    vatAmount = vatAmount,
                    encryptCardData = encryptCardData
                )
            )
        )
    }
}
