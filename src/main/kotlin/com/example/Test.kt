package com.example

import com.mysema.query.jpa.impl.JPAQuery
import com.example.QUser.user
import javax.persistence.Persistence

object Test {
    @JvmStatic
    fun main(args: Array<String>) =
            Persistence.createEntityManagerFactory("unit")
                    .open { emf ->
        emf.createEntityManager().open { entityManager ->
            val transaction = entityManager.transaction
            transaction.begin()

            with (entityManager) {
                persist(User("Smith"))
                persist(User("Gates"))
                persist(User("Orlov"))
                persist(User("Smirnov"))
                persist(User("Orlov"))

                flush()
            }

            transaction.commit()

            val query = JPAQuery(entityManager)

            val uniqueUserNames = query.from(user)
                    .where(user.name.like("%ov"))
                    .groupBy(user.name)
                    .list(user.name)

            println("Unique names: ")
            uniqueUserNames.forEach { println(it) }
        }
    }
}
