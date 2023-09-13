package com.example.domain.transaction.port.`in`

import com.example.domain.transaction.model.SaveTransaction
import com.example.domain.transaction.port.command.SaveCancelCommand

interface SaveCancelUseCase {
    fun save(command: SaveCancelCommand): SaveTransaction
}
