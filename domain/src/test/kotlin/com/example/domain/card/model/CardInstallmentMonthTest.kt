package com.example.domain.card.model

import com.example.common.consts.ValidationConstant
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class CardInstallmentMonthTest : DescribeSpec({

    describe("CardInstallmentMonth validate 메소드는") {
        context("카드할부개월이 ${ValidationConstant.MAX_CARD_INSTALLMENT} 보다 작은 경우") {
            val cardInstallmentMonth = CardInstallmentMonth(11)
            it("Exception을 발생하지 않는다.") {
                shouldNotThrow<BusinessException> {
                    cardInstallmentMonth.validate()
                }
            }
        }

        context("카드할부개월이 ${ValidationConstant.MAX_CARD_INSTALLMENT} 보다 큰 경우") {
            val cardInstallmentMonth = CardInstallmentMonth(13)
            it("Exception을 발생한다.") {
                val exception = shouldThrow<BusinessException> {
                    cardInstallmentMonth.validate()
                }

                exception.errorCode shouldBe ErrorCode.INVALID_CARD_INSTALLMENT_MONTH
            }
        }
    }

    describe("toStringFormat 메소드는") {
        context("카드할부개월이 한자리 수인 경우") {
            val cardInstallmentMonth = CardInstallmentMonth(0)
            it("문자열 2자리로 변경하여 리턴한다.") {
                val result = cardInstallmentMonth.toStringFormat()
                result.shouldBeInstanceOf<String>()
                result shouldBe "00"
            }
        }

        context("카드할부개월이 두자리 수인 경우") {
            val cardInstallmentMonth = CardInstallmentMonth(10)
            it("문자열 2자리로 변경하여 리턴한다.") {
                val result = cardInstallmentMonth.toStringFormat()
                result.shouldBeInstanceOf<String>()
                result shouldBe "10"
            }
        }
    }
})
