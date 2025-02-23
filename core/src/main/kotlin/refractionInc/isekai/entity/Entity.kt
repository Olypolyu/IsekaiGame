package refractionInc.isekai.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.world.World

interface Entity {
    val world: World
    val position: Vector2
    val speed: Vector2
    var rotation: Float
    val size: Vector2

    fun draw(batch: SpriteBatch, delta: Float) {}
    fun tick(delta: Float) {}
}
