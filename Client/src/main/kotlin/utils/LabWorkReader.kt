package utils

import data.*
import exeptions.ValidationException
import java.time.LocalDateTime
import java.util.*

/**
 * A class for reading LabWork instances from user input.
 *
 * @param readLineFn A function to read user input.
 * @param validator A Validator instance to validate user input.
 */
class LabWorkReader(private val readLineFn: () -> String, private val validator: Validator) {

    /**
     * Reads and validates the name of a LabWork object from user input.
     *
     * @return A valid LabWork name.
     */
    private fun readName(): String {
        while (true) {
            print(Messages.ENTER_NAME)
            val name = readLineFn()
            try {
                validator.validateName(name)
                return name
            } catch (e: ValidationException) {
                println(e.message)
            }
        }
    }

    /**
     * Reads and validates the coordinates of a LabWork object from user input.
     *
     * @return A valid Coordinates object.
     */
    private fun readCoordinates(): Coordinates {
        while (true) {
            try {
                print(Messages.ENTER_X)
                val x = readLineFn().trim().toLong()
                print(Messages.ENTER_Y)
                val y = readLineFn().trim().toDouble()
                val coordinates = Coordinates(x, y)
                validator.validateCoordinates(coordinates)
                return coordinates
            } catch (e: NumberFormatException) {
                println(Messages.INVALID_COORDINATES)
            } catch (e: ValidationException) {
                println(e.message)
            }
        }
    }


    /**
     * Reads and validates the minimal point of a LabWork object from user input.
     *
     * @return A valid minimal point value.
     */
    private fun readMinimalPoint(): Int {
        return readInt(Messages.ENTER_MINIMAL_POINT, readLineFn, validator::validateMinimalPoint)
    }


    /**
     * Reads and validates the difficulty of a LabWork object from user input.
     *
     * @return A valid Difficulty object or null if not specified.
     */
    private fun readDifficulty(): Difficulty? {
        print(Messages.ENTER_DIFFICULTY)
        while (true) {
            val input = readLineFn().trim()
            if (input.isEmpty()) {
                return null
            }

            return try {
                Difficulty.valueOf(input.uppercase(Locale.getDefault()))
            } catch (e: IllegalArgumentException) {
                println(Messages.INVALID_DIFFICULTY)
                continue
            }
        }
    }

    /**
     * Reads and validates the discipline of a LabWork object from user input.
     *
     * @return A valid Discipline object.
     */
    private fun readDiscipline(): Discipline {
        while (true) {
            try {
                print(Messages.ENTER_DISCIPLINE)
                val disciplineInput = readLineFn().split(" ").map { it.trim() }
                val disciplineName = disciplineInput[0]
                val selfStudyHours = readSelfStudyHours()
                val discipline = Discipline(disciplineName, selfStudyHours)
                validator.validateDiscipline(discipline)
                validator.validateSelfStudyHours(selfStudyHours)
                return discipline
            } catch (e: Exception) {
                println(Messages.INVALID_DISCIPLINE)
            } catch (e: ValidationException) {
                println(e.message)
            }
        }
    }

    /**
     * Reads a LabWork object from user input.
     *
     * @param id The unique identifier for the LabWork object (default: generated by IdGenerator).
     * @param creationDate The creation date for the LabWork object (default: current date and time).
     * @return The created LabWork object.
     */
    fun readLabWork(
        id: Long = IdGenerator.generateUniqueId(),
        creationDate: LocalDateTime = LocalDateTime.now()
    ): LabWork {
        val name = readName()
        val coordinates = readCoordinates()
        val minimalPoint = readMinimalPoint()
        val personalQualitiesMinimum = readPersonalQualitiesMinimum()
        val difficulty = readDifficulty()
        val discipline = readDiscipline()
        return LabWork(
            id,
            name,
            coordinates,
            creationDate,
            minimalPoint,
            personalQualitiesMinimum,
            difficulty,
            discipline
        )
    }

    /**
     * Reads and validates the personal qualities minimum of a LabWork object from user input.
     *
     * @return A valid personal qualities minimum value.
     */
    private fun readPersonalQualitiesMinimum(): Int {
        return readInt(Messages.ENTER_PERSONAL_QUALITIES_MIN, readLineFn, validator::validatePersonalQualitiesMinimum)
    }

    /**
     * Reads and validates an integer from user input using the provided prompt and validation function.
     *
     * @param prompt The prompt message to display before reading input.
     * @param readLineFn A function to read user input.
     * @param validation A function to validate the input value.
     * @return A valid integer value.
     */
    private fun readInt(prompt: String, readLineFn: () -> String, validation: (Int) -> Unit): Int {
        while (true) {
            try {
                print(prompt)
                val value = readLineFn().toInt()
                validation(value)
                return value
            } catch (e: NumberFormatException) {
                println("Invalid input. Please enter a valid number.")
            } catch (e: ValidationException) {
                println(e.message)
            }
        }
    }

    /**
     * Reads and validates a long integer from user input using the provided validation function.
     *
     * @param readLineFn A function to read user input.
     * @param validation A function to validate the input value.
     * @return A valid long integer value.
     */
    private fun readLong(readLineFn: () -> String, validation: (Long) -> Unit): Long {
        while (true) {
            try {
                print(Messages.ENTER_SELF_STUDY_HOURS)
                val value = readLineFn().toLong()
                validation(value)
                return value
            } catch (e: NumberFormatException) {
                println(Messages.INVALID_NUMBER)
            } catch (e: ValidationException) {
                println(e.message)
            }
        }
    }

    /**
     * Reads and validates the self-study hours of a Discipline object from user input.
     *
     * @return A valid self-study hours value.
     */
    private fun readSelfStudyHours(): Long {
        return readLong(readLineFn, validator::validateSelfStudyHours)
    }


}