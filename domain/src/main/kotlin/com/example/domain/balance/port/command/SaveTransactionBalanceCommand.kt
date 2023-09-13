package com.example.domain.balance.port.command

import com.example.domain.card.model.CardInstallmentMonth
import com.example.domain.card.model.EncryptCardData
import com.example.domain.transaction.model.Payment
import com.example.domain.transaction.model.TransactionId

data class SaveTransactionBalanceCommand(
    val transactionId: TransactionId,
    val payment: Payment,
    val cardInstallmentMonth: CardInstallmentMonth,
    val encryptCardData: EncryptCardData
)
