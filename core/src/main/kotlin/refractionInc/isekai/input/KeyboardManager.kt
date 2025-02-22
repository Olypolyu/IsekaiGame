package refractionInc.isekai.input

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter

class KeyboardManager : InputManager, InputAdapter() {
    override val type = "keyboard"
    private val keyboardBindings = mutableMapOf<Int, EnumCommand>()
    private val controlStack = mutableListOf<Pair<Any, (Command<Any>) -> Boolean>>()
    private val keyDown = mutableMapOf<Int, Boolean>()

    private var currentCapturing: EnumCommand? = null

    override fun assumeControl(caller: Any, callback: (Command<Any>) -> Boolean) {
        controlStack.add(caller to callback)
    }

    override fun relinquishControl(caller: Any) {
        controlStack.removeIf { it.first === caller }
    }

    init {
        setDefaultBindings()
    }

    override fun setDefaultBindings() {
        keyboardBindings[Keys.W] = EnumCommand.MoveNorth
        keyboardBindings[Keys.S] = EnumCommand.MoveSouth
        keyboardBindings[Keys.A] = EnumCommand.MoveWest
        keyboardBindings[Keys.D] = EnumCommand.MoveEast
        keyboardBindings[Keys.P] = EnumCommand.Hit
        keyboardBindings[Keys.TAB] = EnumCommand.Tab
    }

    override fun captureBindingForCommand(command: EnumCommand) {
        this.currentCapturing = command
    }

    override fun executeCommand(command: Command<Any>): Boolean {
        for ((_, callback) in controlStack.reversed()) {
            val result = callback(command)
            if (result) return true
        }
        return false
    }

    override fun tick() {
        for ((keyCode, pressed) in keyDown.entries) {
            if (!pressed) continue

            val command = keyboardBindings[keyCode] ?: return
            executeCommand(Command(command, EnumKeyState.Held, 1.0f))
        }
    }

    override fun keyUp(keycode: Int): Boolean {
        keyDown[keycode] = false

        val command = keyboardBindings[keycode] ?: return false
        return executeCommand(Command(command, EnumKeyState.Up, 1.0f))
    }

    override fun keyDown(keycode: Int): Boolean {
        keyDown[keycode] = true

        val command = keyboardBindings[keycode] ?: return false
        return executeCommand(Command(command, EnumKeyState.Down, 0.0f))
    }

}
