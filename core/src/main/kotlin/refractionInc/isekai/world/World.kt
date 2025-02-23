package refractionInc.isekai.world

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.World
import refractionInc.isekai.Game
import refractionInc.isekai.entity.Entity
import refractionInc.isekai.entity.EntityPlayer


class World(
    val levels: List<Level>,
    val size: Vector2,
) {
    val phys = World(Vector2(0f, 0f), true)
    val debug_physRenderer = Box2DDebugRenderer()

    val entities: MutableList<Entity> = mutableListOf()
    val player by lazy { EntityPlayer(this, Vector2(421f, 109f)) }

    init {
        Game.input.assumeControl(player, player::handleInput)
        Game.cameraFollow = player

        for (level in levels) {
            for (y in level.collisionGrid.indices) {
                for (x in level.collisionGrid[y].indices) {
                    if (level.collisionGrid[y][x] == 1) {
                        val groundBodyDef = BodyDef().apply {
                            position.set( Vector2(
                                level.globalPosition.x + ((x+0.5f)*16f),
                                level.globalPosition.y + (level.gridSize.y-(y*16f))
                            ))
                        }

                        val groundBody: Body = phys.createBody(groundBodyDef).apply {
                            val box = PolygonShape().apply { setAsBox(8f, 8f) }
                            createFixture(box, 0.0f)
                        }
                    }
                }
            }
        }
    }

    var accumulator = 0f
    fun tick(delta: Float) {
        accumulator += delta;
        phys.step(accumulator, 6, 2)

        entities.forEach { it.tick(delta) }
        levels.forEach { it.tick(delta) }
        player.tick(delta)
    }

    fun draw(batch: SpriteBatch, delta: Float) {

        levels.forEach { it.draw(batch) }
        entities.forEach { it.draw(batch, delta) }
    }

}
