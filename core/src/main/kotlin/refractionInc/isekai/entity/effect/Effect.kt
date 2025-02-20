package refractionInc.isekai.entity.effect

abstract class Effect(val duration:Int) {
    abstract val name:String
    abstract val iconPath: String
    fun tick() {}
}
