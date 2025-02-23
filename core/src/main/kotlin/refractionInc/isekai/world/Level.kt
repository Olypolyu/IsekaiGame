package refractionInc.isekai.world

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.entity.Entity

class Level(
    val identifier: String,
    val worldPosition: Vector2,
    val gridSize: Vector2,
    val opacity: Float,
    val visible: Boolean = true,
    val entities: List<Entity>? = null,
    val fields: Map<String, Any>? = null
)
{
    val gridArea get() = gridSize.x * gridSize.y
}
