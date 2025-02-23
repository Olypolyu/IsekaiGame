package refractionInc.isekai.world

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.entity.Entity

class Level(
    val uid: String,
    val identifier: String,

    val globalPosition: Vector2,
    val gridSize: Vector2,
    val bgColor: Color,
    val neighbours: Map<Char, String> = mapOf(),
    val layers: List<Sprite>,
    val collisionGrid: List<List<Int>>,

    val fields: MutableMap<String, Any> = mutableMapOf(),
    private val entities: MutableList<Entity> = mutableListOf()
) {

    init {
        layers.forEach { it.setPosition(globalPosition.x , globalPosition.y) }
    }

    val gridArea get() = gridSize.x * gridSize.y

    fun tick() {
    }

    fun draw(batch: SpriteBatch) {
        layers.forEach { it.draw(batch) }
    }
}
