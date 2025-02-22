package refractionInc.isekai.entity

import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.input.*
import refractionInc.isekai.world.World

class EntityPlayer(world: World, position: Vector2) : EntityActor(world, position) {
    fun handleInput(command: Command<Any>) {
        if (command.state == EnumKeyState.Held) {
            // TODO: put shit here
        }
    }

    override fun tick() {}
    override fun draw(screenX: Int, screenY: Int) {}
}
