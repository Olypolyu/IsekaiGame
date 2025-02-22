package refractionInc.isekai.entity

import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.world.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import refractionInc.isekai.input.Command
import refractionInc.isekai.input.EnumCommand
import refractionInc.isekai.input.EnumKeyState

class EntityPlayer(world: World, position: Vector2) : EntityActor(world, position) {

    override val size = Vector2(40f, 40f)
    val sprite = Texture("ohmygotto.png")

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
        if (position.x > Gdx.graphics.width -size.x) position.x = Gdx.graphics.width.toFloat() - size.x
        if (position.y > Gdx.graphics.height -size.y) position.y = Gdx.graphics.height.toFloat() - size.y
        super.tick()
    }

    override fun draw(batch:SpriteBatch, screenX:Float, screenY:Float) {
        batch.draw(this.sprite, screenX, screenY, size.x, size.y)
    }
}
