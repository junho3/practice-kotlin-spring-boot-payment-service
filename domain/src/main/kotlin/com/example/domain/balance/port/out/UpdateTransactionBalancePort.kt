package com.example.domain.balance.port.out

import com.example.domain.balance.model.SaveTransactionBalance

interface UpdateTransactionBalancePort {
    fun update(saveTransactionBalance: SaveTransactionBalance): SaveTransactionBalance
}
