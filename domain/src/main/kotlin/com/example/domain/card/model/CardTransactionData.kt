package com.example.domain.card.model

import com.example.domain.card.model.CardTransactionDataHelper.convert
import com.example.domain.card.model.CardTransactionDataHelper.emptyConvert
import com.example.common.consts.CardTransactionDataConstant.CANCEL_PAYMENT_ID_LENGTH
import com.example.common.consts.CardTransactionDataConstant.DATA_LENGTH
import com.example.common.consts.CardTransactionDataConstant.PAYMENT_TYPE_LENGTH
import com.example.common.consts.CardTransactionDataConstant.RESERVE_LENGTH
import com.example.common.consts.CardTransactionDataConstant.TRANSACTION_ID_LENGTH
import com.example.common.consts.CardTransactionDataConstant.UNDER_BAR_CHAR
import com.example.common.enums.PaymentType
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.VatAmount

@JvmInline
value class CardTransactionData(
    val value: String
) {
    companion object {
        fun of(
            paymentType: PaymentType,
            paymentId: PaymentId,
            cardNo: CardNo,
            cardInstallmentMonth: CardInstallmentMonth,
            cardExpiredDate: CardExpiredDate,
            cardValidationCode: CardValidationCode,
            paymentAmount: PaymentAmount,
            vatAmount: VatAmount,
            payPaymentId: PaymentId? = null,
            encryptCardData: EncryptCardData
        ): CardTransactionData {
            val data = ""
                .plus(paymentType.convert())
                .plus(paymentId.convert())
                .plus(cardNo.convert())
                .plus(cardInstallmentMonth.convert())
                .plus(cardExpiredDate.convert())
                .plus(cardValidationCode.convert())
                .plus(paymentAmount.convert())
                .plus(vatAmount.convert())
                .plus(payPaymentId?.convert() ?: emptyConvert(CANCEL_PAYMENT_ID_LENGTH, UNDER_BAR_CHAR))
                .plus(encryptCardData.convert())
                .plus(emptyConvert(RESERVE_LENGTH, UNDER_BAR_CHAR))

            return CardTransactionData(
                data.length.toString()
                    .padStart(DATA_LENGTH, UNDER_BAR_CHAR)
                    .plus(data)
            )
        }
    }

    val paymentId: String
        get() = this.value.substring(
            DATA_LENGTH.plus(PAYMENT_TYPE_LENGTH),
            DATA_LENGTH.plus(PAYMENT_TYPE_LENGTH).plus(TRANSACTION_ID_LENGTH)
        )
}
