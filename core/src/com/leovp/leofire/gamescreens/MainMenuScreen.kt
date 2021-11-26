package com.leovp.leofire.gamescreens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.leovp.leofire.LeoFire
import com.leovp.leofire.assets.Assets
import com.leovp.leofire.framework.LeoScreen
import com.leovp.leofire.framework.utils.round
import com.leovp.leofire.gfx.Background

/**
 * Author: Michael Leo
 * Date: 2021/11/16 17:41
 */
class MainMenuScreen(game: LeoFire) : LeoScreen(game, game.batch) {
    override fun getTagName() = TAG

    private val stage: Stage
    private val gameNameLabel: Label
    private val gameStartLabel: Label
    private val loadingLabel: Label

    private var bg: Background = Background(Assets.menuBg, 0.2f, 0.5f)

    init {
        val labelNavyStyle = Label.LabelStyle(Assets.font72, Color.NAVY)
        val labelRoyalStyle = Label.LabelStyle(Assets.font36, Color.ROYAL)
        gameNameLabel = Label(GAME_NAME, labelNavyStyle).apply {
            setAlignment(Align.center)
            setSize(Gdx.graphics.width * 1f, Gdx.graphics.height * 1f)
            setPosition(0f, 0f)
        }
        gameStartLabel = Label(TEXT_START_GAME, labelRoyalStyle).apply {
            setAlignment(Align.center)
            setSize(Gdx.graphics.width * 1f, Gdx.graphics.height * 0.45f)
            setPosition(0f, 0f)
        }
        loadingLabel = Label("0%", labelRoyalStyle).apply {
            setAlignment(Align.center)
            setSize(Gdx.graphics.width * 1f, Gdx.graphics.height * 0.2f)
            setPosition(0f, 0f)
        }
        stage = Stage(ScreenViewport(OrthographicCamera()), batch).apply {
            addActor(gameNameLabel)
            addActor(gameStartLabel)
            addActor(loadingLabel)
        }
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
        val loadingProgress = game.assets.manager.progress.round()
        if (loadingProgress < 1f) {
            loadingLabel.setText("${loadingProgress * 100}%")
        } else {
            loadingLabel.remove()
        }
    }

    override fun drawForDisableBlending() {
        bg.render(batch)
    }

    override fun drawWithoutBatchAround() {
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        super.dispose()
        stage.dispose()
    }

    companion object {
        private const val TAG = "MainMenuScreen"
        private const val GAME_NAME = "Leo Fire"
        private const val TEXT_START_GAME = "Click to start"
    }
}