package hw4.auth.authentication.repository

import hw4.auth.authentication.entity.Session
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionRepository : JpaRepository<Session, Long> {
}