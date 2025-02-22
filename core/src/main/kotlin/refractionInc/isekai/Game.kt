package refractionInc.isekai

import com.badlogic.gdx.*
import refractionInc.isekai.input.*

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
object Game : ApplicationAdapter() {
    // replace with by lazy and a locator pattern to alternate input device specific managers.
    val input = InputManager()

    override fun create() {
        Gdx.input.inputProcessor = input
        input.assumeControl(this) { command ->
            println("[${command.type}] ${command.state} ${command.value}")
            false
        }
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
