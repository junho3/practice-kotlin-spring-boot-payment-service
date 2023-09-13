package com.example.persistence.adapter.balance

import com.example.domain.balance.model.SaveTransactionBalance
import com.example.persistence.entity.balance.toEntity
import com.example.persistence.entity.balance.toSaveDomain
import com.example.domain.balance.port.out.UpdateTransactionBalancePort
import com.example.persistence.repository.balance.TransactionBalanceRepository
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
class UpdateTransactionBalanceAdapter(
    private val transactionBalanceRepository: TransactionBalanceRepository
) : UpdateTransactionBalancePort {
    override fun update(saveTransactionBalance: SaveTransactionBalance): SaveTransactionBalance = with(saveTransactionBalance) {
        return transactionBalanceRepository.findById(transactionId.value)
            .orElseThrow { BusinessException(ErrorCode.NOT_EXIST_TRANSACTION) }
            .updateTransactionType(transactionType)
            .updateCancelTransactionAmount(cancelTransactionAmount)
            .updateCancelVatAmount(cancelVatAmount)
            .addPaymentBalance(paymentBalance.toEntity())
            .toSaveDomain(paymentId = paymentBalance.paymentId)
    }
}
