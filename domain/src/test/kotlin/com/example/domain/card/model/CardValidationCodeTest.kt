package com.example.domain.card.model

import com.example.common.consts.ValidationConstant
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class CardValidationCodeTest : DescribeSpec({

    describe("CardValidationCode validate 메소드는") {
        context("카드검증코드 길이가 ${ValidationConstant.CARD_VALIDATION_CODE_LENGTH} 인 경우") {
            val cardValidationCode = CardValidationCode(111)
            it("Exception을 발생하지 않는다.") {
                shouldNotThrow<BusinessException> {
                    cardValidationCode.validate()
                }
            }
        }

        context("카드검증코드 길이가 ${ValidationConstant.CARD_VALIDATION_CODE_LENGTH}가 아닌 경우") {
            val cardValidationCode = CardValidationCode(1111)
            it("Exception을 발생한다.") {
                val exception = shouldThrow<BusinessException> {
                    cardValidationCode.validate()
                }

                exception.errorCode shouldBe ErrorCode.INVALID_CARD_VALIDATION_CODE_LENGTH
            }
        }
    }
})
