package refractionInc.isekai

import com.badlogic.gdx.ApplicationAdapter
import refractionInc.isekai.input.AbstractInputManager
import refractionInc.isekai.input.KMInputManager

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
class Main : ApplicationAdapter() {


    val input: AbstractInputManager = KMInputManager()

    override fun render() {
    }

    override fun dispose() {
    }
}
