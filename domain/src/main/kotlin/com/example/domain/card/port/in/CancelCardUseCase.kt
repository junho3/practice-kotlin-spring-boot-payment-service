package com.example.domain.card.port.`in`

import com.example.domain.card.model.Card
import com.example.domain.card.port.command.CancelCardCommand

interface CancelCardUseCase {
    fun cancel(command: CancelCardCommand): Card
}
