package refractionInc.isekai.world

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.entity.Entity

class Layer(
    val identifier: String,
    val gridSize: Vector2,
    val worldPosition: Vector2,
    val pxOffset: Vector2,
    val opacity: Float,
    val tilesetTexture: Texture,
    val levelID: Int,
    val entities: List<Entity>? = null,
    val tiles: List<Tile>? = null,
    val visible: Boolean = true,
    val fields: Map<String, Any>? = null
)
{
    val gridArea get() = gridSize.x * gridSize.y
}
