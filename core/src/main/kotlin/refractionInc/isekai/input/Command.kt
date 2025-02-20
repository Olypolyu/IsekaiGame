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
    Down,
    Up,
    Held,
}

class Command<out T>(val type: EnumCommand, val state: EnumKeyState, val value: T)
