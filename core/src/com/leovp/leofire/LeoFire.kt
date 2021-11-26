package com.leovp.leofire

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.leovp.leofire.assets.Assets
import com.leovp.leofire.gamescreens.MainMenuScreen

class LeoFire : Game() {
    lateinit var batch: SpriteBatch
    lateinit var assets: Assets

    override fun create() {
        batch = SpriteBatch()
        // use LibGDX's default Arial font

        assets = Assets()
        Gdx.app.log(TAG, "Loading MainMenuScreen assets...")
        assets.loadMainMenuScreenAssets()
        Gdx.app.log(TAG, "Prepare to goto MainMenuScreen")
        setScreen(MainMenuScreen(this))
    }

    override fun render() {
        Gdx.gl.apply {
            glClearColor(1.0f, 1.0f, 1.0f, 0.0f)
            glClear(GL20.GL_COLOR_BUFFER_BIT)
        }
        super.render()
    }

    override fun dispose() {
        getScreen().dispose()
        batch.dispose()
        assets.dispose()
    }

    companion object {
        private const val TAG = "LeoFire"
        const val DEBUG = false
    }
}