package hw4.auth.authentication.service

import hw4.auth.authentication.entity.Session
import hw4.auth.authentication.repository.SessionRepository
import org.springframework.stereotype.Service

@Service
class SessionService (val sessionRepository: SessionRepository) {
    fun saveSession(session: Session) {
        sessionRepository.save(session)
    }

    fun findByToken(token: String) : Session? {
        return sessionRepository.findByToken(token)
    }

    fun findByUserId(id : Long) : Session? {
        return sessionRepository.findAll().findLast { it.user_id == id }
    }

}