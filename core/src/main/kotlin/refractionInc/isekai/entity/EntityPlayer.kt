package refractionInc.isekai.entity

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.input.*
import refractionInc.isekai.utils.*
import refractionInc.isekai.world.World

class EntityPlayer(world: World, position: Vector2) : EntityActor(world, position) {
    override val size = Vector2(40f, 40f)
    val sprite = Sprite(Texture("ohmygotto.png"))
//    private var elapsed = 0f

    init {
        sprite.setSize(size)
    }

    fun handleInput(command: Command<Any>): Boolean {
        if (command.state == EnumKeyState.Held) {
            when (command.type) {
                EnumCommand.MoveNorth -> speed.y += 30
                EnumCommand.MoveSouth -> speed.y -= 30
                EnumCommand.MoveEast  -> speed.x += 30
                EnumCommand.MoveWest  -> speed.x -= 30
                else -> return false
            }

            return true
        }
        return false
    }

    override fun tick(delta: Float) {
//        elapsed = 0f
//        val (x, y) = position + speed
//        if (x > world.size.x - size.x) speed.x = lerp(speed.x, x - (world.size.x + size.x), delta)
//        if (y > world.size.x - size.x) speed.y = lerp(speed.x, x - (world.size.x + size.x), delta)

        if (position.x < 0) position.x = 0f
        if (position.y < 0) position.y = 0f
        if (position.x > world.size.x -size.x) position.x = world.size.x - size.x
        if (position.y > world.size.y -size.y) position.y = world.size.y - size.y
        super.tick(delta)
    }

    override fun draw(batch: SpriteBatch, delta: Float) {
        sprite.setPosition(position)
        sprite.draw(batch)
    }
}
