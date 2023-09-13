package com.example.domain.balance.port.result

import com.example.domain.balance.model.FindTransactionBalance
import com.example.common.enums.TransactionType
import com.example.domain.transaction.model.TransactionId
import java.time.LocalDateTime

data class FindTransactionBalanceResult(
    val transactionId: TransactionId,
    val transactionType: TransactionType,
    val paymentTransactionAmount: Long,
    val paymentVatAmount: Long,
    val cancelTransactionAmount: Long,
    val cancelVatAmount: Long,
    val payments: List<FindPaymentBalanceResult>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(findTransactionBalance: FindTransactionBalance): FindTransactionBalanceResult = with(findTransactionBalance) {
            return FindTransactionBalanceResult(
                transactionId = transactionId,
                transactionType = transactionType,
                paymentTransactionAmount = paymentTransactionAmount,
                paymentVatAmount = paymentVatAmount,
                cancelTransactionAmount = cancelTransactionAmount,
                cancelVatAmount = cancelVatAmount,
                payments = paymentBalances.map { FindPaymentBalanceResult.from(it) },
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}
