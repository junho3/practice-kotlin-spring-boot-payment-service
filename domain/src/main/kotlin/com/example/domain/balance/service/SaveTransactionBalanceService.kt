package com.example.domain.balance.service

import com.example.domain.balance.model.PaymentBalance
import com.example.domain.balance.model.SaveTransactionBalance
import com.example.domain.balance.model.TransactionBalance
import com.example.domain.balance.port.command.SaveTransactionBalanceCommand
import com.example.domain.balance.port.`in`.SaveTransactionBalanceUseCase
import com.example.domain.balance.port.out.SaveTransactionBalancePort
import com.example.common.enums.TransactionType
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class SaveTransactionBalanceService(
    private val saveTransactionBalancePort: SaveTransactionBalancePort
) : SaveTransactionBalanceUseCase {
    @Transactional
    override fun save(saveTransactionBalanceCommand: SaveTransactionBalanceCommand): TransactionBalance = with(saveTransactionBalanceCommand) {
        return saveTransactionBalancePort.save(
            SaveTransactionBalance(
                transactionId = transactionId,
                transactionType = TransactionType.PAYMENT,
                paymentTransactionAmount = payment.paymentAmount.value,
                paymentVatAmount = payment.vatAmount.value,
                paymentBalance = PaymentBalance(
                    paymentId = payment.paymentId,
                    paymentAmount = payment.paymentAmount.value,
                    vatAmount = payment.vatAmount.value,
                    paymentBalanceAmount = payment.paymentAmount.value,
                    vatBalanceAmount = payment.vatAmount.value,
                    encryptCardData = encryptCardData,
                    cardInstallmentMonth = cardInstallmentMonth.value,
                    paymentType = payment.paymentType
                )
            )
        )
    }
}
