package com.leovp.leofire.gamescreens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.leovp.leofire.assets.Assets
import java.util.*

class HUD(private val sb: SpriteBatch) : Disposable {

    /** Scene graph for the HUD information. */
    private val stage: Stage

    /** Label reporting the current level. */
    private val levelLabel: Label

    /** Label reporting the current score. */
    private val scoreLabel: Label

    /** Graphics for the HUD elements. */
    private val bomber: Sprite = Sprite(Assets.hudBomberTexture)
    private val bomb: Sprite = Sprite(Assets.hudBombTexture)

    /** Current number of lives. */
    private var availableLives = 0

    /** Current number of bombs. */
    private var availableBombs = 0

    init {
        // labels
        val labelStyle = LabelStyle(Assets.fontNormal, Color.WHITE)
        levelLabel = Label(String.format(Locale.US, levelFormat, 1), labelStyle)
        scoreLabel = Label(String.format(Locale.US, scoreFormat, 0), labelStyle)

        // table to organize all the labels
        val table = Table().top()
        table.setFillParent(true)
        table.add(levelLabel).expandX().padTop(10f)
        table.add(scoreLabel).expandX().padTop(10f)

        // stage
        stage = Stage(ScreenViewport(OrthographicCamera()), sb).apply { addActor(table) }
    }

    /** Sets the current level. */
    fun setLevel(level: Int) {
        levelLabel.setText(String.format(Locale.US, levelFormat, level))
    }

    /** Sets the current score. */
    fun setScore(score: Int) {
        scoreLabel.setText(String.format(Locale.US, scoreFormat, score))
    }

    /** Sets the current status to be displayed. */
    fun setStatus(lives: Int, bombs: Int) {
        availableLives = lives
        availableBombs = bombs
    }

    fun render() {
        sb.projectionMatrix = stage.camera.combined
        sb.begin()
        var x = 20f
        val y = stage.height - 35
        for (i in 0 until availableLives) sb.draw(bomber, x + 45 * i, y, 104f / 3, 57f / 3)
        x = stage.width - 100
        for (i in 0 until availableBombs) sb.draw(bomb, x + 15 * i, y, 12f, 20f)
        sb.end()
        stage.viewport.apply()
        stage.draw()
    }

    fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.dispose()
    }

    companion object {
        /** Format string for the level. */
        private const val levelFormat = "Level: %2d"

        /** Format string for the score. */
        private const val scoreFormat = "Score: %3d"
    }
}