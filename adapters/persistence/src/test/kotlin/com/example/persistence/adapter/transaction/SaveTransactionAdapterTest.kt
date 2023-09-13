package com.example.persistence.adapter.transaction

import com.example.domain.card.model.CardExpiredDate
import com.example.domain.card.model.CardInstallmentMonth
import com.example.domain.card.model.CardNo
import com.example.domain.card.model.CardTransactionData
import com.example.domain.card.model.CardTransactionId
import com.example.domain.card.model.CardValidationCode
import com.example.domain.card.model.EncryptCardData
import com.example.common.enums.PaymentStatus
import com.example.common.enums.PaymentType
import com.example.common.exceptions.BusinessException
import com.example.common.exceptions.ErrorCode
import com.example.domain.transaction.model.Payment
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.PaymentCard
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.SaveTransaction
import com.example.domain.transaction.model.Transaction
import com.example.domain.transaction.model.TransactionId
import com.example.domain.transaction.model.VatAmount
import com.example.persistence.repository.transaction.TransactionRepository
import com.example.common.utils.UuidUtil
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@Import(value = [SaveTransactionAdapter::class])
@DataJpaTest
class SaveTransactionAdapterTest(
    private val transactionRepository: TransactionRepository,
    private val saveTransactionAdapter: SaveTransactionAdapter = SaveTransactionAdapter(transactionRepository = transactionRepository)
) : DescribeSpec({
    describe("save 메소드는") {
        context("Transaction 도메인과 Card 도메인을 받아서") {
            val transactionId = UuidUtil.generate()
            val paymentId = UuidUtil.generate()
            val saveTransaction = SaveTransaction(
                TransactionId(transactionId),
                Payment(
                    PaymentId(paymentId),
                    PaymentAmount(10000),
                    VatAmount(1000),
                    PaymentType.PAYMENT,
                    PaymentStatus.SUCCESS,
                    PaymentCard(
                        CardTransactionId(UuidUtil.generate()),
                        CardTransactionData("TEST"),
                        EncryptCardData.of(CardNo(2275116277791), CardExpiredDate("1022"), CardValidationCode(777)),
                        CardInstallmentMonth(0)
                    )
                )
            )

            it("데이터를 저장한다") {
                val transaction = saveTransactionAdapter.save(saveTransaction)

                val result = transactionRepository.findById(transactionId)
                    .orElseThrow { throw BusinessException(ErrorCode.NOT_EXIST_TRANSACTION) }

                transaction.shouldBeInstanceOf<Transaction>()
                transaction.transactionId.value shouldBe result.transactionId
                transaction.payment.paymentId.value shouldBe paymentId
            }
        }
    }
})
