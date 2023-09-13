package com.example.domain.card.model

import com.example.common.consts.ValidationConstant
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class CardNoTest : DescribeSpec({

    describe("CardNo validate 메소드는") {
        context("카드 번호 길이가 ${ValidationConstant.MIN_CARD_LENGTH} 보다 작을 경우") {
            val cardNo = CardNo(1)
            it("Exception을 발생한다.") {
                val exception = shouldThrow<BusinessException> {
                    cardNo.validate()
                }
                exception.errorCode shouldBe ErrorCode.INVALID_CARD_NO_LENGTH
            }
        }

        context("카드 번호 길이가 ${ValidationConstant.MAX_CARD_LENGTH} 보다 클 경우") {
            val cardNo = CardNo(11111111111111111)
            it("Exception을 발생한다.") {
                val exception = shouldThrow<BusinessException> {
                    cardNo.validate()
                }
                exception.errorCode shouldBe ErrorCode.INVALID_CARD_NO_LENGTH
            }
        }

        context("카드 번호 길이가 ${ValidationConstant.MIN_CARD_LENGTH}보다 크고, ${ValidationConstant.MAX_CARD_LENGTH} 보다 작은 경우") {
            val cardNo = CardNo(111111111111111)
            it("Exception이 발생하지 않는다.") {
                shouldNotThrow<BusinessException> {
                    cardNo.validate()
                }
            }
        }
    }

    describe("masking 메소드는") {
        context("Long 타입 카드번호가 주어질 경우") {
            val cardNo = CardNo(2275116277791)
            it("앞 6자리와 뒤 3자리를 제외한 나머지를 마스킹처리 한다.") {
                val result = cardNo.masking()
                result.shouldBeInstanceOf<String>()
                result shouldBe "22751*****791"
            }
        }
    }
})
