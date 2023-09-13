package com.example.domain.transaction.model

import com.example.common.consts.ValidationConstant
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class PaymentAmountTest : DescribeSpec({

    describe("PaymentAmount validate 메소드는") {
        context("결제 금액이 ${ValidationConstant.MIN_PAYMENT_AMOUNT} 보다 작을 경우") {
            val paymentAmount = PaymentAmount(99)
            it("Exception을 발생한다.") {
                val exception = shouldThrow<BusinessException> {
                    paymentAmount.validate()
                }
                exception.errorCode shouldBe ErrorCode.INVALID_MIN_PAYMENT_AMOUNT
            }
        }

        context("결제 금액이 ${ValidationConstant.MAX_PAYMENT_AMOUNT} 보다 클 경우") {
            val paymentAmount = PaymentAmount(11111111111)
            it("Exception을 발생한다.") {
                val exception = shouldThrow<BusinessException> {
                    paymentAmount.validate()
                }
                exception.errorCode shouldBe ErrorCode.INVALID_MAX_PAYMENT_AMOUNT
            }
        }

        context("결제 금액이 ${ValidationConstant.MIN_PAYMENT_AMOUNT}보다 크고, ${ValidationConstant.MAX_PAYMENT_AMOUNT} 보다 작은 경우") {
            val paymentAmount = PaymentAmount(11111111)
            it("Exception이 발생하지 않는다.") {
                shouldNotThrow<BusinessException> {
                    paymentAmount.validate()
                }
            }
        }
    }
})
