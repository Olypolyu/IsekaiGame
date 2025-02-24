package refractionInc.isekai.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import refractionInc.isekai.world.World

interface Entity {
    val world: World
    val size: Vector2
    val physBody: Body

    fun draw(batch: SpriteBatch, delta: Float) {}
    fun tick(delta: Float) {}
    fun onAddedToWorld() {}
    fun onCollide(entity:Entity) {}
    fun dispose() {
        world.physWorld.destroyBody(physBody)
    }
}
