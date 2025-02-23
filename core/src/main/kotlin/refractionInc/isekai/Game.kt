package refractionInc.isekai

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import refractionInc.isekai.entity.Entity
import refractionInc.isekai.input.InputManager
import refractionInc.isekai.utils.screenSize
import refractionInc.isekai.utils.viewportSize
import refractionInc.isekai.world.World
import kotlin.math.max
import kotlin.math.min

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
object Game : ApplicationAdapter() {
    val assetManager = AssetManager()

    // replace with by lazy and a locator pattern to alternate input device specific managers.
    val input = InputManager()

    private val spriteBatch: SpriteBatch by lazy {SpriteBatch()}
    private val guiBatch by lazy { SpriteBatch() }

    val font by lazy { BitmapFont() }

    val world by lazy { World() }

    var cameraFollow: Entity? = null
    val camera by lazy { OrthographicCamera() }

    val viewport by lazy { FitViewport(screenSize.x, screenSize.y, camera) }

    override fun create() {
        Gdx.input.inputProcessor = input

        viewport.update(screenSize.x.toInt(), screenSize.y.toInt())
        camera.viewportSize = screenSize
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f);
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

        spriteBatch.begin()
        world.draw(spriteBatch)
        spriteBatch.end()

    }
}
