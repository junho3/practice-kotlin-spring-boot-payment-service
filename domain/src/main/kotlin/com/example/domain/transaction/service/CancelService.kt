package com.example.domain.transaction.service

import com.example.domain.card.model.Card
import com.example.domain.card.model.EncryptCardData
import com.example.domain.card.port.command.CancelCardCommand
import com.example.domain.card.port.`in`.CancelCardUseCase
import com.example.common.enums.TransactionFailCode
import com.example.common.enums.TransactionType
import com.example.domain.transaction.model.FindTransaction
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.SaveTransaction
import com.example.domain.transaction.port.command.CancelCommand
import com.example.domain.transaction.port.command.FailOverCommand
import com.example.domain.transaction.port.`in`.CancelUseCase
import com.example.domain.transaction.port.`in`.DoAsyncJobsUseCase
import com.example.domain.transaction.port.`in`.FailOverUseCase
import com.example.domain.transaction.port.`in`.SaveCancelUseCase
import com.example.domain.transaction.port.out.FindTransactionPort
import com.example.domain.transaction.port.result.CancelResult
import com.example.common.utils.UuidUtil
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class CancelService(
    private val findTransactionPort: FindTransactionPort,
    private val cancelCardUseCase: CancelCardUseCase,
    private val saveCancelUseCase: SaveCancelUseCase,
    private val failOverUseCase: FailOverUseCase,
    private val doAsyncJobsUseCase: DoAsyncJobsUseCase
) : CancelUseCase {
    companion object {
        val log = KotlinLogging.logger {}
    }

    override fun cancel(command: CancelCommand): CancelResult = with(command) {
        // 1. transactionId로 데이터 조회
        val findTransaction = findTransactionPort.findByTransactionId(transactionId)

        // 2. 결제금액과 취소완료금액, 취소시도금액으로 전체취소 또는 부분취소 판단
        // 결제금액 - 취소시도금액 = 0 전체취소
        // 결제금액 - 취소시도금액 > 0 부분취소
        // 결제금액 - 취소완료금액 - 취소시도금액 = 0 전체취소
        val transactionType = CancelServiceHelper.getTransactionType(
            paymentAmount = findTransaction.paymentAmount,
            vatAmount = findTransaction.vatAmount,
            canceledPaymentAmount = findTransaction.canceledPaymentAmount,
            canceledVatAmount = findTransaction.canceledVatAmount,
            cancelPaymentAmount = paymentAmount.value,
            cancelVatAmount = vatAmount.value
        )

        val paymentId = PaymentId(UuidUtil.generate())

        // 3. 카드사 호출
        val card = cancelCard(paymentId, command, findTransaction)

        // 4. 데이터 적재
        val transaction = save(paymentId, command, transactionType, card, findTransaction.paymentEncryptCardData)

        // 5. 코루틴 후처리 예시 코드
        doAsyncJobsUseCase.doAsyncJobs()

        return CancelResult(
            transaction.transactionId,
            paymentId,
            card.cardTransactionData
        )
    }

    private fun cancelCard(
        paymentId: PaymentId,
        command: CancelCommand,
        transaction: FindTransaction
    ): Card = with(command) {
        return runCatching {
            cancelCardUseCase.cancel(CancelCardCommand.of(paymentId, command, transaction))
        }.getOrElse {
            log.error { "카드사 통신 중 에러 발생 ${it.cause}" }
            failOverUseCase.failOver(FailOverCommand(transactionId, paymentId, TransactionFailCode.CARD_API_ERROR, it.message))
            throw it
        }
    }

    private fun save(
        paymentId: PaymentId,
        command: CancelCommand,
        transactionType: TransactionType,
        card: Card,
        encryptCardData: EncryptCardData
    ): SaveTransaction = with(command) {
        return runCatching {
            saveCancelUseCase.save(
                com.example.domain.transaction.port.command.SaveCancelCommand.of(transactionId, transactionType, paymentId, command, card, encryptCardData)
            )
        }.getOrElse {
            log.error { "데이터 저장 중 에러 발생 ${it.cause}" }
            failOverUseCase.failOver(FailOverCommand(transactionId, paymentId, TransactionFailCode.DB_ERROR, it.message))
            throw it
        }
    }
}
