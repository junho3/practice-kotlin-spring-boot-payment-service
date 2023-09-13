package com.example.domain.card.model

import com.example.common.consts.ValidationConstant.CARD_EXPIRED_DATE_LENGTH
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode

@JvmInline
value class CardExpiredDate(
    val value: String
) {
    fun validate(): CardExpiredDate {
        when {
            value.length != CARD_EXPIRED_DATE_LENGTH -> throw BusinessException(ErrorCode.INVALID_CARD_EXPIRED_DATE_LENGTH)
        }

        return this
    }
}
