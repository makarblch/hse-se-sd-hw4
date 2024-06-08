package hw4.auth.authentication.repository

import hw4.auth.authentication.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findUser(nickname: String, email: String): User?
}