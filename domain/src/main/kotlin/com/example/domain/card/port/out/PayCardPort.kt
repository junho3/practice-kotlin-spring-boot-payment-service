package com.example.domain.card.port.out

import com.example.domain.card.model.Card

interface PayCardPort {
    fun pay(card: Card): Card
}
