package refractionInc.isekai.input

import com.badlogic.gdx.InputAdapter
import java.util.*
import kotlin.collections.HashMap

class KMInputManager: InputAdapter() {
    private val keyboardBindings: HashMap<Int, EnumCommand> = HashMap()
    private val controlStack: MutableList<Pair<Class<Any>?, (Command<Any>) -> Boolean>> = mutableListOf()

    fun assumeControl(callback: (Command<Any>) -> Boolean, caller: Class<Any>? = null) {
        controlStack.add(Pair(caller, callback))
    }

    fun relinquishControl(caller: Class<Any>) {
        for (i in 0..controlStack.size)
    }


    override fun keyUp(keycode: Int): Boolean {
        val command: EnumCommand? = keyboardBindings[keycode]

        return command?.run {
            controlStack.last().second(Command<Float>(command, EnumKeyState.Lifted, 1.0f))
        } ?: false
    }
}
