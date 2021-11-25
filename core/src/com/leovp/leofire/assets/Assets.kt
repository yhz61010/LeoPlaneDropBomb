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

        private fun loadTexture(file: String): Texture = Texture(Gdx.files.internal(file))
        private fun loadAtlas(file: String): TextureAtlas = TextureAtlas(file)

        fun playSound(sound: Sound) {
            sound.play(1f)
        }
    }

    private val assetManager = AssetManager()

    init {
        font = com.leovp.leofire.gfx.Font(6).font

        assetManager.load("menubg.png", Texture::class.java)
        assetManager.load("gamebg.png", Texture::class.java)
        assetManager.load("bomber.png", Texture::class.java)
        assetManager.load("small_bomb.png", Texture::class.java)
        assetManager.load("skyscraper.png", Texture::class.java)
        assetManager.load("explosion.png", Texture::class.java)
        assetManager.load("hud-bomber.png", Texture::class.java)
        assetManager.load("hud-bomb.png", Texture::class.java)
        assetManager.update()

        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"))
        music.setLooping(true)
        music.setVolume(0.5f)
        music.play()

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.mp3"))

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
    }

    fun getMenuBg() {
        while (!assetManager.isLoaded("menubg.png")) assetManager.update()
        menuBg = assetManager.get("menubg.png", Texture::class.java)
    }

    fun finishLoading() {
        assetManager.finishLoading()

        gameBg = assetManager.get("gamebg.png", Texture::class.java)
        bomberTexture = TextureRegion.split(assetManager.get("bomber.png"), 104, 57)[0]
        bombTexture = TextureRegion.split(assetManager.get("small_bomb.png"), 19, 10)[0]
        skyscraperTexture = assetManager.get("skyscraper.png", Texture::class.java)
        explosionTexture = TextureRegion.split(assetManager.get("explosion.png"), 128, 128)[0]
        hudBomberTexture = assetManager.get("hud-bomber.png", Texture::class.java)
        hudBombTexture = assetManager.get("hud-bomb.png", Texture::class.java)
    }

    fun dispose() {
//        if (AssetsManager::playerWalkRunAtlas.isInitialized) playerWalkRunAtlas.dispose()
//        if (AssetsManager::playerIdleAttackAtlas.isInitialized) playerIdleAttackAtlas.dispose()
        font.dispose()
        assetManager.dispose()
    }
}