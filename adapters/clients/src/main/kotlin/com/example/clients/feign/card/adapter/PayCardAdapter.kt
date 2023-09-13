package com.example.clients.feign.card.adapter

import com.example.domain.card.model.Card
import com.example.domain.card.model.CardTransactionId
import com.example.domain.card.port.out.PayCardPort
import com.example.clients.feign.card.entity.toDomain
import com.example.clients.feign.card.entity.toEntity
import com.example.clients.feign.card.repository.CardRepository
import com.example.common.utils.UuidUtil
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
class PayCardAdapter(
    private val cardRepository: CardRepository
) : PayCardPort {
    override fun pay(card: Card): Card {
        // API 호출 대체
        return cardRepository.save(
            card.toEntity(transactionId = CardTransactionId(UuidUtil.generateCardTransactionId()))
        )
            .toDomain()
    }
}
