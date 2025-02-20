package refractionInc.isekai

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import refractionInc.isekai.input.Command
import refractionInc.isekai.input.EnumCommand
import refractionInc.isekai.input.IInputManager
import refractionInc.isekai.input.KMInputManager

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
object Game : ApplicationAdapter() {

    // replace with by lazy and a locator pattern to alternate input device specific managers.
    val input: IInputManager = KMInputManager()

    override fun create() {
        Gdx.input.inputProcessor = input as InputAdapter
        input.assumeControl(this::handleInput, this.javaClass)
    }

    fun handleInput(command: Command<Any>): Boolean{
        println("[${command.type}] ${command.state} ${command.value}")
        return false
    }

    override fun render() {
        input.tick()
        tick()
        draw()
    }

    fun tick() {
    }

    fun draw() {
    }

    override fun dispose() {
    }
}
