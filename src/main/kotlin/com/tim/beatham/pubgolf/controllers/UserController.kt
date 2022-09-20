package com.tim.beatham.pubgolf.controllers

import com.tim.beatham.pubgolf.models.User
import com.tim.beatham.pubgolf.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController {

    @Autowired
    private lateinit var usersRepository: UserRepository

    @GetMapping("/")
    fun getAllUsers(): List<User> {
        return usersRepository.findAll()
    }
}