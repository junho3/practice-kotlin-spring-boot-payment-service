package com.example.persistence.adapter.transaction

import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import com.example.domain.transaction.model.FindTransaction
import com.example.domain.transaction.model.TransactionId
import com.example.persistence.entity.transaction.toFindDomain
import com.example.domain.transaction.port.out.FindTransactionPort
import com.example.persistence.repository.transaction.TransactionRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class FindTransactionAdapter(
    private val transactionRepository: TransactionRepository
) : FindTransactionPort {
    override fun findByTransactionId(transactionId: TransactionId): FindTransaction {
        return transactionRepository.findById(transactionId.value)
            .orElseThrow { throw BusinessException(ErrorCode.NOT_EXIST_TRANSACTION) }
            .toFindDomain()
    }
}
