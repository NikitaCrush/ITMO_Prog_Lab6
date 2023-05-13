package server


import kotlinx.serialization.Serializable
import server.server.CommandArgument

@Serializable
data class CommandData(
    val commandName: String,
    val arguments: List<CommandArgument> = emptyList()
)

