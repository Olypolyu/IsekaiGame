package refractionInc.isekai.world

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.JsonReader
import refractionInc.isekai.utils.fileTree

fun readCSV(csv: String): List<List<Int>> {
    assert(csv.last() == '\n') {"Invalid CSV, last char needs trailing newline."}
    var buffer = ""
    var lineBuffer: MutableList<Int> = mutableListOf()
    val columnBuffer: MutableList<List<Int>> = mutableListOf()

    for (char in csv) {
        when (char) {
            ',' -> {
                assert(buffer != "") {"Invalid CSV, check formatting."}
                lineBuffer.add(buffer.toInt())
                buffer = ""
            }

            '\n' -> {
                if (buffer != "") {
                    lineBuffer.add(buffer.toInt())
                    buffer = ""
                }

                columnBuffer.add(lineBuffer)
                lineBuffer = mutableListOf()
            }

            '\r' -> {}

            else -> {
                assert(char.isDigit()) {"Invalid CSV, \"$char\" is not a digit."}
                buffer += char
            }
        }
    }
    return columnBuffer
}

fun LDtkLoadSimpleWorld(internalPath: String) : World {
    val worldDir = fileTree.get("$internalPath/simplified")
    val levels: MutableList<Level> = mutableListOf()

    for (levelDir in worldDir.children) {
        val collision = readCSV(levelDir.get("Collisions.csv").file.readString())
        val levelData = JsonReader().parse(levelDir.get("data.json").file.read())

        levelData.apply {
            val bgColorStr = getString("bgColor")

            levels.add(
                Level(
                    uid = getString("uniqueIdentifer"),
                    identifier = getString("identifier"),

                    globalPosition = Vector2(getFloat("x"), getFloat("y")),
                    gridSize = Vector2(getFloat("width"), getFloat("height")),
                    bgColor = Color(
                        bgColorStr.substring(1,3).toInt(16) /255f,
                        bgColorStr.substring(3,5).toInt(16) /255f,
                        bgColorStr.substring(5,7).toInt(16) /255f,
                        1f
                    ),
                    neighbours = get("neighbourLevels").associate { it.get("dir").asChar() to it.get("levelIid").asString() },
                    layers = get("layers").map { Sprite(Texture(levelDir.get(it.asString()).path)) },
                    collisionGrid = collision,
                )
            )
        }
    }

    return World(
        levels = levels,
        size = Vector2(1000f, 500f)
    )
}
