@file:Suppress("NOTHING_TO_INLINE")
package refractionInc.isekai.utils

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Vector2

inline fun <T : ShapeRenderer> T.render(type: ShapeType, block: (T) -> Unit) {
    begin(type)
    block(this)
    end()
}

inline fun <T : Batch> T.use(block: (T) -> Unit) {
    begin()
    block(this)
    end()
}

fun Sprite.setPosition(position: Vector2) { setPosition(position.x, position.y) }
fun Sprite.setSize(size: Vector2) { setSize(size.x, size.y) }

operator fun Vector2.component1(): Float = x
operator fun Vector2.component2(): Float = y

operator fun Vector2.plus(vec: Vector2): Vector2 = cpy().add(vec)
operator fun Vector2.minus(vec: Vector2) = cpy().add(-vec.x, -vec.y)

operator fun Vector2.times(float: Float): Vector2 = cpy().scl(float)
operator fun Vector2.div(float: Float): Vector2 = cpy().scl(1/float)

var Camera.viewportSize: Vector2
    set(size) { viewportWidth = size.x; viewportHeight = size.y }
    get() = Vector2(viewportWidth, viewportHeight)
