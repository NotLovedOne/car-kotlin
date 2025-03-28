package com.example.carkotlin.models.dto

import com.example.carkotlin.models.enum.Role

data class LoginRequest (val username: String?=null, val password: String?=null, val role: Role?=null)