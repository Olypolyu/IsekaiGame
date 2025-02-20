package refractionInc.isekai.input

interface IInputManager {
    val type: String
    fun assumeControl(callback: (Command<Any>) -> Boolean, caller: Class<Any>? = null)
    fun relinquishControl(caller: Class<Any>)
    fun setDefaultBindings()
    fun captureBindingForCommand(command: EnumCommand)
    fun executeCommand(command: Command<Any>):Boolean
    fun tick()
}
