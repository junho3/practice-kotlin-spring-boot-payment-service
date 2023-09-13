package com.example.clients.feign.card.adapter

import com.example.domain.card.model.Card
import com.example.domain.card.model.CardTransactionData
import com.example.domain.card.model.CardTransactionId
import com.example.clients.feign.card.entity.CardStatus
import com.example.clients.feign.card.entity.toEntity
import com.example.clients.feign.card.repository.CardRepository
import com.example.domain.transaction.model.PaymentId
import com.example.common.utils.UuidUtil
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@Import(value = [RollbackCardAdapter::class])
@DataJpaTest
class RollbackCardAdapterTest(
    private val cardRepository: CardRepository,
    private val rollbackCardAdapter: RollbackCardAdapter = RollbackCardAdapter(cardRepository)
) : DescribeSpec({

    describe("rollback 메소드는") {
        val paymentId = PaymentId("52556253621307013055")
        val cardTransactionData = CardTransactionData("_446PAYMENT___${paymentId.value}2275116277791_______001022123_____100000000000909____________________6utzihSDhf3UdT+5vW3o3s669kkY7tmoVDt1go5ft2k=_______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________")
        val card = Card(
            cardTransactionId = null,
            cardTransactionData = cardTransactionData
        )
        val cardTransactionId = CardTransactionId(UuidUtil.generateCardTransactionId())

        cardRepository.save(card.toEntity(cardTransactionId))

        context("paymentId가 동일한 데이터가 존재할 경우") {
            it("Rollback 데이터를 생성한다.") {
                val card = rollbackCardAdapter.rollback(paymentId)
                card.shouldBeInstanceOf<Card>()

                val rollbackCard = cardRepository.findById(card.cardTransactionId!!.value).get()

                rollbackCard.partnerTransactionId shouldBe paymentId.value
                rollbackCard.status shouldBe CardStatus.ROLLBACK
            }
        }
    }
})
