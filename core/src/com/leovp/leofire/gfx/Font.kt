package com.leovp.leofire.gfx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter

class Font(size: Int) {
    /** The font generated for the game. */
    val font: BitmapFont

    companion object {
        /** Name of the source font used. */
        private const val FONT_NAME = "SourceCodePro-Regular.otf"
    }

    /** Constructor. */
    init {
        val gen = FreeTypeFontGenerator(Gdx.files.internal(FONT_NAME))
        font = gen.generateFont(FreeTypeFontParameter().also {
            it.size = size
            it.magFilter = TextureFilter.Linear
        })
        gen.dispose()
    }
}