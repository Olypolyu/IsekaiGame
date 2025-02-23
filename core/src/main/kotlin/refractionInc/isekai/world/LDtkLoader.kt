package refractionInc.isekai.world

import com.badlogic.gdx.Gdx

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

            else -> {
                assert(char.isDigit()) {"Invalid CSV, \"$char\" is not a digit."}
                buffer += char
            }
        }
    }
    return columnBuffer
}

fun LDtkLoadSimpleWorld(internalPath: String) : World {
    val files = Gdx.files.internal("level")
    println("$internalPath/simplified/")

    val children = files.list()
    println(children.size)
    for (file in children) {
        println(file.isDirectory)
        println(file.name())
    }

    return World()
}
