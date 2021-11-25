package com.leovp.leofire.framework

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.leovp.leofire.LeoFire

open class DynamicGameObject(x: Float, y: Float, width: Float, height: Float) : GameObject(x, y, width, height) {
    val velocity: Vector2 = Vector2()
    val accel: Vector2 = Vector2()

    private var shapeRenderer: ShapeRenderer? = null

    init {
        if (LeoFire.DEBUG) shapeRenderer = ShapeRenderer()
    }

    open fun drawShapeRenderer(combined: Matrix4, color: Color = Color(0f, 1f, 0f, 0f)) {
        if (LeoFire.DEBUG) shapeRenderer?.let { sr ->
            sr.projectionMatrix = combined
            sr.begin(ShapeRenderer.ShapeType.Line)
            sr.color = color
            sr.rect(x, y, bounds.width, bounds.height)
            sr.end()
        }
    }
}