package elias.fakerMaker.utils

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