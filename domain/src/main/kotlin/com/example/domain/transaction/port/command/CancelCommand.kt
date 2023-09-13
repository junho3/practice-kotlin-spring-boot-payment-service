package com.example.domain.transaction.port.command

import com.example.domain.card.model.CardInstallmentMonth
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.TransactionId
import com.example.domain.transaction.model.VatAmount

data class CancelCommand(
    val transactionId: TransactionId,
    val paymentAmount: PaymentAmount,
    val cardInstallmentMonth: CardInstallmentMonth = CardInstallmentMonth(0),
    val vatAmount: VatAmount
)
