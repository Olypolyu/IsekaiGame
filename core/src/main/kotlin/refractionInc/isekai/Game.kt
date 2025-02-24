package refractionInc.isekai

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import refractionInc.isekai.entity.Entity
import refractionInc.isekai.input.EnumCommand
import refractionInc.isekai.input.EnumKeyState
import refractionInc.isekai.input.InputManager
import refractionInc.isekai.utils.use
import refractionInc.isekai.world.LDtkLoadSimpleWorld
import java.text.DecimalFormat
import kotlin.math.max
import kotlin.math.min

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
object Game : ApplicationAdapter() {

// < var >
    // don't fucking as me why 2.22 but i couldn't find anything else that works.
    val scaledScreenHeight get() = viewport.worldHeight * 2.22f
    var debug = false

// </ var >

// < systems >
    val assetManager = AssetManager()
    val input = InputManager()
    val world by lazy { LDtkLoadSimpleWorld("level/Typical_TopDown_example") }
// </ systems >

// < render >
    private val debugPhysRenderer by lazy { Box2DDebugRenderer() }
    private val spriteBatch: SpriteBatch by lazy {SpriteBatch()}
    private val guiBatch by lazy { SpriteBatch() }

    val font by lazy { BitmapFont() }

    val camera by lazy { OrthographicCamera() }
    var cameraFollow: Entity? = null

    val viewport by lazy { FitViewport(256f * viewDistance, 144f * viewDistance, camera) }
    var viewDistance = 1.5f
        set (value) {
            field = value;
            viewport.worldHeight = 144f * value
            viewport.worldWidth = 256f * value
        }

// </ render >

    override fun create() {
        Gdx.input.inputProcessor = input
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)

        input.assumeControl(this) {
            if (it.state == EnumKeyState.Up && it.type == EnumCommand.OpenDebugInfo) {
                debug = !debug
                return@assumeControl true
            }
            false
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        cameraFollow ?: camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)
        viewport.update(width, height)
    }

    override fun render() {
        val delta = min(Gdx.graphics.deltaTime,  0.25f)
        tick(delta)
        draw(delta)
    }

    private fun tick(delta: Float) {
        input.tick()
        world.tick(delta)
    }

    private fun draw(delta: Float) {
        cameraFollow?.let {
            val followPosition = it.physBody.position.cpy() ?: return@let

            followPosition.y = max(followPosition.y, camera.viewportHeight/2)
            followPosition.y = min(followPosition.y, world.size.y - (camera.viewportHeight/2))
            followPosition.x = max(followPosition.x, camera.viewportWidth/2)
            followPosition.x = min(followPosition.x, world.size.x - (camera.viewportWidth/2))

            camera.position.set(followPosition, 0f)
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        camera.update()
        spriteBatch.projectionMatrix = camera.combined

        spriteBatch.use {
            world.draw(it, delta)
        }

        guiBatch.use {
            if (debug) renderDebugInfo(it)
        }

        if (debug) debugPhysRenderer.render(world.physWorld, camera.combined)
    }

    private fun renderDebugInfo(batch: SpriteBatch) {
        var offsetY = scaledScreenHeight -5f
        val writeInfo: (Any) -> Unit =  {
            font.draw(batch, it.toString(), 5f, offsetY)
            offsetY -= 15f
        }

        val dc = DecimalFormat("#.00")
        writeInfo("FPS: ${Gdx.graphics.framesPerSecond}")
        world.player.physBody.position.apply { writeInfo("X:${dc.format(x)} Y:${dc.format(y)}") }
        writeInfo("Entities:${world.entityCount}")
        writeInfo("PHYS:${world.physWorld.bodyCount}")

    }
}
