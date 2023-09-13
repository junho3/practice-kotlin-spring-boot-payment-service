package com.example.domain.transaction.port.out

import com.example.domain.transaction.model.FindTransaction
import com.example.domain.transaction.model.TransactionId

interface FindTransactionPort {
    fun findByTransactionId(transactionId: TransactionId): FindTransaction
}
