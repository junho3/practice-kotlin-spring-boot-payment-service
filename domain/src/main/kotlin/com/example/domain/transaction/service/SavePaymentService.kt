package com.example.domain.transaction.service

import com.example.domain.balance.model.PaymentBalance
import com.example.domain.balance.model.SaveTransactionBalance
import com.example.domain.balance.port.out.SaveTransactionBalancePort
import com.example.common.enums.PaymentStatus
import com.example.common.enums.PaymentType
import com.example.common.enums.TransactionType
import com.example.domain.transaction.model.Payment
import com.example.domain.transaction.model.PaymentCard
import com.example.domain.transaction.model.SaveTransaction
import com.example.domain.transaction.port.command.SavePaymentCommand
import com.example.domain.transaction.port.`in`.SavePaymentUseCase
import com.example.domain.transaction.port.out.SaveTransactionPort
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Transactional
@Service
class SavePaymentService(
    private val saveTransactionPort: SaveTransactionPort,
    private val saveTransactionBalancePort: SaveTransactionBalancePort
) : SavePaymentUseCase {
    override fun save(command: SavePaymentCommand): SaveTransaction = with(command) {
        val transaction = saveTransaction(command)
        saveTransactionBalance(command)

        return transaction
    }

    private fun saveTransaction(command: SavePaymentCommand): SaveTransaction = with(command) {
        return saveTransactionPort.save(
            saveTransaction = SaveTransaction(
                transactionId = transactionId,
                payment = Payment(
                    paymentId = paymentId,
                    paymentAmount = paymentAmount,
                    vatAmount = vatAmount,
                    paymentType = PaymentType.PAYMENT,
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

    private fun saveTransactionBalance(command: SavePaymentCommand) = with(command) {
        saveTransactionBalancePort.save(
            SaveTransactionBalance(
                transactionId = transactionId,
                transactionType = TransactionType.PAYMENT,
                paymentTransactionAmount = paymentAmount.value,
                paymentVatAmount = vatAmount.value,
                paymentBalance = PaymentBalance(
                    paymentId = paymentId,
                    paymentAmount = paymentAmount.value,
                    vatAmount = vatAmount.value,
                    paymentBalanceAmount = paymentAmount.value,
                    vatBalanceAmount = vatAmount.value,
                    encryptCardData = encryptCardData,
                    cardInstallmentMonth = cardInstallmentMonth.value,
                    paymentType = PaymentType.PAYMENT
                )
            )
        )
    }
}
