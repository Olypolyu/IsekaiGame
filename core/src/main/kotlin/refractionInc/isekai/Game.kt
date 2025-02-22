package refractionInc.isekai

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import refractionInc.isekai.entity.EntityPlayer
import com.badlogic.gdx.*
import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.input.*
import refractionInc.isekai.world.Layer
import refractionInc.isekai.world.World

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
object Game : ApplicationAdapter() {
    // replace with by lazy and a locator pattern to alternate input device specific managers.
    val input = InputManager()

    private val shapeRenderer: ShapeRenderer by lazy {ShapeRenderer()}
    private val spriteBatch: SpriteBatch by lazy {SpriteBatch()}

    var player: EntityPlayer? = null

    override fun create() {
        Gdx.input.inputProcessor = input

        val world = object : World {
            override val layers: List<Layer>
                get() = TODO("Not yet implemented")
        }
        player = EntityPlayer(world, Vector2(40f, 40f))
        input.assumeControl(player!!, player!!::handleInput)
    }

    override fun render() {
        input.tick()
        tick()
        draw()
    }

    fun tick() {
        player?.tick()
    }

    fun draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.RED;
        shapeRenderer.rect(0f,0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat());
        shapeRenderer.end()

        spriteBatch.begin()
        player?.draw(spriteBatch, player!!.position.x, player!!.position.y)
        spriteBatch.end()
    }

    override fun dispose() {
    }
}
