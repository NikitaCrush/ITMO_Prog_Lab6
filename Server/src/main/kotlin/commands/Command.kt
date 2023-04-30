package commands

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.LabWorkCollection
import utils.Validator
/**
 * The Command interface represents a command that can be executed.
 */
abstract class Command: KoinComponent {
    val validator: Validator by inject()
    val labWorkCollection: LabWorkCollection by inject()
    abstract fun execute(args: List<Any>): String
}
