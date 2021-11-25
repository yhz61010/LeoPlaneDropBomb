package com.leovp.leofire.assets

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.leovp.leofire.framework.DynamicGameObject

class Bomber(x: Float, y: Float, speed: Float) : DynamicGameObject(
    x, y,
    Assets.bomberTexture[0].regionWidth * SCALE, Assets.bomberTexture[0].regionHeight * SCALE
) {
    /** Animation representing the bomber. */
    private val animation: Animation<TextureRegion> = Animation(0.25f, *Assets.bomberTexture).apply { playMode = Animation.PlayMode.LOOP }

    /** Time since the animation has started. */
    private var stateTime = 0f

    /** Constructor. */
    init {
        velocity.set(speed, 0f)
    }

    /** Moves the bomber down one row. */
    fun moveDown() {
        position.x = -bounds.width
        position.y += POSITION_CHANGE
        velocity.x += SPEED_CHANGE
    }

    /** Moves the bomber up one row. */
    fun moveUp() {
        position.y -= (2 * POSITION_CHANGE)
        velocity.x -= SPEED_CHANGE
    }

    /** Moves the bomber off the screen. */
    fun moveOffscreen() {
        velocity.x += 2f
        velocity.y += 1f
    }

    fun update(dt: Float) {
        stateTime += dt
        position.add(velocity.x * dt, velocity.y * dt)
        if (y < 0) position.y = 0f
        bounds.setPosition(position)
    }

    fun render(sb: SpriteBatch) {
        sb.draw(
            animation.getKeyFrame(stateTime),
            position.x, position.y, bounds.width, bounds.height
        )
    }

    /** Sets the bomber coordinates. */
    fun setPosition(x: Float, y: Float) = position.set(x, y)

    /** Sets the bomber speed. */
    fun setSpeed(x: Float, y: Float) = velocity.set(x, y)

    companion object {
        /** Scaling factor for the texture. */
        private const val SCALE = 0.12f

        /** Increase in speed at each row. */
        private const val SPEED_CHANGE = 3f

        /** Change in height at each row. */
        private const val POSITION_CHANGE = -8f
    }
}