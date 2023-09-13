package com.example.domain.transaction.port.`in`

import com.example.domain.transaction.port.command.CancelCommand
import com.example.domain.transaction.port.result.CancelResult

interface CancelUseCase {
    fun cancel(command: CancelCommand): CancelResult
}
