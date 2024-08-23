package elias.fakerMaker.dto

class NameDto(options: List<Unit>?) : Maker<Unit>(
    name = "name",
    isNullable = false,
    options
)