package elias.fakerMaker.utils

class RandomEnum {
    companion object {
        /**
         * return random enum value of enum class T
         */
        inline fun <reified T : Enum<T>> randomEnum(): T {
            val enumValues: Array<T> = enumValues()
            return enumValues[enumValues.indices.random()]
        }

        /**
         * returns a list of 2 to n random unique enum values from enum class T,
         * where n is the size of the enum class
         */
        inline fun <reified T : Enum<T>> randomEnums(): List<T> {
            val allEnums = enumValues<T>().toMutableList()
            val selectedEnums = mutableListOf<T>()
            val maxSize = (2..allEnums.size).random()

            for (i in 0 until maxSize) {
                val randomIndex = allEnums.indices.random()
                val randomEnum = allEnums.removeAt(randomIndex)
                selectedEnums.add(randomEnum)
            }

            return selectedEnums.toList()
        }
    }
}