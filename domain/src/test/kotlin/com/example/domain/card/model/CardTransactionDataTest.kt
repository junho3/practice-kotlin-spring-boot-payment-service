package com.example.domain.card.model

import com.example.common.consts.CardTransactionDataConstant.TOTAL_CARD_TRANSACTION_DATA_LENGTH
import com.example.common.consts.CardTransactionDataConstant.TRANSACTION_ID_LENGTH
import com.example.common.enums.PaymentType
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.VatAmount
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class CardTransactionDataTest : DescribeSpec({
    describe("of 메소드는") {
        context("CardTransactoinData 객체가 주어졌을 때") {
            val cardTransactionData = CardTransactionData.of(
                paymentType = PaymentType.PAYMENT,
                paymentId = PaymentId("28448868436265221234"),
                cardNo = CardNo(2275116277791),
                cardInstallmentMonth = CardInstallmentMonth(1),
                cardExpiredDate = CardExpiredDate("0822"),
                cardValidationCode = CardValidationCode(777),
                paymentAmount = PaymentAmount(10000L),
                vatAmount = VatAmount(100L),
                payPaymentId = null,
                encryptCardData = EncryptCardData("NBcNNuvpRK7S4pYx0I/Xd8g7BDnoCrjVGaK/1KDaKbA=")
            )

            it("$TOTAL_CARD_TRANSACTION_DATA_LENGTH 자 평문으로 변환한다.") {
                cardTransactionData.shouldBeInstanceOf<CardTransactionData>()
                cardTransactionData.value.length shouldBe TOTAL_CARD_TRANSACTION_DATA_LENGTH
                cardTransactionData.value shouldBe "_446PAYMENT___284488684362652212342275116277791_______010822777_____100000000000100____________________NBcNNuvpRK7S4pYx0I/Xd8g7BDnoCrjVGaK/1KDaKbA=_______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________"
            }
        }
    }

    describe("paymentId Getter는") {
        context("CardTransactoinData 객체가 주어졌을 때") {
            val paymentId = "28448868436265221234"
            val cardTransactionData = CardTransactionData.of(
                paymentType = PaymentType.PAYMENT,
                paymentId = PaymentId(paymentId),
                cardNo = CardNo(2275116277791),
                cardInstallmentMonth = CardInstallmentMonth(1),
                cardExpiredDate = CardExpiredDate("0822"),
                cardValidationCode = CardValidationCode(777),
                paymentAmount = PaymentAmount(10000L),
                vatAmount = VatAmount(100L),
                payPaymentId = null,
                encryptCardData = EncryptCardData("NBcNNuvpRK7S4pYx0I/Xd8g7BDnoCrjVGaK/1KDaKbA=")
            )

            it("paymentId 문자열 형식 $TRANSACTION_ID_LENGTH 자를 리턴한다.") {
                cardTransactionData.paymentId.shouldBeInstanceOf<String>()
                cardTransactionData.paymentId.length shouldBe TRANSACTION_ID_LENGTH
                cardTransactionData.paymentId shouldBe paymentId
            }
        }
    }
})
