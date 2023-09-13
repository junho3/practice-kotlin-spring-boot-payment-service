package com.example.domain.card.model

import com.example.domain.card.model.CardExpiredDate
import com.example.domain.card.model.CardNo
import com.example.domain.card.model.CardValidationCode
import com.example.domain.card.model.EncryptCardData
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class EncryptCardDataTest : DescribeSpec({
    val cardNo = 2275116277791
    val cardExpiredDate = "0822"
    val cardValidationCode = 777
    val digest = "NBcNNuvpRK7S4pYx0I/Xd8g7BDnoCrjVGaK/1KDaKbA="

    describe("of 메소드는") {
        context("CardNo, CardExpiredDate, CardValidationCode를 파라미터로 받아서") {
            it("CardExpiredDate 객체를 리턴한다.") {
                val encryptCardData = EncryptCardData.of(CardNo(cardNo), CardExpiredDate(cardExpiredDate), CardValidationCode(cardValidationCode))

                encryptCardData.shouldBeInstanceOf<EncryptCardData>()
                encryptCardData.value shouldBe digest
            }
        }
    }

    describe("decryptCardNo 메소드는") {
        context("EncriptCardData 객체가 주어졌을 때") {
            val encryptCardData = EncryptCardData.of(CardNo(cardNo), CardExpiredDate(cardExpiredDate), CardValidationCode(cardValidationCode))
            it("복호화된 CardNo 객체를 리턴한다.") {
                val result = encryptCardData.decryptCardNo()

                result.shouldBeInstanceOf<CardNo>()
                result.value shouldBe cardNo
            }
        }
    }

    describe("decryptCardExpiredDate 메소드는") {
        context("EncriptCardData 객체가 주어졌을 때") {
            val encryptCardData = EncryptCardData.of(CardNo(cardNo), CardExpiredDate(cardExpiredDate), CardValidationCode(cardValidationCode))
            it("복호화된 CardExpiredDate 객체를 리턴한다.") {
                val result = encryptCardData.decryptCardExpiredDate()

                result.shouldBeInstanceOf<CardExpiredDate>()
                result.value shouldBe cardExpiredDate
            }
        }
    }

    describe("decryptCardValidationCode 메소드는") {
        context("EncriptCardData 객체가 주어졌을 때") {
            val encryptCardData = EncryptCardData.of(CardNo(cardNo), CardExpiredDate(cardExpiredDate), CardValidationCode(cardValidationCode))
            it("복호화된 CardValidationCode 객체를 리터한다.") {
                val result = encryptCardData.decryptCardValidationCode()

                result.shouldBeInstanceOf<CardValidationCode>()
                result.value shouldBe cardValidationCode
            }
        }
    }
})
