package hw4.auth.authentication.func

fun isValidEmail(email: String): Boolean {
    val emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$".toRegex()
    return emailPattern.matches(email)
}

fun isValidPassword(password: String): Boolean {
    val minLength = 8
    val hasDigit = password.any { it.isDigit() }
    val hasLowercase = password.any { it.isLowerCase() }
    val hasUppercase = password.any { it.isUpperCase() }
    val hasSpecialChar = password.any { !it.isLetterOrDigit() }

    return password.length >= minLength && hasDigit && hasLowercase && hasUppercase && hasSpecialChar
}
