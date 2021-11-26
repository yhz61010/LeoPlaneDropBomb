package com.leovp.leofire.gamescreens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import com.leovp.leofire.LeoFire
import com.leovp.leofire.World
import com.leovp.leofire.assets.Assets
import com.leovp.leofire.framework.LeoScreen
import com.leovp.leofire.gfx.Background
import com.leovp.leofire.gfx.Font
import java.util.*

class GameOverScreen(game: LeoFire, score: Int) : LeoScreen(game, game.batch) {
    override fun getTagName() = TAG

    /** Scene graph for the game over state. */
    private val stage: Stage

    /** Background image. */
    private val background: Background = Background(Assets.gameBg, 0.15f, 0.5f)

    init {
        val labelStyle = LabelStyle(Font(42).font, Color.SKY)
        // table to organize all the labels
        val table = Table().apply {
            setFillParent(true)
            add(Label("Game Over", labelStyle)).expandX()
            row()
            add(Label(String.format(Locale.US, "Score: %d", score), labelStyle)).expandX()
        }

        // stage
        stage = Stage(FitViewport(World.WORLD_WIDTH * 5, World.WORLD_HEIGHT * 5, OrthographicCamera()), batch).apply {
            addActor(table)
            draw()
        }
    }

    private fun handleInput() {
        if (Gdx.input.justTouched()) game.screen = MainMenuScreen(game)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit()
    }

    override fun update(delta: Float) {
        handleInput()
        background.update(delta)
    }

    override fun drawWithoutBatchAround() {
        stage.draw()
    }

    override fun drawForDisableBlending() {
        background.render(batch)
    }

    override fun dispose() {
        super.dispose()
        stage.dispose()
    }

    companion object {
        private const val TAG = "GameOver"
    }
}