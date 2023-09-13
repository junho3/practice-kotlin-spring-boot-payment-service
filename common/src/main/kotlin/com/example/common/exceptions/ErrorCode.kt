package com.example.common.exceptions

import com.example.common.consts.ValidationConstant.MAX_PAYMENT_AMOUNT
import com.example.common.consts.ValidationConstant.MIN_PAYMENT_AMOUNT

/**
 * 에러코드.
 */
enum class ErrorCode(
    val code: String,
    val status: Int,
    val message: String
) {
    // 공통 오류
    BAD_REQUEST("PAYMENT_API_0000", 400, "서버가 요청 구문을 인식하지 못했습니다."),
    UNSUPPORTED_MEDIA_TYPE("PAYMENT_API_0001", 400, "지원하지 않는 미디어 타입입니다."),
    NOT_ACCEPTABLE("PAYMENT_API_0002", 400, "지원하지 않는 컨텐츠 타입입니다."),
    REQUEST_DATA_NOT_FOUND("PAYMENT_API_0003", 400, "요청값이 존재하지 않습니다."),
    FEIGN_CLIENT_REQUEST_FAIL("PAYMENT_API_0004", 400, "Feign Client 요청이 실패했습니다."),
    UNAUTHORIZED("PAYMENT_API_0005", 401, "인증이 필요합니다."),
    FORBIDDEN("PAYMENT_API_0006", 403, "권한이 없습니다."),
    ENTITY_NOT_FOUND("PAYMENT_API_0007", 404, "요청한 리소스를 찾을 수 없습니다."),
    REQUEST_MAPPING_NOT_FOUND("PAYMENT_API_0008", 404, "허용되지 않은 URL입니다."),
    INVALID_INPUT_VALUE("PAYMENT_API_0009", 422, "입력 값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR("PAYMENT_API_0010", 500, "서버에 오류가 발생하여 요청을 수행하지 못했습니다."),
    GATEWAY_TIMEOUT("PAYMENT_API_0011", 504, "서버가 응답하지 않습니다."),

    INVALID_CARD_NO_LENGTH("PAYMENT_API_0012", 400, "카드번호 길이가 잘못되었습니다."),
    INVALID_CARD_EXPIRED_DATE_LENGTH("PAYMENT_API_0013", 400, "카드만료기간 길이가 잘못되었습니다."),
    INVALID_CARD_VALIDATION_CODE_LENGTH("PAYMENT_API_0014", 400, "카드검증번호 길이가 잘못되었습니다."),
    INVALID_CARD_INSTALLMENT_MONTH("PAYMENT_API_0015", 400, "카드할부개월이 잘못되었습니다."),
    INVALID_MIN_PAYMENT_AMOUNT("PAYMENT_API_0016", 400, "결제금액 ${MIN_PAYMENT_AMOUNT}원 이하는 불가능합니다."),
    INVALID_MAX_PAYMENT_AMOUNT("PAYMENT_API_0017", 400, "결제금액 ${MAX_PAYMENT_AMOUNT}원 이상은 불가능합니다."),
    INVALID_VAT_AMOUNT("PAYMENT_API_0018", 400, "부가가치세는 결제금액보다 작아야합니다."),
    NOT_EXIST_TRANSACTION("PAYMENT_API_0019", 400, "거래건이 존재하지 않습니다."),
    INVALID_CANCEL_AMOUNT("PAYMENT_API_0020", 400, "취소 금액과 VAT가 잘못되었습니다."),
    NOT_EXIST_PAYMENT("PAYMENT_API_0021", 400, "결제건이 존재하지 않습니다."),
    NOT_EXIST_CARD("CARD_API_0001", 400, "카드정보가 존재하지 않습니다.")
    ;

    companion object {
        /**
         * Http Status Code에 알맞은 공통 오류 코드 객체 code 값을 리턴.
         *
         * @param httpStatusCode Http Status Code
         * @return 공통 오류 코드 객체 code 값
         * */
        fun of(httpStatusCode: Int): ErrorCode {
            return when (httpStatusCode) {
                400 -> BAD_REQUEST
                401 -> UNAUTHORIZED
                403 -> FORBIDDEN
                404 -> ENTITY_NOT_FOUND
                406 -> NOT_ACCEPTABLE
                415 -> UNSUPPORTED_MEDIA_TYPE
                422 -> INVALID_INPUT_VALUE
                500 -> INTERNAL_SERVER_ERROR
                504 -> GATEWAY_TIMEOUT
                else -> INTERNAL_SERVER_ERROR
            }
        }
    }
}
