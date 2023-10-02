package com.kitaplik.libraryservice.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "libraries")
data class Library @JvmOverloads constructor(
        @Id
        @Column(name = "library_id")
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id:String?="",
        @ElementCollection
        val userBook:List<String> = ArrayList()//book'ların id'lerini tutacak ve ihtiyac olunca book-service üzerinden id ile detayını çağıracak
)
