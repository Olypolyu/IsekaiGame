package refractionInc.isekai.world

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.World
import refractionInc.isekai.Game
import refractionInc.isekai.entity.Entity
import refractionInc.isekai.entity.EntityBouncyBall
import refractionInc.isekai.entity.EntityPlayer
import refractionInc.isekai.utils.PhysUserData

class World(
    val levels: List<Level>,
    val size: Vector2,
) {

// < system >
    val entitiesToDestroy: MutableList<Entity> = mutableListOf()
    val entities: MutableList<Entity> = mutableListOf()
    val physWorld = World(Vector2(0f, 0f), true)
// </ system >

// < var >
    private var accumulator = 0f
    val player by lazy { EntityPlayer(this, Vector2(421f, 109f)).also { addEntity(it) } }
    val entityCount get() = entities.size
// </ var >

    init {
        Game.input.assumeControl(player, player::handleInput)
        Game.cameraFollow = player

        EntityBouncyBall(this, Vector2(421f, 109f)).also { addEntity(it) }

        for (level in levels) {
            for (y in level.collisionGrid.indices)
            for (x in level.collisionGrid[y].indices) {

                if (level.collisionGrid[y][x] == 1) {
                    val groundBodyDef = BodyDef().apply {
                        this.position.set(
                            level.globalPosition.x + ((x + 0.5f) * 16f),
                            level.globalPosition.y + (level.gridSize.y - (y * 16f) -1f)
                        )
                    }

                    physWorld.createBody(groundBodyDef).apply {
                        createFixture(PolygonShape().apply { setAsBox(8f, 8f) }, 0.0f)
                    }
                }
            }
        }

        physWorld.setContactListener(
            object : ContactListener {
                override fun endContact(p0: Contact?) {
                    val bodyA = p0?.fixtureA?.body ?: return
                    val bodyB = p0.fixtureB?.body ?: return

                    if (bodyA.userData is PhysUserData && bodyB.userData is PhysUserData) {
                        (bodyA.userData as PhysUserData).entity.onCollide((bodyB.userData as PhysUserData).entity)
                        (bodyB.userData as PhysUserData).entity.onCollide((bodyA.userData as PhysUserData).entity)
                    }
                }

                override fun beginContact(p0: Contact?) {}
                override fun preSolve(p0: Contact?, p1: Manifold?) {}
                override fun postSolve(p0: Contact?, p1: ContactImpulse?) {}
            }
        )
    }

    fun addEntity(entity: Entity): Entity {
        entities.add(entity)
        entity.onAddedToWorld()

        return entity
    }

    fun destroyEntity(entity: Entity) {
        entities.remove(entity)
        entitiesToDestroy.add(entity)
    }

    fun tick(delta: Float) {
        accumulator += delta;

        while (entitiesToDestroy.isNotEmpty()) {
            entitiesToDestroy.removeFirst().dispose()
        }

        entities.forEach { it.tick(delta) }
        levels.forEach { it.tick(delta) }

        physWorld.step(accumulator, 6, 2)
    }

    fun draw(batch: SpriteBatch, delta: Float) {
        levels.forEach { it.draw(batch) }
        entities.forEach { it.draw(batch, delta) }
    }

}
