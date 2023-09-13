package com.example.domain.transaction.port.command

import com.example.domain.card.model.CardExpiredDate
import com.example.domain.card.model.CardInstallmentMonth
import com.example.domain.card.model.CardNo
import com.example.domain.card.model.CardValidationCode
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.VatAmount

data class PaymentCommand(
    val cardNo: CardNo,
    val cardExpiredDate: CardExpiredDate,
    val cardValidationCode: CardValidationCode,
    val cardInstallmentMonth: CardInstallmentMonth,
    val paymentAmount: PaymentAmount,
    val vatAmount: VatAmount
)
