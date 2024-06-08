package hw4.auth.authentication.entity
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "session")
data class Session(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "token", nullable = false, length = 255)
    val token: String,

    @Column(name = "expires", nullable = false)
    val expires: LocalDateTime
)
