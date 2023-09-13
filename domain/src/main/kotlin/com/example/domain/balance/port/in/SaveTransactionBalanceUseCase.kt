package com.example.domain.balance.port.`in`

import com.example.domain.balance.model.TransactionBalance
import com.example.domain.balance.port.command.SaveTransactionBalanceCommand

interface SaveTransactionBalanceUseCase {
    fun save(saveTransactionBalanceCommand: SaveTransactionBalanceCommand): TransactionBalance
}
