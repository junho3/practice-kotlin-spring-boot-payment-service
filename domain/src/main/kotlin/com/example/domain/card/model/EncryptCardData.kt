package com.example.domain.card.model

import com.example.common.consts.ValidationConstant.ENCRIPT_CARD_DATA_SEPERATER
import com.example.common.utils.AES128Util

@JvmInline
value class EncryptCardData(
    val value: String
) {
    companion object {
        fun of(
            cardNo: CardNo,
            cardExpiredDate: CardExpiredDate,
            cardValidationCode: CardValidationCode
        ): EncryptCardData {
            val message = listOf(
                cardNo.value.toString(),
                cardExpiredDate.value,
                cardValidationCode.value
            )
                .joinToString(ENCRIPT_CARD_DATA_SEPERATER)

            return EncryptCardData(AES128Util.encrypt(message))
        }
    }

    fun decryptCardNo(): CardNo {
        return CardNo(AES128Util.decrypt(value).split(ENCRIPT_CARD_DATA_SEPERATER)[0].toLong())
    }

    fun decryptCardExpiredDate(): CardExpiredDate {
        return CardExpiredDate(AES128Util.decrypt(value).split(ENCRIPT_CARD_DATA_SEPERATER)[1])
    }

    fun decryptCardValidationCode(): CardValidationCode {
        return CardValidationCode(AES128Util.decrypt(value).split(ENCRIPT_CARD_DATA_SEPERATER)[2].toInt())
    }
}
