package com.example.domain.balance.port.out

import com.example.domain.balance.model.SaveTransactionBalance

interface SaveTransactionBalancePort {
    fun save(saveTransactionBalance: SaveTransactionBalance): SaveTransactionBalance
}
