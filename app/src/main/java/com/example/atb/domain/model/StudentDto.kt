package com.example.atb.domain.model

data class StudentDto(
    val name: String = "",
    val enrollmentNumber: String = "",
    val registrationNumber: String? = null,
    val course: String = "",
    val fathersName: String = ""
) {
    fun addValue(pair: Pair<String, String>): StudentDto {
        return when (pair.first) {
            "name" -> this.copy(name = pair.second)
            "enrollmentNumber" -> this.copy(enrollmentNumber = pair.second)
            "registrationNumber" -> this.copy(registrationNumber = pair.second)
            "course" -> this.copy(course = pair.second)
            "fathersName" -> this.copy(fathersName = pair.second)
            else -> this
        }
    }
}
