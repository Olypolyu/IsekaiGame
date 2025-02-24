package refractionInc.isekai.entity

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import refractionInc.isekai.utils.*
import refractionInc.isekai.world.World

class EntityBouncyBall(world: World, position: Vector2 = Vector2(16f, 16f)) : EntityActor(world, position) {

    companion object {
        val texture = listOf(
            Texture("ball.png"),
            Texture("ballred.png"),
            Texture("ballgreen.png"),
            Texture("ballyellow.png"),
        )
    }
    override val size = Vector2(16f, 16f)

    private val sprite = Sprite(texture.random())

    var cooldown = 100

    override val physBody: Body by lazy {
        val collisionShape = CircleShape().apply { radius = 8f }

        val body = world.physWorld.createBody(
            BodyDef().apply {
                type = BodyDef.BodyType.DynamicBody
                this.position.set(position)
            }
        )

        body.createFixture(
            FixtureDef().apply {
                shape = collisionShape
                density = 1.0f
                restitution = 0.9f
            }
        )

        body.userData = PhysUserData(this)
        collisionShape.dispose()
        body
    }

    init { sprite.setSize(size) }

    override fun tick(delta: Float) {
        cooldown--
        super.tick(delta)
    }

    override fun onCollide(entity: Entity) {
        if (world.entityCount > 600) {
            world.destroyEntity(
                world.entities
                    .filter { it != this && it != world.player }
                    .random()
            )
        }

        if (cooldown < 0) {
            world.addEntity(EntityBouncyBall(world, physBody.position))
            cooldown = 100
        }
    }

    override fun draw(batch: SpriteBatch, delta: Float) {
        sprite.setPosition(physBody.position - (size/2f))
        sprite.draw(batch)
    }
}
