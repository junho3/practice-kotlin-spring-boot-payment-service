package com.example.domain.balance.service

import com.example.domain.balance.port.`in`.FindTransactionBalanceUseCase
import com.example.domain.balance.port.out.FindTransactionBalancePort
import com.example.domain.balance.port.result.FindTransactionBalanceResult
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.TransactionId
import org.springframework.stereotype.Service

@Service
class FindTransactionBalanceService(
    private val findTransactionBalancePort: FindTransactionBalancePort
) : FindTransactionBalanceUseCase {
    override fun findByTransactionId(transactionId: TransactionId): FindTransactionBalanceResult {
        val transactionBalance = findTransactionBalancePort.findByTransactionId(transactionId)
        return FindTransactionBalanceResult.from(transactionBalance)
    }

    override fun findByTransactionIdAndPaymentId(
        transactionId: TransactionId,
        paymentId: PaymentId
    ): FindTransactionBalanceResult {
        val transactionBalance = findTransactionBalancePort.findByTransactionIdAndPaymentId(transactionId, paymentId)
        return FindTransactionBalanceResult.from(transactionBalance)
    }
}
