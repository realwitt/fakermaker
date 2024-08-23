package elias.fakerMaker.mapper

//import kotlinx.serialization.*
//import kotlinx.serialization.json.*

// no mapstruct bc we're not saving or querying anything from the db
class SchemaMapper() {
    // use kotlinx.serialization to parse the json payload
    // not sure what type this param should be...
    fun serializeJson(schemaJson : String) {
//        val schema = Json.decodeFromString(schemaJson)

    }
}