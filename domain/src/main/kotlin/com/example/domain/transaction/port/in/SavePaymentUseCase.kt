package com.example.domain.transaction.port.`in`

import com.example.domain.transaction.model.SaveTransaction
import com.example.domain.transaction.port.command.SavePaymentCommand

interface SavePaymentUseCase {
    fun save(command: SavePaymentCommand): SaveTransaction
}
