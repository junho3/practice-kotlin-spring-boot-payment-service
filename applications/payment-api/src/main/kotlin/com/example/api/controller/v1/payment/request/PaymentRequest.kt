package com.example.api.controller.v1.payment.request

import com.example.domain.card.model.CardExpiredDate
import com.example.domain.card.model.CardInstallmentMonth
import com.example.domain.card.model.CardNo
import com.example.domain.card.model.CardValidationCode
import com.example.common.consts.ValidationConstant.CARD_EXPIRED_DATE_LENGTH
import com.example.common.consts.ValidationConstant.MAX_CARD_INSTALLMENT
import com.example.common.consts.ValidationConstant.MAX_PAYMENT_AMOUNT
import com.example.common.consts.ValidationConstant.MIN_CARD_INSTALLMENT
import com.example.common.consts.ValidationConstant.MIN_PAYMENT_AMOUNT
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.VatAmount
import com.example.domain.transaction.port.command.PaymentCommand
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

data class PaymentRequest(
    @field:NotNull(message = "카드번호는 필수 값입니다.")
    val cardNo: Long,
    @field:NotEmpty(message = "카드만료일은 필수 값입니다.")
    @field:Size(min = CARD_EXPIRED_DATE_LENGTH, max = CARD_EXPIRED_DATE_LENGTH, message = "카드만료일은 ${CARD_EXPIRED_DATE_LENGTH}자 이어야 합니다.")
    val cardExpiredDate: String,
    @field:NotNull(message = "CVC는 필수 값입니다.")
    val cardValidationCode: Int,
    @field:PositiveOrZero(message = "카드할부개월은 $MIN_CARD_INSTALLMENT 이상이어야 합니다.")
    @field:Max(value = MAX_CARD_INSTALLMENT.toLong(), message = "카드할부개월은 $MAX_CARD_INSTALLMENT 이하여야 합니다.")
    val cardInstallmentMonth: Int,
    @field:NotNull(message = "결제금액은 필수 값입니다.")
    @field:Min(value = MIN_PAYMENT_AMOUNT, message = "최소 결제금액은 ${MIN_PAYMENT_AMOUNT}원 이상입니다.")
    @field:Max(value = MAX_PAYMENT_AMOUNT, message = "최대 결제금액은 ${MAX_PAYMENT_AMOUNT}원 이하입니다.")
    val paymentAmount: Long,
    @field:PositiveOrZero(message = "VAT는 음수일 수 없습니다.")
    val vatAmount: Long?
) {
    fun toCommand(): PaymentCommand = PaymentCommand(
        cardNo = CardNo(cardNo).validate(),
        cardExpiredDate = CardExpiredDate(cardExpiredDate).validate(),
        cardValidationCode = CardValidationCode(cardValidationCode).validate(),
        cardInstallmentMonth = CardInstallmentMonth(cardInstallmentMonth).validate(),
        paymentAmount = PaymentAmount(paymentAmount).validate(),
        vatAmount = vatAmount?.let { VatAmount(it).validate(paymentAmount) }
            ?: VatAmount.of(paymentAmount)
    )
}
