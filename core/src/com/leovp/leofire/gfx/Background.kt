package com.leovp.leofire.gfx

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils

class Background(texture: Texture, scale: Float, bgSpeed: Float) {
    /** Image to display in the background. */
    private val background: Sprite = Sprite(texture).apply {
        setScale(scale)
        setOrigin(0f, 0f)
    }

    /** Speed of movement of the background. */
    private val speed: Float = bgSpeed

    /** Width of the background image after scaling. */
    private val scaledWidth: Float = background.width * scale

    /** Current horizontal coordinate. */
    private var x: Float

    /** Constructor. */
    init {
        x = -MathUtils.random(scaledWidth.toInt() / 2).toFloat()
    }

    fun update(delta: Float) {
        x = (x - speed * delta) % scaledWidth
    }

    fun render(sb: SpriteBatch) {
        sb.disableBlending()
        background.setPosition(x, 0f)
        background.draw(sb)
        background.setPosition(x + scaledWidth, 0f)
        background.draw(sb)
        sb.enableBlending()
    }
}