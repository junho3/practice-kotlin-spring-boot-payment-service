package com.example.persistence.adapter.transaction

import com.example.domain.transaction.model.TransactionFailLog
import com.example.persistence.entity.transaction.toDomain
import com.example.persistence.entity.transaction.toEntity
import com.example.domain.transaction.port.out.SaveTransactionFailLogPort
import com.example.persistence.repository.transaction.TransactionFailLogRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
class SaveTransactionFailLogAdapter(
    private val transactionFailLogRepository: TransactionFailLogRepository
) : SaveTransactionFailLogPort {
    override fun save(transactionFailLog: TransactionFailLog): TransactionFailLog {
        return transactionFailLogRepository.save(transactionFailLog.toEntity())
            .toDomain()
    }
}
