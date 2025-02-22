package refractionInc.isekai.entity

import com.badlogic.gdx.math.Vector2
import refractionInc.isekai.entity.effect.Effect
import refractionInc.isekai.world.World

abstract class EntityActor(
    override val world: World,
    override val position: Vector2,
    override val speed: Vector2 = Vector2(0f, 0f),
    override var rotation: Float = 0f,
    override val size: Vector2 = Vector2(10f, 10f)
) : Entity {
    val effects = mutableListOf<Effect>()
    val maxHealth = 20
    var health = maxHealth
        private set

    fun hurt(damage: Int, damageType: List<EnumDamageType>) {}
    fun heal(amount: Int) {}
}
