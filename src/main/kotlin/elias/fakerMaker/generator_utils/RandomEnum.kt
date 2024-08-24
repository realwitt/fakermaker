package elias.fakerMaker.generator_utils

import kotlin.reflect.typeOf

class RandomEnum {
    /**
     * return random enum value of enum class T
     */
    companion object {
        inline fun <reified T : Enum<T>> randomEnum(): T {
            val enumValues: Array<T> = enumValues()
            return enumValues[enumValues.indices.random()]
        }
    }

}