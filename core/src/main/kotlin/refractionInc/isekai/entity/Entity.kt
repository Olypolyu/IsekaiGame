package refractionInc.isekai.entity

abstract class Entity(private val world:Any, worldX:Float, worldY:Float) {
    var rotation:Float = 0f
    val sizeX:Int = 10
    val sizeY:Int = 10
    val speedX:Float = 0f
    val speedY:Float = 0f
    abstract fun draw(screenX:Int, screenY:Int)
    abstract fun tick()
}
