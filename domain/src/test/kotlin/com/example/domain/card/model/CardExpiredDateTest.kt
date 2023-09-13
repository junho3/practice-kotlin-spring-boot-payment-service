package com.example.domain.card.model

import com.example.common.consts.ValidationConstant
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class CardExpiredDateTest : DescribeSpec({

    describe("CardExpiredDate validate 메소드는") {
        context("카드만료일 길이가 ${ValidationConstant.CARD_EXPIRED_DATE_LENGTH} 인 경우") {
            val cardExpiredDate = CardExpiredDate("0822")
            it("Exception을 발생하지 않는다.") {
                shouldNotThrow<BusinessException> {
                    cardExpiredDate.validate()
                }
            }
        }

        context("카드만료일 길이가 ${ValidationConstant.CARD_EXPIRED_DATE_LENGTH}가 아닌 경우") {
            val cardExpiredDate = CardExpiredDate("082")
            it("Exception을 발생한다.") {
                val exception = shouldThrow<BusinessException> {
                    cardExpiredDate.validate()
                }

                exception.errorCode shouldBe ErrorCode.INVALID_CARD_EXPIRED_DATE_LENGTH
            }
        }
    }
})
