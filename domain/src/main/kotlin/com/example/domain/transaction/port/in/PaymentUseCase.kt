package com.example.domain.transaction.port.`in`

import com.example.domain.transaction.port.command.PaymentCommand
import com.example.domain.transaction.port.result.PaymentResult

interface PaymentUseCase {
    fun payment(command: PaymentCommand): PaymentResult
}
