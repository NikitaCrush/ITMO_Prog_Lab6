package client


import kotlinx.serialization.Serializable

@Serializable
data class CommandData(
    val commandName: String,
    val arguments: List<CommandArgument> = emptyList()
)
