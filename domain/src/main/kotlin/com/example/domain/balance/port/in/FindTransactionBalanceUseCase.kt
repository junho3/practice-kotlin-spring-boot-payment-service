package com.example.domain.balance.port.`in`

import com.example.domain.balance.port.result.FindTransactionBalanceResult
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.TransactionId

interface FindTransactionBalanceUseCase {
    fun findByTransactionId(transactionId: TransactionId): FindTransactionBalanceResult

    fun findByTransactionIdAndPaymentId(
        transactionId: TransactionId,
        paymentId: PaymentId
    ): FindTransactionBalanceResult
}
