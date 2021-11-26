package com.leovp.leofire.framework

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.leovp.leofire.LeoFire
import com.leovp.leofire.World

/**
 * Author: Michael Leo
 * Date: 2021/11/22 13:30
 */
abstract class LeoScreen(protected val game: LeoFire, protected val batch: SpriteBatch) : Screen { // or implements ScreenAdapter
    abstract fun getTagName(): String
    private val tag: String by lazy { getTagName() }

    /** Active camera. */
    protected var camera: OrthographicCamera = OrthographicCamera().apply { setToOrtho(false, World.WORLD_WIDTH, World.WORLD_HEIGHT) }

    /** Game viewport. */
    private val viewport: Viewport by lazy { FitViewport(World.WORLD_WIDTH, World.WORLD_HEIGHT, camera).apply { apply(true) } }

    /** Update logic */
    protected abstract fun update(delta: Float)
    protected open fun drawForDisableBlending() {}
    protected open fun drawForBlending() {}
    protected open fun drawWithoutBatchAround() {}
    protected open fun drawShapeRenderer() {}

    override fun render(delta: Float) {
        update(delta)

        // clear the screen
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        camera.update()
        batch.projectionMatrix = camera.combined

        batch.disableBlending()
        batch.begin()
        drawForDisableBlending()
        batch.end()

        batch.enableBlending()
        batch.begin()
        drawForBlending()
        batch.end()

        drawWithoutBatchAround()

        if (LeoFire.DEBUG) drawShapeRenderer()
    }

    override fun show() {
        Gdx.app.log(tag, "=====> show() <=====")
    }

    override fun pause() {
        Gdx.app.log(tag, "=====> pause() <=====")
    }

    override fun resume() {
        Gdx.app.log(tag, "=====> resume() <=====")
    }

    override fun hide() {
        Gdx.app.log(tag, "=====> hide() <=====")
    }

    override fun dispose() {
        Gdx.app.log(tag, "=====> dispose() <=====")
    }

    override fun resize(width: Int, height: Int) {
        Gdx.app.log(tag, "=====> resize($width, $height) <=====")
        viewport.update(width, height)
    }
}