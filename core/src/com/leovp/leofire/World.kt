package com.leovp.leofire

import com.badlogic.gdx.math.Vector2

/**
 * Author: Michael Leo
 * Date: 2021/11/18 14:18
 */
class World {
    companion object {
        const val WORLD_WIDTH = 120f * 16 / 9
        const val WORLD_HEIGHT = 120f
        val gravity = Vector2(0.1f, 0f)
    }
}