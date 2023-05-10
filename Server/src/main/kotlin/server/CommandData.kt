package server


import kotlinx.serialization.Serializable

@Serializable
data class CommandData(
    val commandName: String,
    val parameters: List<String> = emptyList()
)


