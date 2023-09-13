package com.example.domain.balance.port.`in`

import com.example.domain.balance.model.SaveTransactionBalance
import com.example.domain.balance.port.command.UpdateTransactionBalanceCommand

interface UpdateTransactionBalanceUseCase {
    fun update(command: UpdateTransactionBalanceCommand): SaveTransactionBalance
}
