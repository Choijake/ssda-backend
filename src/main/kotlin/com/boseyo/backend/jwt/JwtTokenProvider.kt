package com.boseyo.backend.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors

@Component
class JwtTokenProvider(
    @param:Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.token-validity-in-seconds}") tokenValidityInSeconds: Long,
    @Value("\${jwt.refresh-token-validity-in-seconds}") refreshTokenValidityInSeconds: Long
) : InitializingBean {
    private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)
    private val tokenValidityInMilliseconds: Long
    private val refreshTokenValidityInMilliseconds: Long // 추가: refresh token의 유효기간

    private var key: Key? = null

    init {
        tokenValidityInMilliseconds = tokenValidityInSeconds * 10000000000000000
        refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000 // 추가: refresh token의 유효기간을 밀리초로 계산
    }

    companion object {
        private const val AUTHORITIES_KEY = "auth"
    }

    override fun afterPropertiesSet() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    }

    fun createAccessToken(authentication: Authentication): String {
        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))

        val validity = Date(Date().time + tokenValidityInMilliseconds)

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
    }

    fun createRefreshToken(): String {
        val now = Date()
        val validity = Date(now.time + refreshTokenValidityInMilliseconds)
        return Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getAuthentication(token: String?): Authentication {
        val claims = Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        val authorities: Collection<GrantedAuthority> = Arrays
            .stream(claims[AUTHORITIES_KEY].toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            .map { role: String? -> SimpleGrantedAuthority(role) }
            .collect(Collectors.toList())

        val principal = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

        fun validateToken(token: String?): Boolean {
            try {
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
                return true
            } catch (e: SecurityException) {
                logger.info("잘못된 JWT 서명입니다.")
            } catch (e: MalformedJwtException) {
                logger.info("잘못된 JWT 서명입니다.")
            } catch (e: ExpiredJwtException) {
                logger.info("만료된 JWT 토큰입니다.")
            } catch (e: UnsupportedJwtException) {
                logger.info("지원되지 않는 JWT 토큰입니다.")
            } catch (e: IllegalArgumentException) {
                logger.info("JWT 토큰이 잘못되었습니다.")
            }
            return false
        }
}