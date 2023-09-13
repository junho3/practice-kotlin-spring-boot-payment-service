package com.example.domain.balance.service

import com.example.domain.balance.model.PaymentBalance
import com.example.domain.balance.model.SaveTransactionBalance
import com.example.domain.balance.port.command.UpdateTransactionBalanceCommand
import com.example.domain.balance.port.`in`.UpdateTransactionBalanceUseCase
import com.example.domain.balance.port.out.UpdateTransactionBalancePort
import com.example.common.enums.PaymentStatus
import com.example.common.enums.PaymentType
import com.example.domain.transaction.port.out.FindTransactionPort
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UpdateTransactionBalanceService(
    private val findTransactionPort: FindTransactionPort,
    private val updateTransactionBalancePort: UpdateTransactionBalancePort
) : UpdateTransactionBalanceUseCase {
    @Transactional
    override fun update(command: UpdateTransactionBalanceCommand): SaveTransactionBalance = with(command) {
        val transaction = findTransactionPort.findByTransactionId(transactionId)

        /**
         * WRITE(MASTER) / READ(SLAVE) DB가 분리된 운영환경에서는 WRITE DB 인서트 또는 업데이트 후 READ DB 까지 전파되는 시간이 있음
         * 본 환경에서는 WRITE와 READ 동일한 DB를 사용하기 때문에 결제 취소한 payment 데이터를 인위적으로 제외
         **/
        val canceledPayments = transaction.payments
            .filterNot { it.paymentId.value == payment.paymentId.value }
            .filter { it.paymentType == PaymentType.CANCEL }
            .filter { it.paymentStatus == PaymentStatus.SUCCESS }

        return updateTransactionBalancePort.update(
            SaveTransactionBalance(
                transactionId = transactionId,
                transactionType = transactionType,
                paymentTransactionAmount = transaction.paymentAmount,
                paymentVatAmount = transaction.vatAmount,
                cancelTransactionAmount = canceledPayments.sumOf { it.paymentAmount.value }
                    .plus(payment.paymentAmount.value),
                cancelVatAmount = canceledPayments.sumOf { it.vatAmount.value }
                    .plus(payment.vatAmount.value),
                paymentBalance = PaymentBalance(
                    paymentId = payment.paymentId,
                    paymentAmount = payment.paymentAmount.value,
                    vatAmount = payment.vatAmount.value,
                    paymentBalanceAmount = transaction.paymentAmount
                        .minus(canceledPayments.sumOf { it.paymentAmount.value })
                        .minus(payment.paymentAmount.value),
                    vatBalanceAmount = transaction.vatAmount
                        .minus(canceledPayments.sumOf { it.vatAmount.value })
                        .minus(payment.vatAmount.value),
                    encryptCardData = encryptCardData,
                    cardInstallmentMonth = cardInstallmentMonth.value,
                    paymentType = payment.paymentType
                )
            )
        )
    }
}
