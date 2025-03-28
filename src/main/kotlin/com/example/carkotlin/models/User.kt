package com.example.carkotlin.models


import com.example.carkotlin.models.enum.Role
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "users")
class User() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var username: String? = null
    var password: String? = null

    @Enumerated(EnumType.STRING)
    @NotNull
    var role: Role? = null

}
