package com.example

import io.quarkus.runtime.Startup
import javax.enterprise.context.ApplicationScoped
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence


class ApplicationConfig {
    @Startup
    @ApplicationScoped
    fun createEntityManager(): EntityManager {
        return Persistence.createEntityManagerFactory("unit").open {
            it.createEntityManager()
        }
    }
}

inline fun <T> EntityManagerFactory.open(f: (EntityManagerFactory) -> T) = try { f(this) } finally { close() }

inline fun <T> EntityManager.open(f: (EntityManager) -> T) = try { f(this) } finally { close() }