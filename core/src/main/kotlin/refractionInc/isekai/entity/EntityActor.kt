package refractionInc.isekai.entity

import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.entity.effect.Effect
import refractionInc.isekai.world.World
import kotlin.math.abs

abstract class EntityActor(
    override val world: World,
    override val position: Vector2,
    override val speed: Vector2 = Vector2(0f, 0f),
    override var rotation: Float = 0f,
    override val size: Vector2 = Vector2(10f, 10f)
) : Entity {

    init {
        world.entities.add(this)
    }

    val effects = mutableListOf<Effect>()
    val maxHealth = 20
    var health = maxHealth
        private set

    override fun tick() {
        super.tick()
        position.x += speed.x
        position.y += speed.y
        speed.x = (abs(speed.x) -0.1f) * 0.75f * (if (speed.x < 0) -1 else 1)
        speed.y = (abs(speed.y) -0.1f) * 0.75f * (if (speed.y < 0) -1 else 1)
    }

    open fun hurt(damage: Int, damageType: List<EnumDamageType>) {}
    open fun heal(amount: Int) {}
}
