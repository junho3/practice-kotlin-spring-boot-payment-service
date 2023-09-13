package com.example.domain.transaction.port.out

import com.example.domain.transaction.model.SaveTransaction

interface SaveTransactionPort {
    fun save(saveTransaction: SaveTransaction): SaveTransaction

    fun savePayment(saveTransaction: SaveTransaction): SaveTransaction
}
