package com.tim.beatham.pubgolf.models.representation

import com.tim.beatham.pubgolf.controllers.UserController
import com.tim.beatham.pubgolf.models.User
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.stereotype.Component

@Component
class UserModelAssembler : RepresentationModelAssembler<User, EntityModel<User>> {
    override fun toModel(user: User): EntityModel<User> {
        return EntityModel.of(user, linkTo(methodOn(UserController::class.java).one(user.id as String)).withSelfRel())
    }
}