package com.example.domain.card.model

import com.example.common.consts.ValidationConstant.MAX_CARD_INSTALLMENT
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode

@JvmInline
value class CardInstallmentMonth(
    val value: Int
) {
    fun validate(): CardInstallmentMonth {
        when {
            value > MAX_CARD_INSTALLMENT -> throw BusinessException(ErrorCode.INVALID_CARD_INSTALLMENT_MONTH)
        }

        return this
    }

    fun toStringFormat(): String {
        return when (value.toString().length) {
            1 -> value.toString().padStart(2, '0')
            else -> value.toString()
        }
    }
}
