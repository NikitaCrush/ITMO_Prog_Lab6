// Request.kt
package server

import kotlinx.serialization.Serializable

@Serializable
data class Request(val commandName: String, val args: List<CommandArgument>)

@Serializable
data class CommandArgument(val name: String, val value: String)
