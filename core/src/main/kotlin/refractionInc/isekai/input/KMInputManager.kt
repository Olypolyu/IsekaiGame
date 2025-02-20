package refractionInc.isekai.input

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter

class KMInputManager: IInputManager, InputAdapter() {
    override val type = "keyboard"
    private val keyboardBindings: MutableMap<Int, EnumCommand> = mutableMapOf()
    private val controlStack: MutableList<Pair<Class<Any>?, (Command<Any>) -> Boolean>> = mutableListOf()
    private val keyDown: MutableMap<Int, Boolean> = mutableMapOf()

    private var currentCapturing: EnumCommand? = null

    override fun assumeControl(callback: (Command<Any>) -> Boolean, caller: Class<Any>?) {
        controlStack.add(Pair(caller, callback))
    }

    override fun relinquishControl(caller: Class<Any>) {
        for (i in controlStack.indices) {
            if (controlStack[i].first == caller) controlStack.removeAt(i)
        }
    }

    init { setDefaultBindings() }
    override fun setDefaultBindings() {
        keyboardBindings[Keys.W] = EnumCommand.MoveNorth
        keyboardBindings[Keys.S] = EnumCommand.MoveSouth
        keyboardBindings[Keys.A] = EnumCommand.MoveWest
        keyboardBindings[Keys.D] = EnumCommand.MoveEast
        keyboardBindings[Keys.P] = EnumCommand.Hit
        keyboardBindings[Keys.TAB] = EnumCommand.Tab
    }

    override fun captureBindingForCommand(command: EnumCommand) { this.currentCapturing = command }

    override fun executeCommand(command: Command<Any>):Boolean {
        for (entry in controlStack.reversed()) {
            val result = entry.second(command)
            if (result) return true
        }
        return false
    }

    override fun tick() {
        for (pair in keyDown.entries) {
            if (pair.value) {
                val command: EnumCommand? = keyboardBindings[pair.key]
                command?.run {
                    executeCommand(Command<Float>(command, EnumKeyState.Held, 1.0f))
                }
            }
        }
    }

    override fun keyUp(keycode: Int): Boolean {
        keyDown[keycode] = false

        val command: EnumCommand? = keyboardBindings[keycode]
        return command?.run {
            return executeCommand(Command<Float>(command, EnumKeyState.Up, 1.0f))
        } ?: false
    }

    override fun keyDown(keycode: Int): Boolean {
        keyDown[keycode] = true

        val command: EnumCommand? = keyboardBindings[keycode]
        return command?.run {
            return executeCommand(Command<Float>(command, EnumKeyState.Down, 0.0f))
        } ?: false
    }

}
