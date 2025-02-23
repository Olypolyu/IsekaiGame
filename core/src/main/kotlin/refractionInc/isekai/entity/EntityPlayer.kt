package refractionInc.isekai.entity

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
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
        sprite.setSize(size)
    }

    fun handleInput(command: Command<Any>): Boolean {
        if (command.state == EnumKeyState.Held) {
            when (command.type) {
                EnumCommand.MoveNorth -> {speed.y += 5; return true}
                EnumCommand.MoveSouth -> {speed.y -= 5; return true}
                EnumCommand.MoveEast ->  {speed.x += 5; return true}
                EnumCommand.MoveWest ->  {speed.x -= 5; return true}
                else -> {}
            }
        }
        return false
    }

    override fun tick() {
        if (position.x < 0) position.x = 0f
        if (position.y < 0) position.y = 0f
        if (position.x > world.size.x -size.x) position.x = world.size.x - size.x
        if (position.y > world.size.y -size.y) position.y = world.size.y - size.y
        super.tick()
    }

    override fun draw(batch:SpriteBatch) {
        sprite.setPosition(position)
        sprite.draw(batch)
    }
}
