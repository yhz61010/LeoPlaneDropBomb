package com.leovp.leofire.gamescreens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.leovp.leofire.LeoFire
import com.leovp.leofire.assets.Assets
import com.leovp.leofire.framework.LeoScreen
import com.leovp.leofire.gfx.Background
import com.leovp.leofire.gfx.Font

/**
 * Author: Michael Leo
 * Date: 2021/11/16 17:41
 */
class MainMenuScreen(game: LeoFire) : LeoScreen(game, game.batch) {
    override fun getTagName() = TAG

    private var bg: Background = Background(Assets.menuBg, 0.1f, 0.5f)

    private val glyphLayout = GlyphLayout()
    private val font1 = Font(15).font.apply { color = Color.NAVY }
    private val font2 = Font(10).font.apply { color = Color.ROYAL }
    private val font3 = Font(8).font.apply { color = Color.ROYAL }
    private var gameNamePosX = 0f
    private var startGameTextPosX = 0f
    private var loadingTextPosX = 0f

    init {
        glyphLayout.setText(font1, GAME_NAME)
        val gameNameFontWidth: Float = glyphLayout.width
        gameNamePosX = (camera.viewportWidth - gameNameFontWidth) / 2

        glyphLayout.setText(font2, TEXT_START_GAME)
        val startGameFontWidth: Float = glyphLayout.width
        startGameTextPosX = (camera.viewportWidth - startGameFontWidth) / 2
    }

    override fun show() {
        super.show()
        game.assets.enqueueGameScreenAssets()
    }

    private fun handlerInput() {
        if (Gdx.input.justTouched()) {
            game.assets.loadGameScreenAssets()
            game.screen = GameScreen(game)
            dispose()
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
        }
    }

    override fun update(delta: Float) {
        bg.update(delta)
        if (game.assets.manager.update(30)) {
            handlerInput()
        }
    }

    override fun drawForBlending() {
        font1.draw(batch, GAME_NAME, gameNamePosX, camera.viewportHeight - camera.viewportHeight * 0.3f)
        font2.draw(batch, TEXT_START_GAME, startGameTextPosX, camera.viewportHeight * 0.3f)

        val loadingProgress = game.assets.manager.progress
        if (loadingProgress < 1f) {
            val loadingProgressStr = "${loadingProgress * 100}%"
            glyphLayout.setText(font3, loadingProgressStr)
            loadingTextPosX = (camera.viewportWidth - glyphLayout.width) / 2
            font3.draw(batch, loadingProgressStr, loadingTextPosX, camera.viewportHeight * 0.15f)
        }
    }

    override fun drawForDisableBlending() {
        bg.render(batch)
    }

    override fun dispose() {
        super.dispose()
        font1.dispose()
        font2.dispose()
    }

    companion object {
        private const val TAG = "MainMenuScreen"
        private const val GAME_NAME = "Leo Fire"
        private const val TEXT_START_GAME = "Click to start"
    }
}