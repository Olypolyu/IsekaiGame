package refractionInc.isekai.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import java.io.FileNotFoundException
import kotlin.math.max

val fileTree = FileTree.init()
class FileTree
private constructor(
    val name: String,
    val path: String,
    val children: MutableList<FileTree> = mutableListOf(),
    val isFolder: Boolean
)

{
    companion object {
        fun init(): FileTree {
            val fileList = Gdx.files.internal("assets.txt")
                .readString()
                .split("\n")
                .map { it.split("/") }

            val root = FileTree("root", "", mutableListOf(), true)
            for (path in fileList) {
                var parent = root
                for (index in path.indices) {
                    val dir = path[index]
                    parent = parent.children.find { it.name == dir } ?: FileTree(
                        name = dir,
                        path = path.slice(IntRange(0, index)).joinToString("/"),
                        isFolder = index != path.lastIndex
                    ).also { parent.children.add(it) }
                }
            }
            return root
        }
    }

    val file: FileHandle get() {
        assert(isFolder) { "Internal Asset File Tree: Cannot Read a file Handle from a folder."}
        return Gdx.files.internal(path)
    }

    fun get(path: String): FileTree = get(path.split('/'))
    fun get(path: List<String>): FileTree {
        if (path.isEmpty()) return this
        return children.find { it.name == path[0] }?.get(path.slice(IntRange(1, path.lastIndex))) ?: throw FileNotFoundException("Couldn't find ${path[0]}")
    }

    fun print() { printNode(this) }
    private fun printNode(node: FileTree, indent: Int = 0) {
        val separator = "|  ".repeat(max(indent-1, 0))
        println("${if (node.isFolder) "$separator| \n" else ""}$separator|- ${if (node.isFolder) node.path else node.name}".trimIndent())
        node.children.forEach { printNode(it, indent+1) }
    }
}
