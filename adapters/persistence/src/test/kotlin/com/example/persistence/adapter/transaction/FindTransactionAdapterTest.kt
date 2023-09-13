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
import com.example.domain.transaction.model.FindTransaction
import com.example.domain.transaction.model.Payment
import com.example.domain.transaction.model.PaymentAmount
import com.example.domain.transaction.model.PaymentCard
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.SaveTransaction
import com.example.domain.transaction.model.TransactionId
import com.example.domain.transaction.model.VatAmount
import com.example.persistence.entity.transaction.toEntity
import com.example.persistence.repository.transaction.TransactionRepository
import com.example.common.utils.UuidUtil
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@Import(value = [FindTransactionAdapter::class])
@DataJpaTest
class FindTransactionAdapterTest(
    private val transactionRepository: TransactionRepository,
    private val findTransactionAdapter: FindTransactionAdapter = FindTransactionAdapter(transactionRepository = transactionRepository)
) : DescribeSpec({
    lateinit var transactionId: String
    lateinit var paymentId: String

    beforeSpec {
        transactionId = UuidUtil.generate()
        paymentId = UuidUtil.generate()
    }

    describe("findByTransactionId 메소드는") {
        context("transactionId와 매칭되는 데이터가 존재할 경우") {
            val transaction = SaveTransaction(
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

            transactionRepository.save(transaction.toEntity())

            it("FindTransaction 객체를 리턴한다.") {
                shouldNotThrow<BusinessException> {
                    findTransactionAdapter.findByTransactionId(TransactionId(transactionId))
                }

                val result = findTransactionAdapter.findByTransactionId(TransactionId(transactionId))
                result.shouldBeInstanceOf<FindTransaction>()
                result.transactionId.value shouldBe transactionId
            }
        }

        context("transactionId와 매칭되는 데이터가 존재하지 않을 경우") {
            it("BusinessException을 발생한다.") {
                val result = shouldThrow<BusinessException> {
                    findTransactionAdapter.findByTransactionId(TransactionId(UuidUtil.generate()))
                }

                result.errorCode shouldBe ErrorCode.NOT_EXIST_TRANSACTION
            }
        }
    }
})
