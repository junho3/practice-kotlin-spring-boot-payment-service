package com.example.domain.transaction.port.`in`

import com.example.domain.transaction.port.command.FailOverCommand

interface FailOverUseCase {
    fun failOver(command: FailOverCommand)
}
