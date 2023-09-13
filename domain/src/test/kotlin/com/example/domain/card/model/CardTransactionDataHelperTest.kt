package com.example.domain.card.model

import com.example.domain.card.model.CardTransactionDataHelper.convert
import com.example.domain.card.model.CardTransactionDataHelper.emptyConvert
import com.example.common.consts.CardTransactionDataConstant.CANCEL_PAYMENT_ID_LENGTH
import com.example.common.consts.CardTransactionDataConstant.CARD_EXPIRED_DATE_LENGTH
import com.example.common.consts.CardTransactionDataConstant.CARD_INSTALLMENT_MONTH_LENGTH
import com.example.common.consts.CardTransactionDataConstant.CARD_NO_LENGTH
import com.example.common.consts.CardTransactionDataConstant.CARD_VALIDATION_CODE_LENGTH
import com.example.common.consts.CardTransactionDataConstant.ENCRYPT_CARD_DATA_LENGTH
import com.example.common.consts.CardTransactionDataConstant.PAYMENT_AMOUNT_LENGTH
import com.example.common.consts.CardTransactionDataConstant.PAYMENT_TYPE_LENGTH
import com.example.common.consts.CardTransactionDataConstant.RESERVE_LENGTH
import com.example.common.consts.CardTransactionDataConstant.TRANSACTION_ID_LENGTH
import com.example.common.consts.CardTransactionDataConstant.UNDER_BAR_CHAR
import com.example.common.consts.CardTransactionDataConstant.VAT_AMOUNT_LENGTH
import com.example.common.enums.PaymentType
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.TransactionId
import com.example.domain.transaction.model.VatAmount
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class CardTransactionDataHelperTest : DescribeSpec({

    describe("convert 메소드는") {
        context("PaymentType이 주어지면") {
            val paymentType = PaymentType.PAYMENT
            it("$PAYMENT_TYPE_LENGTH 자로 변환한다.") {
                val result = paymentType.convert()
                println(result)
                result.shouldBeInstanceOf<String>()
                result.length shouldBe PAYMENT_TYPE_LENGTH
                result shouldBe "PAYMENT___"
            }
        }

        context("TransactionId가 주어지면") {
            val transactionId = TransactionId("28448868436265221234")
            it("$TRANSACTION_ID_LENGTH 자로 변환한다.") {
                val result = transactionId.convert()
                println(result)
                result.shouldBeInstanceOf<String>()
                result.length shouldBe TRANSACTION_ID_LENGTH
                result shouldBe "28448868436265221234"
            }
        }

        context("CardNo가 주어지면") {
            val cardNo = CardNo(2275116277791)
            it("$CARD_NO_LENGTH 자로 변환한다.") {
                val result = cardNo.convert()
                result.shouldBeInstanceOf<String>()
                result.length shouldBe CARD_NO_LENGTH
                result shouldBe "2275116277791_______"
            }
        }

        context("CardInstallmentMonth가 주어지면") {
            val cardInstallmentMonth = CardInstallmentMonth(1)
            it("$CARD_INSTALLMENT_MONTH_LENGTH 자로 변환한다.") {
                val result = cardInstallmentMonth.convert()
                result.shouldBeInstanceOf<String>()
                result.length shouldBe CARD_INSTALLMENT_MONTH_LENGTH
                result shouldBe "01"
            }
        }

        context("CardExpiredDate가 주어지면") {
            val cardExpiredDate = CardExpiredDate("0822")
            it("$CARD_EXPIRED_DATE_LENGTH 자로 변환한다.") {
                val result = cardExpiredDate.convert()
                result.shouldBeInstanceOf<String>()
                result.length shouldBe CARD_EXPIRED_DATE_LENGTH
                result shouldBe "0822"
            }
        }

        context("CardValidationCode가 주어지면") {
            val cardValidationCode = CardValidationCode(777)
            it("$CARD_VALIDATION_CODE_LENGTH 자로 변환한다.") {
                val result = cardValidationCode.convert()
                result.shouldBeInstanceOf<String>()
                result.length shouldBe CARD_VALIDATION_CODE_LENGTH
                result shouldBe "777"
            }
        }

        context("PaymentAmount가 주어지면") {
            val paymentAmount = PaymentAmount(10000L)
            it("$PAYMENT_AMOUNT_LENGTH 자로 변환한다.") {
                val result = paymentAmount.convert()
                result.shouldBeInstanceOf<String>()
                result.length shouldBe PAYMENT_AMOUNT_LENGTH
                result shouldBe "_____10000"
            }
        }

        context("VatAmount가 주어지면") {
            val vatAmount = VatAmount(10000L)
            it("$VAT_AMOUNT_LENGTH 자로 변환한다.") {
                val result = vatAmount.convert()
                result.shouldBeInstanceOf<String>()
                result.length shouldBe VAT_AMOUNT_LENGTH
                result shouldBe "0000010000"
            }
        }

        context("PaymentId가 주어지면") {
            val paymentId = PaymentId("28448868436265221234")
            it("$CANCEL_PAYMENT_ID_LENGTH 자로 변환한다.") {
                val result = paymentId.convert()
                result.shouldBeInstanceOf<String>()
                result.length shouldBe CANCEL_PAYMENT_ID_LENGTH
                result shouldBe "28448868436265221234"
            }
        }

        context("EncryptCardData가 주어지면") {
            val encryptCardData = EncryptCardData("NBcNNuvpRK7S4pYx0I/Xd8g7BDnoCrjVGaK/1KDaKbA=")
            it("$ENCRYPT_CARD_DATA_LENGTH 자로 변환한다.") {
                val result = encryptCardData.convert()
                result.shouldBeInstanceOf<String>()
                result.length shouldBe ENCRYPT_CARD_DATA_LENGTH
                result shouldBe "NBcNNuvpRK7S4pYx0I/Xd8g7BDnoCrjVGaK/1KDaKbA=________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________"
            }
        }
    }

    describe("emptyConvert 메소드는") {
        context("예비필드가 주어지면") {
            it("$RESERVE_LENGTH 자로 변환한다.") {
                val result = emptyConvert(RESERVE_LENGTH, UNDER_BAR_CHAR)
                result.shouldBeInstanceOf<String>()
                result.length shouldBe RESERVE_LENGTH
                result shouldBe "_______________________________________________"
            }
        }

        context("PaymentId가 null 인 경우") {
            it("$CANCEL_PAYMENT_ID_LENGTH 자로 변환한다.") {
                val result = emptyConvert(CANCEL_PAYMENT_ID_LENGTH, UNDER_BAR_CHAR)
                result.shouldBeInstanceOf<String>()
                result.length shouldBe CANCEL_PAYMENT_ID_LENGTH
                result shouldBe "____________________"
            }
        }
    }
})
