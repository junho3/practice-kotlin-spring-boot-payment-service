package com.example.domain.transaction.service

import com.example.domain.balance.model.PaymentBalance
import com.example.domain.balance.model.SaveTransactionBalance
import com.example.domain.balance.port.out.UpdateTransactionBalancePort
import com.example.common.enums.PaymentStatus
import com.example.common.enums.PaymentType
import com.example.domain.transaction.model.Payment
import com.example.domain.transaction.model.PaymentCard
import com.example.domain.transaction.model.SaveTransaction
import com.example.domain.transaction.port.command.SaveCancelCommand
import com.example.domain.transaction.port.`in`.SaveCancelUseCase
import com.example.domain.transaction.port.out.FindTransactionPort
import com.example.domain.transaction.port.out.SaveTransactionPort
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Transactional
@Service
class SaveCancelService(
    private val saveTransactionPort: SaveTransactionPort,
    private val findTransactionPort: FindTransactionPort,
    private val updateTransactionBalancePort: UpdateTransactionBalancePort
) : SaveCancelUseCase {
    override fun save(command: SaveCancelCommand): SaveTransaction = with(command) {
        val transaction = saveTransaction(command)
        updateTransactionBalance(command)

        return transaction
    }

    private fun saveTransaction(command: SaveCancelCommand): SaveTransaction = with(command) {
        return saveTransactionPort.savePayment(
            saveTransaction = SaveTransaction(
                transactionId = transactionId,
                payment = Payment(
                    paymentId = paymentId,
                    paymentAmount = paymentAmount,
                    vatAmount = vatAmount,
                    paymentType = PaymentType.CANCEL,
                    paymentStatus = PaymentStatus.SUCCESS,
                    paymentCard = PaymentCard(
                        cardTransactionId = card.cardTransactionId!!,
                        cardTransactionData = card.cardTransactionData,
                        encryptCardData = encryptCardData,
                        cardInstallmentMonth = cardInstallmentMonth
                    )
                )
            )
        )
    }

    private fun updateTransactionBalance(command: SaveCancelCommand) = with(command) {
        val transaction = findTransactionPort.findByTransactionId(transactionId)

        /**
         * WRITE(MASTER) / READ(SLAVE) DB가 분리된 운영환경에서는 WRITE DB 인서트 또는 업데이트 후 READ DB 까지 전파되는 시간이 있음
         * 본 환경에서는 WRITE와 READ 동일한 DB를 사용하기 때문에 결제 취소한 payment 데이터를 인위적으로 제외
         **/
        val canceledPayments = transaction.payments
            .filterNot { it.paymentId.value == paymentId.value }
            .filter { it.paymentType == PaymentType.CANCEL }
            .filter { it.paymentStatus == PaymentStatus.SUCCESS }

        updateTransactionBalancePort.update(
            SaveTransactionBalance(
                transactionId = transactionId,
                transactionType = transactionType,
                paymentTransactionAmount = transaction.paymentAmount,
                paymentVatAmount = transaction.vatAmount,
                cancelTransactionAmount = canceledPayments.sumOf { it.paymentAmount.value }
                    .plus(paymentAmount.value),
                cancelVatAmount = canceledPayments.sumOf { it.vatAmount.value }
                    .plus(vatAmount.value),
                paymentBalance = PaymentBalance(
                    paymentId = paymentId,
                    paymentAmount = paymentAmount.value,
                    vatAmount = vatAmount.value,
                    paymentBalanceAmount = transaction.paymentAmount
                        .minus(canceledPayments.sumOf { it.paymentAmount.value })
                        .minus(paymentAmount.value),
                    vatBalanceAmount = transaction.vatAmount
                        .minus(canceledPayments.sumOf { it.vatAmount.value })
                        .minus(vatAmount.value),
                    encryptCardData = encryptCardData,
                    cardInstallmentMonth = cardInstallmentMonth.value,
                    paymentType = PaymentType.CANCEL
                )
            )
        )
    }
}
