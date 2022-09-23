package com.tim.beatham.pubgolf.controllers

import com.tim.beatham.pubgolf.configuration.JwtTokenUtil
import com.tim.beatham.pubgolf.controllers.exception.UserAlreadyExistsException
import com.tim.beatham.pubgolf.controllers.exception.UserNotFoundException
import com.tim.beatham.pubgolf.controllers.exception.UserUnauthorizedException
import com.tim.beatham.pubgolf.models.Response
import com.tim.beatham.pubgolf.models.User
import com.tim.beatham.pubgolf.models.UserLogin
import com.tim.beatham.pubgolf.models.representation.UserModelAssembler
import com.tim.beatham.pubgolf.repositories.UserRepository
import org.jetbrains.annotations.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.EntityModel
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException.BadRequest
import org.springframework.web.client.HttpClientErrorException.Unauthorized
import java.util.Base64.Encoder
import java.util.StringJoiner
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
class UserController {

    @Autowired
    private lateinit var usersRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Autowired
    private lateinit var userModelAssembler: UserModelAssembler

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @GetMapping("/")
    fun getAllUsers(): Response<List<EntityModel<User>>> {
        val users = usersRepository.findAll().map { userModelAssembler.toModel(it) }
        return Response.createSuccessResponse(users)
    }

    @GetMapping("/{id}")
    fun one(@PathVariable id: String): Response<EntityModel<User>> {
        val user = usersRepository.findById(id).orElseThrow() { UserNotFoundException(id) }
        return Response.createSuccessResponse(userModelAssembler.toModel(user))
    }

    @PostMapping("/register")
    fun createUser(@Valid @RequestBody user: User): Response<EntityModel<User>> {
        if (!usersRepository.findByEmail(user.email).isEmpty)
            throw UserAlreadyExistsException(user.email)

        val encryptedUser = User(name = user.name, email = user.email, password = passwordEncoder.encode(user.password).toString())

        usersRepository.insert(encryptedUser)
        return Response.createSuccessResponse(userModelAssembler.toModel(encryptedUser))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody userLogin: UserLogin): Response<String> {
        val referencedUser = usersRepository.findByEmail(userLogin.email)

        if (referencedUser.isEmpty)
            throw UserNotFoundException("")

        if (!passwordEncoder.matches(userLogin.password, referencedUser.get().password))
            throw UserUnauthorizedException()

        return Response.createSuccessResponse(jwtTokenUtil.generateToken(referencedUser.get()))
    }
}