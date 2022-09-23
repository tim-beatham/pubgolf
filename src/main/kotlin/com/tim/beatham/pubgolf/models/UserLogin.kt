package com.tim.beatham.pubgolf.models

import javax.validation.constraints.NotEmpty

data class UserLogin(
    @NotEmpty
    val email: String,

    @NotEmpty
    val password: String)