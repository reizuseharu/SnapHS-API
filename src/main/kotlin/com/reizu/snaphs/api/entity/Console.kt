package com.reizu.snaphs.api.entity

enum class Console(name: String, isEmulated: Boolean) {

    N64("N64", false),

    N64_EMULATED("N64", true),

    WII_VC("WiiVC", false),

    WII_VC_EMULATED("WiiVC", true),

    WII_U_VC("WiiUVC", false),

    WII_U_VC_EMULATED("WiiUVC", true)

}
