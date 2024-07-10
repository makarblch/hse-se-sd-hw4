package hw4.auth.authentication.repository

import hw4.auth.authentication.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email : String) : User?
    override fun findById(id : Long) : Optional<User>
}