package com.example.domain.transaction.port.out

import com.example.domain.transaction.model.TransactionFailLog

interface SaveTransactionFailLogPort {
    fun save(transactionFailLog: TransactionFailLog): TransactionFailLog
}
