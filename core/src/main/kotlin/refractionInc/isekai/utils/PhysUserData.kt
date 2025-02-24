package refractionInc.isekai.utils

import refractionInc.isekai.entity.Entity

// basically, we just attach this to every phys body, so we can retrieve the entity back. later we add other shite.
data class PhysUserData (
    var entity: Entity
)
