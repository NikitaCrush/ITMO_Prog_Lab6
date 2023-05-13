package commands

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.LabWorkCollection
/**
 * The Command interface represents a command that can be executed.
 */
abstract class Command: KoinComponent {
    val labWorkCollection: LabWorkCollection by inject()
    abstract fun execute(args: List<Any>): String

    open fun readArguments(input: () -> String): List<Any> {
        return emptyList()
    }
}
