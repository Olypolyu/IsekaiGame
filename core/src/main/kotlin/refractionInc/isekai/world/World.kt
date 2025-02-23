package refractionInc.isekai.world

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.Game
import refractionInc.isekai.entity.Entity
import refractionInc.isekai.entity.EntityPlayer

class World(
    val layers: List<Sprite>,
    val size: Vector2,
) {

    val entities: MutableList<Entity> = mutableListOf()
    val player by lazy { EntityPlayer(this, Vector2(0f, 0f)) }
    init {
        Game.input.assumeControl(player, player::handleInput)
        Game.cameraFollow = player

        layers.forEach { it.setPosition(0f, 0f) }
    }

    fun tick() {
        entities.forEach { it.tick() }
    }

    fun draw(batch: SpriteBatch) {
        layers.forEach { it.draw(batch) }
        entities.forEach { it.draw(batch) }
    }

}
