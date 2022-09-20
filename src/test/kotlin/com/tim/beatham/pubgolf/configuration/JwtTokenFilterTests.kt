package com.tim.beatham.pubgolf.configuration

import com.ninjasquad.springmockk.MockkBean
import com.tim.beatham.pubgolf.controllers.UserController
import com.tim.beatham.pubgolf.repositories.UserRepository
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.h2.engine.User
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest
@ContextConfiguration(classes = [UserController::class])
class JwtTokenFilterTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockkBean
    private lateinit var userRepository: UserRepository

    @Test
    fun `Returns 404 when there is no bearer token`() {
        every { userRepository.findAll() } returns listOf()

        val result = mvc.perform(get("/api/users/"))
            .andExpect(status().isUnauthorized)
            .andReturn()

        assertThat(result.response.contentAsString).isSubstringOf("Authorization Failed")
    }
}