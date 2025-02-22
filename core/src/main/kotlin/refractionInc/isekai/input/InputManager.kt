package refractionInc.isekai.input

import com.badlogic.gdx.InputProcessor

/**
 * A factory function that returns an InputManager impl
 */
fun InputManager(): InputManager = KeyboardManager()

interface InputManager : InputProcessor {
    val type: String
    fun assumeControl(caller: Any, callback: (Command<Any>) -> Boolean)
    fun relinquishControl(caller: Any)
    fun setDefaultBindings()
    fun captureBindingForCommand(command: EnumCommand)
    fun executeCommand(command: Command<Any>): Boolean
    fun tick()
}
