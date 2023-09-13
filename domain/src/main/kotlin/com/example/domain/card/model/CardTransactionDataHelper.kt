package com.example.domain.card.model

import com.example.common.consts.CardTransactionDataConstant
import com.example.common.enums.PaymentType
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.TransactionId
import com.example.domain.transaction.model.VatAmount

object CardTransactionDataHelper {
    fun PaymentType.convert(): String {
        return this.name.padEnd(
            CardTransactionDataConstant.PAYMENT_TYPE_LENGTH,
            CardTransactionDataConstant.UNDER_BAR_CHAR
        )
    }

    fun TransactionId.convert(): String {
        return this.value.padEnd(
            CardTransactionDataConstant.TRANSACTION_ID_LENGTH,
            CardTransactionDataConstant.UNDER_BAR_CHAR
        )
    }

    fun CardNo.convert(): String {
        return this.value.toString().padEnd(
            CardTransactionDataConstant.CARD_NO_LENGTH,
            CardTransactionDataConstant.UNDER_BAR_CHAR
        )
    }

    fun CardInstallmentMonth.convert(): String {
        return this.toStringFormat().padStart(
            CardTransactionDataConstant.CARD_INSTALLMENT_MONTH_LENGTH,
            CardTransactionDataConstant.ZERO_CHAR
        )
    }

    fun CardExpiredDate.convert(): String {
        return this.value.padEnd(
            CardTransactionDataConstant.CARD_EXPIRED_DATE_LENGTH,
            CardTransactionDataConstant.UNDER_BAR_CHAR
        )
    }

    fun CardValidationCode.convert(): String {
        return this.value.toString().padEnd(
            CardTransactionDataConstant.CARD_VALIDATION_CODE_LENGTH,
            CardTransactionDataConstant.UNDER_BAR_CHAR
        )
    }

    fun PaymentAmount.convert(): String {
        return this.value.toString().padStart(
            CardTransactionDataConstant.PAYMENT_AMOUNT_LENGTH,
            CardTransactionDataConstant.UNDER_BAR_CHAR
        )
    }

    fun VatAmount.convert(): String {
        return this.value.toString().padStart(
            CardTransactionDataConstant.VAT_AMOUNT_LENGTH,
            CardTransactionDataConstant.ZERO_CHAR
        )
    }

    fun PaymentId.convert(): String {
        return this.value.padEnd(
            CardTransactionDataConstant.CANCEL_PAYMENT_ID_LENGTH,
            CardTransactionDataConstant.UNDER_BAR_CHAR
        )
    }

    fun EncryptCardData.convert(): String {
        return this.value.padEnd(
            CardTransactionDataConstant.ENCRYPT_CARD_DATA_LENGTH,
            CardTransactionDataConstant.UNDER_BAR_CHAR
        )
    }

    fun emptyConvert(length: Int, char: Char): String {
        return "".padEnd(length, char)
    }
}
