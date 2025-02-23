package refractionInc.isekai

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import refractionInc.isekai.entity.Entity
import refractionInc.isekai.input.InputManager
import refractionInc.isekai.utils.use
import refractionInc.isekai.utils.viewportSize
import refractionInc.isekai.world.LDtkLoadSimpleWorld
import kotlin.math.max
import kotlin.math.min

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
object Game : ApplicationAdapter() {

// < systems >
    val assetManager = AssetManager()
    val input = InputManager()
    val camera by lazy { OrthographicCamera() }
    val viewport by lazy { FitViewport(16f*16f, 9f*16f, camera) }
    val world by lazy { LDtkLoadSimpleWorld("level/Typical_TopDown_example/simplified") }
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
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f);
        viewport.update(screenSize.x.toInt(), screenSize.y.toInt())
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        camera.viewportSize = screenSize
        cameraFollow ?: camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)
        viewport.update(width, height)
    }

    override fun render() {
        tick()
        draw()
    }

    fun tick() {
        input.tick()
        world.tick()
    }

    fun draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cameraFollow?.run {
            val followPosition = cameraFollow?.position?.cpy() ?: return@run

            followPosition.y = max(followPosition.y, camera.viewportHeight/2)
            followPosition.y = min(followPosition.y,world.size.y - (camera.viewportHeight/2))
            followPosition.x = max(followPosition.x, camera.viewportWidth/2)
            followPosition.x = min(followPosition.x,world.size.x - (camera.viewportWidth/2))

            camera.position.set(followPosition, 0f)
        }

        camera.update()
        spriteBatch.projectionMatrix = camera.combined
        spriteBatch.use {
            world.draw(this)
        }

        guiBatch.use {
            font.draw(this, "Hello!", 5f, 15f)
        }
    }
}
