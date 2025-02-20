package refractionInc.isekai.entity

import refractionInc.isekai.input.Command
import refractionInc.isekai.input.EnumCommand
import refractionInc.isekai.input.EnumKeyState

class EntityPlayer(world: Any, worldX: Float, worldY: Float) : EntityActor(world, worldX, worldY) {


    fun handleInput(command: Command<Any>) {
        if (command.state == EnumKeyState.Held) {
        }
    }

    override fun tick() {}

    override fun draw(screenX: Int, screenY: Int) {}

}
