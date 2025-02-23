package refractionInc.isekai.entity

import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.entity.effect.Effect
import refractionInc.isekai.utils.times
import refractionInc.isekai.world.World

abstract class EntityActor(
    override val world: World,
    override val position: Vector2,
    override val speed: Vector2 = Vector2(0f, 0f),
    override var rotation: Float = 0f,
    override val size: Vector2 = Vector2(10f, 10f)
) : Entity {
    init { world.entities.add(this) }

    val effects = mutableListOf<Effect>()
    val maxHealth = 20
    var health = maxHealth
        private set

    override fun tick(delta: Float) {
        super.tick(delta)
        println(speed)

        position.x += speed.x * delta
        position.y += speed.y * delta
        speed.set(speed * FRICTION)

        if (speed.x in -0.0001..0.0001) speed.x = 0f
        if (speed.y in -0.0001..0.0001) speed.y = 0f
    }

    open fun hurt(damage: Int, damageType: List<EnumDamageType>) {}
    open fun heal(amount: Int) {}

    companion object {
        const val FRICTION = 0.90f
    }
}
