package com.boseyo.backend.service

import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.Response
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.util.*

@Service
class EmailService(@Autowired val redisService: RedisService, @Autowired val htmlTemplateEngine: TemplateEngine) {
    @Value("\${SENDGRID_API_KEY}") val apiKey: String = ""

    fun sendEmailForm(email: String) {
        val from = Email("admin@ssda.dawoony.com", "SSDA 관리자")
        val subject = "SSDA 인증코드 발송"
        val to = Email(email)
        val content = Content("text/html", createEmail(email))
        val mail = Mail(from, subject, to, content)
        val sg = SendGrid(apiKey)
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            val response: Response = sg.api(request)
            println(response.statusCode)
            println(response.body)
            println(response.headers)
        } catch (e: Exception) {
            println(e.message)
            throw e
        }

    }

    fun createEmail(email: String):String {
        if (redisService.existData(email)) {
            redisService.deleteData(email)
        }

        val context = Context()

        context.setVariable("email", email)
        context.setVariable("token", createToken(email))

        return htmlTemplateEngine.process("mail/mail", context)
    }

    fun createToken(email: String):String {
        val emailToken = UUID.randomUUID().toString()
        redisService.setDataExpire(email, emailToken, java.time.Duration.ofHours(24))
        return emailToken
    }

    fun confirmEmail(email: String, emailToken: String): ConfirmEmailResult {
        if (!redisService.existData(email)) {
            return ConfirmEmailResult(false, "이메일 인증정보가 존재하지 않습니다.")
        }
        val redisEmailToken = redisService.getData(email) ?: return ConfirmEmailResult(false, "이메일 인증정보가 존재하지 않습니다.")
        if (redisEmailToken != emailToken) {
            return ConfirmEmailResult(false, "이메일 인증정보가 일치하지 않습니다.")
        }
        else {
            redisService.deleteData(email)
            return ConfirmEmailResult(true, "이메일 인증이 완료되었습니다.")
        }
    }
}

data class ConfirmEmailResult(val result: Boolean, val message: String)