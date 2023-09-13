package com.example.persistence.adapter.transaction

import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import com.example.domain.transaction.model.SaveTransaction
import com.example.persistence.entity.transaction.toEntity
import com.example.persistence.entity.transaction.toSaveDomain
import com.example.domain.transaction.port.out.SaveTransactionPort
import com.example.persistence.repository.transaction.TransactionRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
class SaveTransactionAdapter(
    private val transactionRepository: TransactionRepository
) : SaveTransactionPort {
    override fun save(saveTransaction: SaveTransaction): SaveTransaction {
        return transactionRepository.save(saveTransaction.toEntity())
            .toSaveDomain(saveTransaction.payment.paymentId)
    }

    override fun savePayment(saveTransaction: SaveTransaction): SaveTransaction {
        return transactionRepository.findById(saveTransaction.transactionId.value)
            .orElseThrow { throw BusinessException(ErrorCode.NOT_EXIST_TRANSACTION) }
            .addPayment(saveTransaction.payment.toEntity())
            .toSaveDomain(saveTransaction.payment.paymentId)
    }
}
