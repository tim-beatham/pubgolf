package com.tim.beatham.pubgolf.repositories

import com.tim.beatham.pubgolf.models.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UserRepository : MongoRepository<User, String> {
    fun findByEmail(email: String): Optional<User>
}