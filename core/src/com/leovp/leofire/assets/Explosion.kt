package com.leovp.leofire.assets

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.leovp.leofire.framework.DynamicGameObject

class Explosion(x: Float, y: Float) : DynamicGameObject(x, y, Assets.explosionTexture[0].regionWidth * SCALE, Assets.explosionTexture[0].regionHeight * SCALE) {
    /** Animation representing the explosion. */
    private val animation: Animation<TextureRegion> = Animation(0.05f, *Assets.explosionTexture)

    /** Time since the animation has started. */
    private var stateTime = 0f

    /** Returns whether the explosion should be removed. */
    fun shouldRemove(): Boolean {
        return animation.isAnimationFinished(stateTime)
    }

    fun update(dt: Float) {
        stateTime += dt
    }

    fun render(sb: SpriteBatch) {
        sb.draw(animation.getKeyFrame(stateTime), x, y - 5, bounds.width, bounds.height)
    }

    companion object {
        /** Scaling factor for the texture. */
        private const val SCALE = 0.1f
    }
}