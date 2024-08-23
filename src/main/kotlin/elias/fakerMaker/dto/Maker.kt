package elias.fakerMaker.dto

open class Maker<T>(
    name: String,
    isNullable: Boolean,
    options: List<T>?
)