package com.leovp.leofire.assets

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.leovp.leofire.framework.DynamicGameObject

class Bomb(x: Float, y: Float) : DynamicGameObject(
    x, y,
    Assets.bombTexture[0].regionWidth * SCALE, Assets.bombTexture[0].regionHeight * SCALE
) {
    /** Animation representing the bomb. */
    private val animation: Animation<TextureRegion> = Animation(0.15f, *Assets.bombTexture).apply { playMode = Animation.PlayMode.LOOP }

    /** Time since the animation has started. */
    private var stateTime = 0f

    /** Constructor. */
    init {
        velocity.y = GRAVITY
    }

    /** Returns whether the bomb should be removed. */
    fun shouldRemove(): Boolean {
        return position.y < -bounds.height
    }

    fun update(dt: Float) {
        stateTime += dt
        velocity.add(0f, GRAVITY)
        position.add(0f, velocity.y * dt)
        if (position.y < -bounds.height) position.y = -1000f
        bounds.setPosition(position)
    }

    fun render(sb: SpriteBatch) {
        sb.draw(animation.getKeyFrame(stateTime), position.x, position.y, bounds.width, bounds.height)
    }

    companion object {
        /** Scaling factor for the texture. */
        private const val SCALE = 0.2f

        /** Amount of gravity. */
        private const val GRAVITY = -1f
    }
}