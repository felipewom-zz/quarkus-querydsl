package com.example

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
open class User(
    val name: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int = 0)