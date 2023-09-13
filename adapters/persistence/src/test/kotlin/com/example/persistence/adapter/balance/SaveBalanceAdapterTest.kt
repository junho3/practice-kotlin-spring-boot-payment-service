package com.example.persistence.adapter.balance

import com.example.domain.balance.model.PaymentBalance
import com.example.domain.balance.model.SaveTransactionBalance
import com.example.domain.balance.model.TransactionBalance
import com.example.persistence.repository.balance.TransactionBalanceRepository
import com.example.domain.card.model.EncryptCardData
import com.example.common.enums.PaymentType
import com.example.common.enums.TransactionType
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.TransactionId
import com.example.common.utils.UuidUtil
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@Import(value = [SaveTransactionBalanceAdapter::class])
@DataJpaTest
class SaveBalanceAdapterTest(
    private val transactionBalanceRepository: TransactionBalanceRepository,
    private val transactionBalanceAdapter: SaveTransactionBalanceAdapter = SaveTransactionBalanceAdapter(transactionBalanceRepository)
) : DescribeSpec({
    describe("save 메소드는") {
        context("TransactionBalance 도메인을 받아서") {
            val transactionId = UuidUtil.generate()
            val paymentId = UuidUtil.generate()

            val transactionBalance = SaveTransactionBalance(
                transactionId = TransactionId(transactionId),
                transactionType = TransactionType.PAYMENT,
                paymentTransactionAmount = 1000L,
                paymentVatAmount = 100L,
                paymentBalance = PaymentBalance(
                    paymentId = PaymentId(paymentId),
                    paymentAmount = 1000L,
                    vatAmount = 100L,
                    paymentBalanceAmount = 1000L,
                    vatBalanceAmount = 100L,
                    cardInstallmentMonth = 0,
                    encryptCardData = EncryptCardData("6utzihSDhf3UdT+5vW3o3s669kkY7tmoVDt1go5ft2k="),
                    paymentType = PaymentType.PAYMENT
                )
            )

            it("데이터를 저장한다.") {
                val result = transactionBalanceAdapter.save(transactionBalance)

                result.shouldBeInstanceOf<TransactionBalance>()
                result.transactionId.value shouldBe transactionId
            }
        }
    }
})
