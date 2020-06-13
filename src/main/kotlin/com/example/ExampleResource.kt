package com.example

import com.mysema.query.jpa.impl.JPAQuery
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/hello")
class ExampleResource {

    @Inject
    @field: Default
    lateinit var entityManager: EntityManager

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun hello(): MutableList<String>? {

        val transaction = entityManager.transaction
        transaction.begin()

        with(entityManager) {
            persist(User("Smith"))
            persist(User("Gates"))
            persist(User("Orlov"))
            persist(User("Smirnov"))
            persist(User("Orlov"))

            flush()
        }

        transaction.commit()

        val query = JPAQuery(entityManager)

        val uniqueUserNames = query.from(QUser.user)
                .where(QUser.user.name.like("%ov"))
                .groupBy(QUser.user.name)
                .list(QUser.user.name)

        return uniqueUserNames
    }
}