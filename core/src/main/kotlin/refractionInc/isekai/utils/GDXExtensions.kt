package refractionInc.isekai.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Vector2

inline fun ShapeRenderer.render(type: ShapeType, block: ShapeRenderer.() -> Unit) {
    begin(type)
    block()
    end()
}

fun Sprite.setPosition(position: Vector2) { setPosition(position.x, position.y) }
fun Sprite.setSize(size: Vector2) { setSize(size.x, size.y) }

operator fun Vector2.component1(): Float = x
operator fun Vector2.component2(): Float = y
operator fun Vector2.minus(vec: Vector2) = Vector2(x - vec.x, y - vec.y)
operator fun Vector2.div(float: Float): Vector2 = this.cpy().scl(float)
operator fun Vector2.times(float: Float) = Vector2(x * float, y * float)

var Camera.viewportSize: Vector2
    set(size: Vector2) {viewportWidth = size.x; viewportHeight = size.y}
    get() = Vector2(viewportWidth, viewportHeight)

val screenSize get() = Vector2(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
