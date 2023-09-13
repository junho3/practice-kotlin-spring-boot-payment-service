package com.example.domain.balance.port.command

import com.example.domain.card.model.CardInstallmentMonth
import com.example.domain.card.model.EncryptCardData
import com.example.common.enums.TransactionType
import com.example.domain.transaction.model.Payment
import com.example.domain.transaction.model.TransactionId

data class UpdateTransactionBalanceCommand(
    val transactionId: TransactionId,
    val transactionType: TransactionType,
    val payment: Payment,
    val cardInstallmentMonth: CardInstallmentMonth,
    val encryptCardData: EncryptCardData
)
