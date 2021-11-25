package com.leovp.leofire.framework

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

open class GameObject(x: Float, y: Float, width: Float, height: Float) {
    val position: Vector2 = Vector2(x, y)
    val bounds: Rectangle = Rectangle(x - width / 2, y - height / 2, width, height)

    var x: Float
        get() = position.x
        set(x) {
            position.x = x
        }

    var y: Float
        get() = position.y
        set(y) {
            position.y = y
        }
}