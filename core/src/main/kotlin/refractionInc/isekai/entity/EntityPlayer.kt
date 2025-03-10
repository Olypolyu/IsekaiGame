package refractionInc.isekai.entity

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import refractionInc.isekai.input.Command
import refractionInc.isekai.input.EnumCommand
import refractionInc.isekai.input.EnumKeyState
import refractionInc.isekai.utils.*
import refractionInc.isekai.world.World

class EntityPlayer(world: World, position: Vector2) : EntityActor(world, position) {

    companion object {
        val texture = Texture("ohmygotto.png")
    }

    private val sprite = Sprite(texture)

    override val size = Vector2(16f, 16f)

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
                density = 2f
                friction = 0f
                restitution = 5f

            }
        )

        body.userData = PhysUserData(this)
        collisionShape.dispose()
        body
    }

    init { sprite.setSize(size) }

    fun handleInput(command: Command<Any>): Boolean {
        if (command.state == EnumKeyState.Held) {
            when (command.type) {
                EnumCommand.MoveNorth -> physBody.applyForceToCenter(0f, 1f, true)
                EnumCommand.MoveSouth -> physBody.applyForceToCenter(0f, -1f, true)
                EnumCommand.MoveEast  -> physBody.applyForceToCenter(1f, 0f, true)
                EnumCommand.MoveWest  -> physBody.applyForceToCenter(-1f, 0f, true)
                else -> return false
            }

            return true
        }
        return false
    }

    override fun tick(delta: Float) {
        super.tick(delta)
    }

    override fun draw(batch: SpriteBatch, delta: Float) {
        sprite.setPosition(physBody.position - (size/2f))
        sprite.draw(batch)
    }
}
