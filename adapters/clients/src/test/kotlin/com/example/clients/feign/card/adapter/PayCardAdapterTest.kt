package com.example.clients.feign.card.adapter

import com.example.domain.card.model.Card
import com.example.domain.card.model.CardTransactionData
import com.example.domain.card.model.CardTransactionId
import com.example.clients.feign.card.repository.CardRepository
import com.example.common.utils.UuidUtil
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@Import(value = [PayCardAdapter::class])
@DataJpaTest
class PayCardAdapterTest(
    private val cardRepository: CardRepository,
    private val payCardAdapter: PayCardAdapter = PayCardAdapter(cardRepository)
) : DescribeSpec({

    describe("pay 메소드는") {
        context("Card 도메인을 받아서") {
            val cardTransactionData = CardTransactionData("_446PAYMENT___XXXXXXXXXXXXXXXXXXXX1234567890123456____001125777____1100000000010000____________________YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY________________________________________________________________________________________________________________________________________________________________________________")
            val card = Card(
                cardTransactionId = null,
                cardTransactionData = cardTransactionData
            )
            val cardTransactionId = CardTransactionId(UuidUtil.generateCardTransactionId())

            it("36자 cardTransactionId를 생성한다") {
                cardTransactionId.shouldBeInstanceOf<CardTransactionId>()
                cardTransactionId.value.length shouldBe 36
            }

            it("데이터를 저장한다. (API 호출 대체)") {
                val result = payCardAdapter.pay(card)

                result.shouldBeInstanceOf<Card>()
            }
        }
    }
})
