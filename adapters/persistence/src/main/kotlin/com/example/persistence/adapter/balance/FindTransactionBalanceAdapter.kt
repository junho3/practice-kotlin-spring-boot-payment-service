package com.example.persistence.adapter.balance

import com.example.domain.balance.model.FindTransactionBalance
import com.example.persistence.entity.balance.toFindDomain
import com.example.domain.balance.port.out.FindTransactionBalancePort
import com.example.persistence.repository.balance.TransactionBalanceRepository
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.TransactionId
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class FindTransactionBalanceAdapter(
    private val transactionBalanceRepository: TransactionBalanceRepository
) : FindTransactionBalancePort {
    override fun findByTransactionId(transactionId: TransactionId): FindTransactionBalance {
        return transactionBalanceRepository.findById(transactionId.value)
            .orElseThrow { BusinessException(ErrorCode.NOT_EXIST_TRANSACTION) }
            .toFindDomain()
    }

    override fun findByTransactionIdAndPaymentId(
        transactionId: TransactionId,
        paymentId: PaymentId
    ): FindTransactionBalance {
        val transactionBalance = transactionBalanceRepository.findById(transactionId.value)
            .orElseThrow { BusinessException(ErrorCode.NOT_EXIST_TRANSACTION) }

        if (transactionBalance.paymentBalancesEntities.none { it.paymentId == paymentId.value }) {
            throw BusinessException(ErrorCode.NOT_EXIST_TRANSACTION)
        }

        return transactionBalance.toFindDomain(paymentId)
    }
}
