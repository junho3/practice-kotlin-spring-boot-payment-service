package com.example.domain.card.port.`in`

import com.example.domain.card.model.Card
import com.example.domain.card.port.command.PayCardCommand

interface PayCardUseCase {
    fun pay(command: PayCardCommand): Card
}
