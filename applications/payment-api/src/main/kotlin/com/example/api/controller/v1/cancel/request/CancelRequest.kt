package com.example.api.controller.v1.cancel.request

import com.example.common.consts.CardTransactionDataConstant.TRANSACTION_ID_LENGTH
import com.example.common.consts.ValidationConstant.MAX_PAYMENT_AMOUNT
import com.example.common.consts.ValidationConstant.MIN_PAYMENT_AMOUNT
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.TransactionId
import com.example.domain.transaction.model.VatAmount
import com.example.domain.transaction.port.command.CancelCommand
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

data class CancelRequest(
    @field:NotEmpty(message = "트랜잭션 ID는 필수 값입니다.")
    @field:Size(max = TRANSACTION_ID_LENGTH, min = TRANSACTION_ID_LENGTH, message = "트랜잭션 ID는 $TRANSACTION_ID_LENGTH 자 입니다.")
    val transactionId: String,
    @field:Min(value = MIN_PAYMENT_AMOUNT, message = "최소 결제금액은 ${MIN_PAYMENT_AMOUNT}원 이상입니다.")
    @field:Max(value = MAX_PAYMENT_AMOUNT, message = "최대 결제금액은 ${MAX_PAYMENT_AMOUNT}원 이하입니다.")
    val paymentAmount: Long,
    @field:PositiveOrZero(message = "VAT는 음수일 수 없습니다.")
    val vatAmount: Long?
) {
    fun toCommand(): CancelCommand = CancelCommand(
        transactionId = TransactionId(transactionId),
        paymentAmount = PaymentAmount(paymentAmount).validate(),
        vatAmount = vatAmount?.let { VatAmount(it).validate(paymentAmount) }
            ?: VatAmount.of(paymentAmount)
    )
}
