package com.example.domain.transaction.model

import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class VatAmountTest : DescribeSpec({

    describe("VatAmount validate 메소드는") {
        context("부가가치세가 결제금액보다 작은 경우") {
            val paymentAmount = 1000L
            val vatAmount = VatAmount(91)
            it("Exception을 발생하지 않는다.") {
                shouldNotThrow<BusinessException> {
                    vatAmount.validate(paymentAmount)
                }
            }
        }

        context("부가가치세가 결제금액보다 큰 경우") {
            val paymentAmount = 1000L
            val vatAmount = VatAmount(9000)
            it("Exception을 발생한다.") {
                val exception = shouldThrow<BusinessException> {
                    vatAmount.validate(paymentAmount)
                }

                exception.errorCode shouldBe ErrorCode.INVALID_VAT_AMOUNT
            }
        }
    }

    describe("VatAmount of 컴페니온 메소드는") {
        context("부가가치세가 NULL이고, 결제금액이 1000원인 경우") {
            val vatAmount = VatAmount.of(1000L)
            it("Long 타입 91원을 리턴한다.") {
                vatAmount.value.shouldBeInstanceOf<Long>()
                vatAmount.value shouldBe 91
            }
        }
    }
})
