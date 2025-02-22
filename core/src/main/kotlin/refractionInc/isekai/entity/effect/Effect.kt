package refractionInc.isekai.entity.effect

interface Effect {
    val duration: Int
    val name: String
    val iconPath: String

    fun tick()
}
