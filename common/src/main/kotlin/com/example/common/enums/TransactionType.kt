package com.example.common.enums

enum class TransactionType(val description: String) {
    PAYMENT("결제"),
    PARTIAL_CANCEL("부분취소"),
    CANCEL("전체취소"),
}
