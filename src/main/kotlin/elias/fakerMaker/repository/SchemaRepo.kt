package elias.fakerMaker.repository

import elias.fakerMaker.serializers.ListEnumFakerSerializer
import elias.fakerMaker.types.model.Schema
import elias.fakerMaker.types.MakerConfig
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Repository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional

@Repository
class SchemaRepository(private val jdbcTemplate: JdbcTemplate) {
    private val json = Json {
        prettyPrint = false
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @Transactional
    fun saveSchema(schema: Schema) {
        // Serialize the lists to JSON strings
        val fakersJson = json.encodeToString(ListEnumFakerSerializer, schema.fakers)
        val makersJson = json.encodeToString(ListSerializer(MakerConfig.serializer()), schema.makers)

        val sql = """
            INSERT INTO schema_session (session_id, fakers, makers)
            VALUES (?, ?::jsonb, ?::jsonb)
            ON CONFLICT (session_id) 
            DO UPDATE SET 
                fakers = EXCLUDED.fakers,
                makers = EXCLUDED.makers,
                updated_at = CURRENT_TIMESTAMP
        """.trimIndent()

        jdbcTemplate.update(sql,
                            schema.sessionID,
                            fakersJson,
                            makersJson
        )
    }

    @Transactional(readOnly = true)
    fun getSchema(sessionId: String): Schema? {
        val sql = "SELECT fakers, makers FROM schema_session WHERE session_id = ?"

        return jdbcTemplate.query(sql, { rs, _ ->
            val fakers = json.decodeFromString(
                ListEnumFakerSerializer,
                rs.getString("fakers")
            )
            val makers = json.decodeFromString(
                ListSerializer(MakerConfig.serializer()),
                rs.getString("makers")
            )
            Schema(sessionId, fakers, makers)
        }, sessionId).firstOrNull()
    }

    @Transactional(readOnly = true)
    fun exists(sessionId: String): Boolean {
        val sql = "SELECT EXISTS(SELECT 1 FROM schema_session WHERE session_id = ?)"
        return jdbcTemplate.queryForObject(sql, Boolean::class.java, sessionId) ?: false
    }
}