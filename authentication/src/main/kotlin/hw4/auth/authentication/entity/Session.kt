package hw4.auth.authentication.entity
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity(name="session")
data class Session(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val user_id: Long,

    @Column(name = "token", nullable = false, length = 255)
    val token: String,

    @Column(name = "expires", nullable = false)
    val expires: LocalDateTime = LocalDateTime.now().plusMinutes(10)
)
