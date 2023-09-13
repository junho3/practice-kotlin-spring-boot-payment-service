package com.example.domain.transaction.service

import com.example.domain.card.port.out.RollbackCardPort
import com.example.domain.transaction.model.TransactionFailLog
import com.example.domain.transaction.port.command.FailOverCommand
import com.example.domain.transaction.port.`in`.FailOverUseCase
import com.example.domain.transaction.port.out.SaveTransactionFailLogPort
import org.springframework.stereotype.Service

@Service
class FailOverService(
    private val saveTransactionFailLogPort: SaveTransactionFailLogPort,
    private val rollbackCardPort: RollbackCardPort
) : FailOverUseCase {
    override fun failOver(command: FailOverCommand) {
        rollbackCardPort.rollback(command.paymentId) // 망취소(카드사 롤백) 호출

        saveTransactionFailLogPort.save(
            TransactionFailLog(
                transactionId = command.transactionId.value,
                paymentId = command.paymentId.value,
                code = command.code,
                reason = command.reason
            )
        )
    }
}
