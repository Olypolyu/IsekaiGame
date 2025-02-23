package refractionInc.isekai.world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.Game
import refractionInc.isekai.entity.Entity
import refractionInc.isekai.entity.EntityPlayer

class World() {

    val entities: MutableList<Entity> = mutableListOf()

    val player by lazy { EntityPlayer(this, Vector2(0f, 0f)) }
    val background by lazy { Sprite(Texture(Gdx.files.internal("level/Typical_TopDown_example/simplified/World_Level_0/_composite.png"))) }
    val size get() = Vector2(background.width, background.height)

    init {
        Game.input.assumeControl(player, player::handleInput)
        Game.cameraFollow = player

        background.setPosition(0f, 0f)
    }

    fun tick() {
        entities.forEach { it.tick() }
    }

    fun draw(batch: SpriteBatch) {
        background.draw(batch)
        entities.forEach { it.draw(batch) }
    }

}
