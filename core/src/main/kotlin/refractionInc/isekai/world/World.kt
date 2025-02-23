package refractionInc.isekai.world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.Game
import refractionInc.isekai.entity.Entity
import refractionInc.isekai.entity.EntityPlayer

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

    fun tick() {
        val delta = Gdx.graphics.deltaTime

        entities.forEach { it.tick(delta) }
        levels.forEach { it.tick(delta) }
    }

    fun draw(batch: SpriteBatch) {
        levels.forEach { it.draw(batch) }
        entities.forEach { it.draw(batch) }
    }

}
