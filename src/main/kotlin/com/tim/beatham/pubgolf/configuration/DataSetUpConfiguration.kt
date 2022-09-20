package com.tim.beatham.pubgolf.configuration

import com.tim.beatham.pubgolf.models.User
import com.tim.beatham.pubgolf.repositories.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataSetUpConfiguration {
    @Bean
    fun databaseInitializer(userRepository: UserRepository)
        = ApplicationRunner {
            userRepository.save(User(name = "Tim Beatham", email = "beatham.tim@gmail.com", password = "some-password"))
    }
}