package refractionInc.isekai.entity

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import refractionInc.isekai.input.Command
import refractionInc.isekai.input.EnumCommand
import refractionInc.isekai.input.EnumKeyState
import refractionInc.isekai.utils.setPosition
import refractionInc.isekai.utils.setSize
import refractionInc.isekai.world.World

class EntityPlayer(world: World, position: Vector2) : EntityActor(world, position) {
    override val size = Vector2(16f, 16f)
    val sprite = Sprite(Texture("ohmygotto.png"))

    init {
        world.entities.add(this)
        sprite.setSize(size)
    }

    val collision by lazy {
        val collisionShape = CircleShape().apply { radius = 8f }

        val body = world.phys.createBody(
            BodyDef().apply {
                type = BodyDef.BodyType.DynamicBody
                this.position.set(position)
            }
        )

        body.createFixture(
            FixtureDef().apply {
                shape = collisionShape
                density = 2f
                friction = 0f
                restitution = 5f
                rotation = 0f
            }
        )

        collisionShape.dispose()
        body
    }

    fun handleInput(command: Command<Any>): Boolean {
        if (command.state == EnumKeyState.Held) {
            when (command.type) {
                EnumCommand.MoveNorth -> collision.applyForceToCenter(0f, 1f, true)
                EnumCommand.MoveSouth -> collision.applyForceToCenter(0f, -1f, true)
                EnumCommand.MoveEast  -> collision.applyForceToCenter(1f, 0f, true)
                EnumCommand.MoveWest  -> collision.applyForceToCenter(-1f, 0f, true)
                else -> return false
            }

            return true
        }
        return false
    }

    override fun tick(delta: Float) {
        position.set(collision.position)
        position.x -= size.x/2
        position.y -= size.y/2

        super.tick(delta)
    }

    override fun draw(batch: SpriteBatch, delta: Float) {
        sprite.setPosition(position)
        sprite.draw(batch)
    }
}
