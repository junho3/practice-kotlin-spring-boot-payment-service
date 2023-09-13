package com.example.clients.feign.card.adapter

import com.example.domain.card.model.Card
import com.example.domain.card.model.CardTransactionId
import com.example.domain.card.port.out.RollbackCardPort
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import com.example.clients.feign.card.entity.CardStatus
import com.example.clients.feign.card.entity.toDomain
import com.example.clients.feign.card.entity.toEntity
import com.example.clients.feign.card.repository.CardRepository
import com.example.domain.transaction.model.PaymentId
import com.example.common.utils.UuidUtil
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
class RollbackCardAdapter(
    private val cardRepository: CardRepository
) : RollbackCardPort {
    override fun rollback(paymentId: PaymentId): Card {
        // API 호출 대체
        val card = cardRepository.findByPartnerTransactionIdAndStatus(
            paymentId.value,
            CardStatus.COMPLETE
        )
            ?.toDomain()
            ?: throw BusinessException(ErrorCode.NOT_EXIST_CARD)

        return cardRepository.save(
            card.toEntity(
                transactionId = CardTransactionId(UuidUtil.generateCardTransactionId()),
                status = CardStatus.ROLLBACK
            )
        )
            .toDomain()
    }
}
