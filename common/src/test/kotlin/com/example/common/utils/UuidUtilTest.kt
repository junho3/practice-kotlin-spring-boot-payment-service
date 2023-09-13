package com.example.common.utils

import com.example.common.utils.UuidUtil
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class UuidUtilTest : DescribeSpec({
    describe("generate 메소드는") {
        val result = UuidUtil.generate()
        it("20자리 UUID를 생성해준다") {
            result.shouldBeInstanceOf<String>()
            result.length shouldBe 20
        }
    }
})
