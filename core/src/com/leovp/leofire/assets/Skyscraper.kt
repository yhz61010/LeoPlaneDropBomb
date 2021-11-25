package com.leovp.leofire.assets

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.leovp.leofire.framework.DynamicGameObject
import com.leovp.leofire.gfx.ImageBlock

class Skyscraper(x: Float, width: Float, floors: Int) : DynamicGameObject(x, 0f, width, 0f) {
    /** Skyscraper blocks. */
    private val blocks: ImageBlock

    /** Width of each floor. */
    private var floorWidth = width

    /** Height of each floor. */
    private var floorHeight = 0f

    /** Skyscraper type. */
    val type: Int

    /** Starting height of the skyscraper in blocks. */
    val startBlockCount: Int = floors

    /** Current floors of the skyscraper in blocks. */
    private var leftFloorCount: Int = floors

    /** Whether the skyscraper has ever been hit. */
    private var everHit: Boolean

    /** Returns whether the skyscraper has been destroyed. */
    /** Whether the skyscraper has been completely destroyed. */
    var isDestroyed: Boolean
        private set

    init {
        floorHeight = width * 6 / 10
        blocks = ImageBlock(Assets.skyscraperTexture, 4, SKYSCRAPER_TYPES)
        bounds.set(x, 0f, floorWidth, (floors + 1) * floorHeight)
        type = MathUtils.random(SKYSCRAPER_TYPES - 1)
        everHit = false
        isDestroyed = false
    }

    /** Returns whether there is a collision with another entity. */
    fun checkCollision(entity: Rectangle): Boolean {
        val collision = !isDestroyed && entity.overlaps(bounds)
        if (collision) {
            if (everHit) leftFloorCount -= 1
            everHit = true
            bounds.height -= floorHeight
            if (leftFloorCount == 0) isDestroyed = true
        }
        return collision
    }

    /** Extracts the skyscraper block to draw. */
    private fun extractBlock(blockType: Int, leftFloorCount: Int): Sprite {
//        Gdx.app.log(TAG, "extractBlock blockType=$blockType leftFloorCount=$leftFloorCount")
        val sprite = blocks.getFrame(blockType, type)
        sprite.setSize(floorWidth, floorHeight)
        sprite.setPosition(x, (leftFloorCount * floorHeight))
        return sprite
    }

    fun render(sb: SpriteBatch) {
        extractBlock(if (everHit) BLOCK_RUBBLE else BLOCK_TOP, leftFloorCount).draw(sb)
        for (i in 1 until leftFloorCount) extractBlock(BLOCK_BODY, i).draw(sb)
        if (leftFloorCount > 0) extractBlock(BLOCK_BASE, 0).draw(sb)
    }

    companion object {
        private const val TAG = "Skyscraper"

        /** Number of skyscraper types in the texture. */
        private const val SKYSCRAPER_TYPES = 10

        /** Blocks in the order they appear in the texture. */
        private const val BLOCK_RUBBLE = 0
        private const val BLOCK_TOP = 1
        private const val BLOCK_BODY = 2
        private const val BLOCK_BASE = 3
    }
}