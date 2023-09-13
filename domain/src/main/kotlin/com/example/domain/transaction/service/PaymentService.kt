package com.example.domain.transaction.service

import com.example.domain.card.model.Card
import com.example.domain.card.model.EncryptCardData
import com.example.domain.card.port.command.PayCardCommand
import com.example.domain.card.port.`in`.PayCardUseCase
import com.example.common.enums.TransactionFailCode
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.SaveTransaction
import com.example.domain.transaction.model.TransactionId
import com.example.domain.transaction.port.command.FailOverCommand
import com.example.domain.transaction.port.command.PaymentCommand
import com.example.domain.transaction.port.command.SavePaymentCommand
import com.example.domain.transaction.port.`in`.DoAsyncJobsUseCase
import com.example.domain.transaction.port.`in`.FailOverUseCase
import com.example.domain.transaction.port.`in`.PaymentUseCase
import com.example.domain.transaction.port.`in`.SavePaymentUseCase
import com.example.domain.transaction.port.result.PaymentResult
import com.example.common.utils.UuidUtil
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val payCardUseCase: PayCardUseCase,
    private val savePaymentUseCase: SavePaymentUseCase,
    private val failOverUseCase: FailOverUseCase,
    private val doAsyncJobsUseCase: DoAsyncJobsUseCase
) : PaymentUseCase {
    companion object {
        val log = KotlinLogging.logger {}
    }

    override fun payment(command: PaymentCommand): PaymentResult = with(command) {
        val transactionId = TransactionId(UuidUtil.generate())
        val paymentId = PaymentId(UuidUtil.generate())
        // TODO transactionId와 paymentId 중복이 있는지 검증 필요
        // TODO transaction과 payment를 먼저 인서트하고, 카드사 요청 후 완료 상태 업데이트로 순서를 잡아야할 수 있음

        // 카드정보 암호화
        val encryptCardData = EncryptCardData.of(cardNo, cardExpiredDate, cardValidationCode)

        // 카드사 요청
        val card = payCard(transactionId, paymentId, command, encryptCardData)

        // 데이터 저장
        val transaction = save(transactionId, paymentId, command, card, encryptCardData)

        // 5. 코루틴 후처리 예시 코드
        doAsyncJobsUseCase.doAsyncJobs()

        return PaymentResult(
            transactionId = transaction.transactionId,
            paymentId = transaction.payment.paymentId,
            cardTransactionData = card.cardTransactionData
        )
    }

    private fun payCard(
        transactionId: TransactionId,
        paymentId: PaymentId,
        command: PaymentCommand,
        encryptCardData: EncryptCardData
    ): Card {
        return runCatching {
            payCardUseCase.pay(PayCardCommand.of(paymentId, command, encryptCardData))
        }.getOrElse {
            log.error { "카드사 통신 중 에러 발생 ${it.cause}" }
            failOverUseCase.failOver(FailOverCommand(transactionId, paymentId, TransactionFailCode.CARD_API_ERROR, it.message))
            throw it
        }
    }

    private fun save(
        transactionId: TransactionId,
        paymentId: PaymentId,
        command: PaymentCommand,
        card: Card,
        encryptCardData: EncryptCardData
    ): SaveTransaction {
        return runCatching {
            savePaymentUseCase.save(SavePaymentCommand.of(transactionId, paymentId, command, card, encryptCardData))
        }.getOrElse {
            log.error { "데이터 저장 중 에러 발생 ${it.cause}" }
            failOverUseCase.failOver(FailOverCommand(transactionId, paymentId, TransactionFailCode.DB_ERROR, it.message))
            throw it
        }
    }
}
