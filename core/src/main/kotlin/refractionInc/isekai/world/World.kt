package refractionInc.isekai.world

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.Game
import refractionInc.isekai.entity.*

class World(
    val levels: List<Level>,
    val size: Vector2,
) {
    val entities: MutableList<Entity> = mutableListOf()
    val player by lazy { EntityPlayer(this, Vector2(0f, 0f)) }

    init {
        Game.input.assumeControl(player, player::handleInput)
        Game.cameraFollow = player
    }

    fun tick(delta: Float) {
        entities.forEach { it.tick(delta) }
        levels.forEach { it.tick(delta) }
    }

    fun draw(batch: SpriteBatch, delta: Float) {
        levels.forEach { it.draw(batch) }
        entities.forEach { it.draw(batch, delta) }
    }

}
