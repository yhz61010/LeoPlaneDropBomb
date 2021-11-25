package com.leovp.leofire.gfx

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.Array

class ImageBlock(
    texture: Texture,
    /** Number of rows in the texture. */
    private val rowCount: Int, columnCount: Int
) {
    /** Array of image blocks. */
    private val blocks: Array<Sprite> = Array()

    /** Returns the frame at the given coordinates. */
    fun getFrame(row: Int, col: Int): Sprite {
//        Gdx.app.log(TAG, "ImageBlock getFrame($row, $col)=${row + col * rowCount}")
        return blocks[row + col * rowCount]
    }

    /** Constructor. */
    init {
        // dimensions of each block
        val width = texture.width / columnCount
        val height = texture.height / rowCount

        // extract the blocks in column-major order
        for (col in 0 until columnCount) {
            for (row in 0 until rowCount) {
//                Gdx.app.log(TAG, "${col * rowCount + row}: [$row, $col]=${col * width}, ${row * height}")
                blocks.add(
                    Sprite(
                        texture, col * width, row * height,
                        width, height
                    )/*.also {
                        it.color = when (row) {
                            0 -> Color.RED
                            1 -> Color.GREEN
                            2 -> Color.BLUE
                            3 -> Color.BROWN
                            else -> Color.YELLOW
                        }
                    }*/
                )
            }
        }
    }

    companion object {
        private const val TAG = "ImageBlock"
    }
}