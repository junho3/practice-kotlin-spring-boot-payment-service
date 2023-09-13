package com.example.domain.card.model

import com.example.common.consts.MaskingConstant.CARD_END_MASKING_LENGTH
import com.example.common.consts.MaskingConstant.CARD_MASKING_CHAR
import com.example.common.consts.MaskingConstant.CARD_START_MASKING_LENGTH
import com.example.common.consts.ValidationConstant.MAX_CARD_LENGTH
import com.example.common.consts.ValidationConstant.MIN_CARD_LENGTH
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode

@JvmInline
value class CardNo(
    val value: Long
) {
    fun validate(): CardNo {
        when {
            value.toString().length < MIN_CARD_LENGTH -> throw BusinessException(ErrorCode.INVALID_CARD_NO_LENGTH)
            value.toString().length > MAX_CARD_LENGTH -> throw BusinessException(ErrorCode.INVALID_CARD_NO_LENGTH)
        }
        return this
    }

    fun masking(): String {
        val length = value.toString().length
        return value.toString().replaceRange(
            CARD_START_MASKING_LENGTH,
            length.minus(CARD_END_MASKING_LENGTH),
            CARD_MASKING_CHAR.repeat(length.minus(CARD_START_MASKING_LENGTH).minus(CARD_END_MASKING_LENGTH))
        )
    }
}
