package com.example.domain.card.port.out

import com.example.domain.card.model.Card

interface CancelCardPort {
    fun cancel(card: Card): Card
}
