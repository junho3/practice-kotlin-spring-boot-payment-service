package com.example.common.utils

import com.example.common.utils.AES128Util
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class AES128UtilTest : DescribeSpec({
    val message = "2275116277791|0822|777"
    val digest = "NBcNNuvpRK7S4pYx0I/Xd8g7BDnoCrjVGaK/1KDaKbA="

    describe("encrypt 메소드는") {
        context("평문을 받으면") {
            it("AES128 암호화된 데이터를 리턴한다.") {
                val data = AES128Util.encrypt(message)

                data.shouldBeInstanceOf<String>()
                data shouldBe digest
            }
        }
    }

    describe("decrypt") {
        context("암호화된 데이터를 받으면") {
            it("복호화된 평문 데이터를 리턴한다.") {
                val data = AES128Util.decrypt(digest)

                data.shouldBeInstanceOf<String>()
                data shouldBe message
            }
        }
    }
})
