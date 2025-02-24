package refractionInc.isekai.entity

import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.entity.effect.Effect
import refractionInc.isekai.world.World

abstract class EntityActor(
    override val world: World,
    position: Vector2 = Vector2(16f, 16f)
) : Entity {

    val effects = mutableListOf<Effect>()
    val maxHealth = 20

    var health = maxHealth
        private set

    override fun tick(delta: Float) {
        super.tick(delta)
    }

    open fun hurt(damage: Int, damageType: List<EnumDamageType>) {}
    open fun heal(amount: Int) {}

    companion object {
        const val FRICTION = 0.75f
    }
}
