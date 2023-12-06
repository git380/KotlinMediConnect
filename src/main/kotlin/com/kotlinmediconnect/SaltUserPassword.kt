package com.kotlinmediconnect

import org.apache.commons.codec.digest.DigestUtils


class SaltUserPassword {
    fun getDigest(before1: String?, before2: String?): String = DigestUtils.sha256Hex(
            "9b7a8d5fb348bd4949536cdc5b8d30c426b80d2fe30c5c308e2cdec422ae3244"
                    + DigestUtils.sha256Hex(before1)
                    + DigestUtils.sha256Hex(before2)
        )
}