package refractionInc.isekai

import com.badlogic.gdx.*
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import refractionInc.isekai.entity.Entity
import refractionInc.isekai.input.InputManager
import refractionInc.isekai.utils.*
import refractionInc.isekai.world.LDtkLoadSimpleWorld
import kotlin.math.*

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
object Game : ApplicationAdapter() {

    val viewDistance = 1.5f
// < systems >
    val assetManager = AssetManager()
    val input = InputManager()
    val camera by lazy { OrthographicCamera() }
    val viewport by lazy { FitViewport(16f*16f * viewDistance, 9f*16f * viewDistance, camera) }
    val world by lazy { LDtkLoadSimpleWorld("level/Typical_TopDown_example") }
// </ systems >

// < render >
    val screenSize get() = Vector2(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    private val spriteBatch: SpriteBatch by lazy {SpriteBatch()}
    private val guiBatch by lazy { SpriteBatch() }

    val font by lazy { BitmapFont() }
    var cameraFollow: Entity? = null

// </ render >
    override fun create() {
        Gdx.input.inputProcessor = input

        camera.viewportSize = screenSize
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)
        viewport.update(screenSize.x.toInt(), screenSize.y.toInt())
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        camera.viewportSize = screenSize
        cameraFollow ?: camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)
        viewport.update(width, height)
    }

    override fun render() {
        val delta = Gdx.graphics.deltaTime

        tick(delta)
        draw(delta)
    }

    fun tick(delta: Float) {
        input.tick()
        world.tick(delta)
    }

    fun draw(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        cameraFollow?.let {
            val followPosition = it.position.cpy() ?: return@let

            followPosition.y = max(followPosition.y, camera.viewportHeight/2)
            followPosition.y = min(followPosition.y, world.size.y - (camera.viewportHeight/2))
            followPosition.x = max(followPosition.x, camera.viewportWidth/2)
            followPosition.x = min(followPosition.x, world.size.x - (camera.viewportWidth/2))

            camera.position.set(followPosition, 0f)
        }

        camera.update()
        spriteBatch.projectionMatrix = camera.combined
        spriteBatch.use {
            world.draw(it, delta)
        }

        guiBatch.use {
            font.draw(it, "Hello!", 5f, 15f)
        }
    }
}
