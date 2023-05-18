package com.boseyo.backend.service

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@RequiredArgsConstructor
@Service
class RedisService(@Autowired private val template: RedisTemplate<String, String>?) {
    //redis 기본적인 CRUD 로직
    fun getData(key: String?): String? {
        val valueOperations = template!!.opsForValue()
        return valueOperations[key!!]
    }

    fun existData(key: String): Boolean {
        return template!!.hasKey(key)
    }

    fun setDataExpire(key: String?, value: String?, duration: Duration) {
        val valueOperations = template!!.opsForValue()
        val expireDuration: Duration = Duration.ofSeconds(duration.toSeconds())
        if (key != null && value != null) {
            valueOperations.set(key, value, expireDuration)
        }
    }

    fun deleteData(key: String) {
        template!!.delete(key)
    }
}