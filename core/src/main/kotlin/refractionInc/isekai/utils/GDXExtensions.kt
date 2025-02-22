package refractionInc.isekai.utils

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Vector2

inline fun ShapeRenderer.render(type: ShapeType, block: ShapeRenderer.() -> Unit) {
    begin(type)
    block()
    end()
}

operator fun Vector2.component1(): Float = x
operator fun Vector2.component2(): Float = y