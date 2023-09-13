package com.example.persistence.adapter.balance

import com.example.domain.balance.model.SaveTransactionBalance
import com.example.persistence.entity.balance.toEntity
import com.example.persistence.entity.balance.toSaveDomain
import com.example.domain.balance.port.out.SaveTransactionBalancePort
import com.example.persistence.repository.balance.TransactionBalanceRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
class SaveTransactionBalanceAdapter(
    private val transactionBalanceRepository: TransactionBalanceRepository
) : SaveTransactionBalancePort {
    override fun save(saveTransactionBalance: SaveTransactionBalance): SaveTransactionBalance = with(saveTransactionBalance) {
        return transactionBalanceRepository.save(saveTransactionBalance.toEntity())
            .toSaveDomain(paymentId = paymentBalance.paymentId)
    }
}
