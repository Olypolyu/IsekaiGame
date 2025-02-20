package refractionInc.isekai.input

enum class EnumCommand {
    MoveNorth,
    MoveSouth,
    MoveEast,
    MoveWest,
    Hit,
    Interact,
    OpenMainMenu,
    Tab
}

enum class EnumKeyState {
    Pressed,
    Lifted,
    Held,
}

class Command<out T>(val type: EnumCommand, val state: EnumKeyState, val value: T)
