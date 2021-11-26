package com.leovp.leofire.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

/**
 * Author: Michael Leo
 * Date: 2021/11/17 10:05
 *
 * https://github.com/libgdx/libgdx/wiki/Managing-your-assets
 */
class Assets {
//    private lateinit var playerWalkRunAtlas: TextureAtlas
//    private lateinit var playerIdleAttackAtlas: TextureAtlas
//
//    lateinit var playerWalk: Animation<AtlasRegion>
//    lateinit var playerRun: Animation<AtlasRegion>
//    lateinit var playerIdle: Animation<AtlasRegion>
//    lateinit var playerAttack: Animation<AtlasRegion>

    companion object {
        lateinit var font: BitmapFont

        lateinit var menuBg: Texture
        lateinit var gameBg: Texture
        lateinit var bomberTexture: Array<TextureRegion>
        lateinit var bombTexture: Array<TextureRegion>
        lateinit var explosionTexture: Array<TextureRegion>
        lateinit var skyscraperTexture: Texture
        lateinit var hudBomberTexture: Texture
        lateinit var hudBombTexture: Texture

        lateinit var music: Music
        lateinit var explosionSound: Sound
        lateinit var bombDrop: Sound

        private fun loadTexture(file: String): Texture = Texture(Gdx.files.internal(file))
        private fun loadAtlas(file: String): TextureAtlas = TextureAtlas(file)

        fun playSound(sound: Sound, vol: Float = 1f): Long = sound.play(vol)

        fun stopSound(sound: Sound, soundId: Long = -1) = if (soundId > -1) sound.stop(soundId) else sound.stop()
    }

    val manager = AssetManager()

//    fun load() {
//        playerWalkRunAtlas = loadAtlas("data/entity_1_walk_run.atlas.txt")
//        val playerWalkRegions: Array<AtlasRegion> = playerWalkRunAtlas.findRegions("1_entity_000_WALK")!!
//        playerWalk = Animation(0.1f, playerWalkRegions, Animation.PlayMode.LOOP)
//        val playerRunRegions: Array<AtlasRegion> = playerWalkRunAtlas.findRegions("1_entity_000_RUN")!!
//        // Run speed: 10% faster than walk.
//        playerRun = Animation(0.1f * (1 - 0.1f), playerRunRegions, Animation.PlayMode.LOOP)
//
//        playerIdleAttackAtlas = loadAtlas("data/entity_1_idle_attack.atlas.txt")
//        val playerIdleRegions: Array<AtlasRegion> = playerIdleAttackAtlas.findRegions("1_entity_000_IDLE")!!
//        playerIdle = Animation(0.1f, playerIdleRegions, Animation.PlayMode.LOOP)
//        val playerAttackRegions: Array<AtlasRegion> = playerIdleAttackAtlas.findRegions("1_entity_000_ATTACK")!!
//        playerAttack = Animation(0.1f, playerAttackRegions, Animation.PlayMode.LOOP)
//    }

    /**
     * Make sure only the necessary assets are loaded for the first screen.
     * Other assets should be loaded when [Loading Screen] are displayed.
     */
    fun loadMainMenuScreenAssets() {
        font = com.leovp.leofire.gfx.Font(6).font
        manager.load("menubg.png", Texture::class.java)
        manager.load("music.mp3", Music::class.java)
        // Block until all the assets that have been queued are actually done loading.
        manager.finishLoading()

        menuBg = manager.get("menubg.png", Texture::class.java)
//        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3")).apply {
        music = manager.get("music.mp3", Music::class.java).apply {
            isLooping = true
            volume = 0.5f
            play()
        }
    }

    fun enqueueGameScreenAssets() {
        // Enqueue those assets for loading. The assets will be loaded in the order we called the AssetManager.load() method.
        // At this time, all the assets are only queued to be loaded. The AssetManager does not yet load anything.
        // To kick this off we have to call AssetManager.update() continuously to check whether all loading is finished.
        manager.load("gamebg.png", Texture::class.java)
        manager.load("bomber.png", Texture::class.java)
        manager.load("small_bomb.png", Texture::class.java)
        manager.load("skyscraper.png", Texture::class.java)
        manager.load("explosion.png", Texture::class.java)
        manager.load("hud-bomber.png", Texture::class.java)
        manager.load("hud-bomb.png", Texture::class.java)
        manager.load("explosion.mp3", Sound::class.java)
        manager.load("drop_bomb.mp3", Sound::class.java)
    }

    fun loadGameScreenAssets() {
        gameBg = manager.get("gamebg.png", Texture::class.java)
        bomberTexture = TextureRegion.split(manager.get("bomber.png"), 104, 57)[0]
        bombTexture = TextureRegion.split(manager.get("small_bomb.png"), 19, 10)[0]
        skyscraperTexture = manager.get("skyscraper.png", Texture::class.java)
        explosionTexture = TextureRegion.split(manager.get("explosion.png"), 128, 128)[0]
        hudBomberTexture = manager.get("hud-bomber.png", Texture::class.java)
        hudBombTexture = manager.get("hud-bomb.png", Texture::class.java)
//        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.mp3"))
//        bombDrop = Gdx.audio.newSound(Gdx.files.internal("drop_bomb.mp3"))
        explosionSound = manager.get("explosion.mp3", Sound::class.java)
        bombDrop = manager.get("drop_bomb.mp3", Sound::class.java)
    }

    fun dispose() {
//        if (AssetsManager::playerWalkRunAtlas.isInitialized) playerWalkRunAtlas.dispose()
//        if (AssetsManager::playerIdleAttackAtlas.isInitialized) playerIdleAttackAtlas.dispose()
        font.dispose()
        manager.dispose()
    }
}