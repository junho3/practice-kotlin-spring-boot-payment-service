package com.example.domain.card.model

import com.example.common.consts.ValidationConstant.CARD_VALIDATION_CODE_LENGTH
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode

@JvmInline
value class CardValidationCode(
    val value: Int
) {
    fun validate(): CardValidationCode {
        when {
            value.toString().length != CARD_VALIDATION_CODE_LENGTH -> throw BusinessException(ErrorCode.INVALID_CARD_VALIDATION_CODE_LENGTH)
        }

        return this
    }
}
