package com.tim.beatham.pubgolf.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.validation.constraints.NotEmpty

const val UserCollectionName = "users"

@Document(collection = UserCollectionName)
class User(
    @Id var id: String? = null,

    @NotEmpty(message = "A name must be provided")
    var name: String,

    @NotEmpty(message = "An email must be provided")
    var email: String,

    @NotEmpty(message = "A password must be provided")
    private var password: String? = null) : UserDetails {

    @JsonIgnore
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun getPassword() = password

    @JsonIgnore
    override fun getUsername() = name

    @JsonIgnore
    override fun isAccountNonExpired() = false

    @JsonIgnore
    override fun isAccountNonLocked() = false

    @JsonIgnore
    override fun isCredentialsNonExpired() = true

    @JsonIgnore
    override fun isEnabled() = true

}