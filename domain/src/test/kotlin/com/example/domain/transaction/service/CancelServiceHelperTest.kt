package com.example.domain.transaction.service

import com.example.common.enums.TransactionType
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class CancelServiceHelperTest : DescribeSpec({

    describe("getTransactionType 메소드는") {
        context("결제금액 20000원, 결제VAT 2000원, 취소완료금액 1000원, 취소완료VAT 100원, 취소금액 2000원, 취소VAT 200원이 주어졌을 때") {
            it("${TransactionType.PARTIAL_CANCEL} 를 리턴한다.") {
                val transactionType = CancelServiceHelper.getTransactionType(
                    paymentAmount = 20000L,
                    vatAmount = 2000L,
                    canceledPaymentAmount = 1000L,
                    canceledVatAmount = 100L,
                    cancelPaymentAmount = 2000L,
                    cancelVatAmount = 200L
                )

                transactionType shouldBe TransactionType.PARTIAL_CANCEL
            }
        }

        context("결제금액 20000원, 결제VAT 2000원, 취소완료금액 15000원, 취소완료VAT 1500원, 취소금액 5000원, 취소VAT 500원이 주어졌을 때") {
            it("${TransactionType.CANCEL} 를 리턴한다.") {
                val transactionType = CancelServiceHelper.getTransactionType(
                    paymentAmount = 20000L,
                    vatAmount = 2000L,
                    canceledPaymentAmount = 15000L,
                    canceledVatAmount = 1500L,
                    cancelPaymentAmount = 5000L,
                    cancelVatAmount = 500L
                )

                transactionType shouldBe TransactionType.CANCEL
            }
        }

        context("결제금액 20000원, 결제VAT 2000원, 취소완료금액 20000원, 취소완료VAT 2000원, 취소금액 5000원, 취소VAT 500원이 주어졌을 때") {
            it("BusinessException 를 리턴한다.") {
                val exception = shouldThrow<BusinessException> {
                    CancelServiceHelper.getTransactionType(
                        paymentAmount = 20000L,
                        vatAmount = 2000L,
                        canceledPaymentAmount = 20000L,
                        canceledVatAmount = 2000L,
                        cancelPaymentAmount = 5000L,
                        cancelVatAmount = 500L
                    )
                }

                exception.errorCode shouldBe ErrorCode.INVALID_CANCEL_AMOUNT
            }
        }
    }
})
