package refractionInc.isekai.entity

import refractionInc.isekai.entity.effect.Effect

abstract class EntityActor(world: Any, worldX: Float, worldY: Float) : Entity(world, worldX, worldY) {
    val effects: MutableList<Effect> = mutableListOf()
    val maxHealth:Int = 20
    var health:Int = maxHealth
        private set

    fun hurt(damage:Int, damageType:List<EnumDamageType>) {}
    fun heal(amount:Int) {}
}
