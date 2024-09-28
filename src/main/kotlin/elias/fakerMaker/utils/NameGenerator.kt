package elias.fakerMaker.generator_utils

import net.datafaker.Faker
import kotlin.random.Random


class NameGenerator {
    private val faker: Faker = Faker()
    private val rand: Random = Random

    private fun generateNames(): Map<String, Array<String>> {
        val LotRNameArray =
            faker.lordOfTheRings().character().replace("Mrs. |Mr. |Dr. |Jr. ]".toRegex(), "").split("\\s+".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()

        val names = HashMap<String, Array<String>>()
        names["LotR"] = LotRNameArray

        return names
    }

    fun getRandomFirstName(): String {
        val names = generateNames()
        val randomPick: Int = rand.nextInt(names.keys.size)

        for (i in names.keys.indices) {
            if (randomPick == i) {
                val key: Any = names.keys.toTypedArray()[i]
                // todo what the crap is causing this to be possibly null
                return names[key]!![0]
            }
        }
        return ""
    }

    fun getRandomLastName(): String {
        val names = generateNames()
        val randomPick: Int = rand.nextInt(names.keys.size)

        for (i in names.keys.indices) {
            if (randomPick == i) {
                val key: Any = names.keys.toTypedArray()[i]
                val lastIndex = names[key]!!.size - 1
                return names[key]!![lastIndex]
            }
        }
        return ""
    }
}