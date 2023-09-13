package com.example.domain.card.port.out

import com.example.domain.card.model.Card
import com.example.domain.transaction.model.PaymentId

interface RollbackCardPort {
    fun rollback(paymentId: PaymentId): Card
}
