package com.leovp.leofire.gamescreens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.leovp.leofire.LeoFire
import com.leovp.leofire.assets.*
import com.leovp.leofire.framework.LeoScreen
import com.leovp.leofire.framework.utils.humanReadableByteCount
import com.leovp.leofire.gfx.Background
import kotlin.math.max
import kotlin.math.min

/**
 * Author: Michael Leo
 * Date: 2021/11/16 17:43
 */
class GameScreen(game: LeoFire) : LeoScreen(game, game.batch) {
    override fun getTagName() = TAG

    private var level = 1

    /** Maximum number of bombs active at the same time. */
    private var maxBombs = 3

    /** Array of explosion objects. */
    private val explosions: Array<Explosion> = Array()

    /** Number of skyscrapers standing. */
    private var skyscraperCount = 10

    private val bg: Background = Background(Assets.gameBg, 0.15f, 0.5f)
    private val bomber: Bomber = Bomber(0f, 0f, BOMBER_START_SPEED)

    /** Array of bomb objects. */
    private val bombs: Array<Bomb> = Array()

    /** Array of skyscraper objects. */
    private val skyscrapers: Array<Skyscraper> = Array()

    /** Number of remaining lives. */
    private var lives = 3

    /** Whether the game is over. */
    private var gameOver = false

    /** Whether the bomber has collided with a skyscraper. */
    private var crashed = false

    /** Amount of time passed since the crash. */
    private var timeSinceCrash = 0f

    /** Bonus awarded at each level. */
    private var bonus = 0

    /** Current score. */
    private var score = 0

    /** Heads-up display. */
    private val hud: HUD = HUD(game.batch)

    init {
        Gdx.app.log(TAG, "=====> GameScreen <=====")
        createWorld(level)
    }

    private fun createWorld(level: Int) {
        hud.setLevel(level)
        skyscrapers.clear()
        skyscraperCount = min(3 + level * 2, MAX_SKYSCRAPER_COUNT)
        val skyscraperGap: Float = (camera.viewportWidth - skyscraperCount * SKYSCRAPER_WIDTH) / (skyscraperCount + 1)
        val fixedSkyscraperPos: Float = skyscraperGap + SKYSCRAPER_WIDTH
        val skyscraperMaxFloors = 1 + 2 * level
        Gdx.app.log(TAG, "skyscraperCount=$skyscraperCount skyscraperMaxFloors=$skyscraperMaxFloors")
        for (i in 0 until skyscraperCount) {
            val randomTotalFloors = MathUtils.random(level, skyscraperMaxFloors)
            val skyscraper = Skyscraper(skyscraperGap + i * fixedSkyscraperPos, SKYSCRAPER_WIDTH.toFloat(), randomTotalFloors)
            Gdx.app.log(TAG, "skyscraper[$i] type=${skyscraper.type} pos_x=${skyscraperGap + i * fixedSkyscraperPos} randomTotalFloors=$randomTotalFloors")
            skyscrapers.add(skyscraper)
        }

        bombs.clear()
        bomber.setPosition(0f, camera.viewportHeight - bomber.bounds.height - 9)
        bomber.setSpeed(BOMBER_START_SPEED + level * 5, 0f)

        if (level > 3) {
            if (level % 3 == 1) maxBombs++
            if (level % 3 == 2) lives++
        }
        bonus = LEVEL_BONUS_START
    }

    private fun handleInput() {
        if (Gdx.input.justTouched()) {
            if (bombs.size < maxBombs) {
                val soundId = Assets.playSound(Assets.bombDrop)
                bombs.add(Bomb(bomber.x, bomber.y, soundId))
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) game.screen = MainMenuScreen(game)
    }

    override fun update(delta: Float) {
        handleInput()
        bg.update(delta)
        bomber.update(delta)

//        Gdx.app.log(TAG, "bomber.x=${bomber.x} camera.viewportWidth=${camera.viewportWidth}")
        if (bomber.x > camera.viewportWidth) {
            bomber.moveDown()
            bonus -= LEVEL_BONUS_DROP
            if (bonus < 0) bonus = 0
        }
        // coordinate of the right-most moving entity
        var xMax: Float = bomber.x
        for (bb in bombs) xMax = max(xMax, bb.x)

        checkCollisionWithSkyscrapers(xMax)
        checkLevelComplete()
        updateBombs(delta)
        updateExplosions(delta)

        if (crashed) {
            bomber.moveUp()
            crashed = false
            lives--
        }

        hud.setStatus(lives, maxBombs - bombs.size)

        if (lives == 0) {
            bomber.setPosition(-100f, 100f)
            maxBombs = 0
            timeSinceCrash += delta
            if (timeSinceCrash > 1) gameOver = true
        }
    }

    private fun updateExplosions(delta: Float) {
        for (i in (explosions.size - 1) downTo 0) {
            val ee = explosions[i]
            ee.update(delta)
            if (ee.shouldRemove()) explosions.removeIndex(i)
        }
    }

    private fun updateBombs(delta: Float) {
        for (i in (bombs.size - 1) downTo 0) {
            val bb = bombs[i]
            bb.update(delta)
            if (bb.shouldRemove()) {
                Assets.stopSound(Assets.bombDrop, bb.soundId)
                bombs.removeIndex(i)
            }
        }
    }

    private fun checkLevelComplete() {
        // level completed
        if (skyscraperCount == 0) {
            bomber.moveOffscreen()
            if (bomber.x > camera.viewportWidth - 10 ||
                bomber.y > camera.viewportHeight
            ) {
                score += bonus * level
                hud.setScore(score)
                level++
                createWorld(level)
            }
        }
    }

    private fun checkCollisionWithSkyscrapers(xMax: Float) {
        // Check for collisions with skyscrapers
        for (ss in skyscrapers) {
            // Don't check for collisions with skyscrapers ahead
            if (ss.x - SKYSCRAPER_WIDTH > xMax) break

            // Bomber crashed
            if (ss.checkCollision(bomber.bounds)) {
                Assets.playSound(Assets.explosionSound)
                val adj: Float = bomber.bounds.width
                explosions.add(Explosion(bomber.x + adj, bomber.y))
                explosions.add(Explosion(bomber.x + adj / 3 * 2, bomber.y))
                crashed = true
                if (ss.isDestroyed) skyscraperCount--
            }
            for (j in bombs.size - 1 downTo 0) {
                // bomb hit
                if (ss.checkCollision(bombs[j].bounds)) {
                    Assets.playSound(Assets.explosionSound)
                    val position: Vector2 = bombs[j].position
                    val adj: Float = SKYSCRAPER_WIDTH * 6 / 10f
                    explosions.add(Explosion(position.x, position.y - adj))
                    score += 1
                    Assets.stopSound(Assets.bombDrop, bombs[j].soundId)
                    bombs.removeIndex(j)
                    if (ss.isDestroyed) {
                        skyscraperCount--
                        score += level * ss.startBlockCount * 2
                    }
                    hud.setScore(score)
                }
            }
        }
    }

    override fun drawForDisableBlending() {
        bg.render(batch)
    }

    override fun drawForBlending() {
        if (LeoFire.DEBUG) renderDebugInfo()
        for (ss in skyscrapers) ss.render(batch)
        bomber.render(batch)
        for (bb in bombs) bb.render(batch)
        for (e in explosions) e.render(batch)
    }

    override fun drawWithoutBatchAround() {
        // Draw the hud
        if (gameOver) game.screen = GameOverScreen(game, score) else hud.render()
    }

    override fun drawShapeRenderer() {
        for (ss in skyscrapers) ss.drawShapeRenderer(camera.combined)
        bomber.drawShapeRenderer(camera.combined)
        for (bb in bombs) bb.drawShapeRenderer(camera.combined)
        for (e in explosions) e.drawShapeRenderer(camera.combined, Color(1f, 0f, 0f, 0f))
    }

    private fun renderDebugInfo() {
        val debugInfo = String.format(
            "FPS=%d Sprites=%d\nrenderCalls=%d totalRenderCalls=%d\njavaHeap=%s nativeHeap=%s",
            Gdx.graphics.framesPerSecond, game.batch.maxSpritesInBatch, game.batch.renderCalls, game.batch.totalRenderCalls,
            Gdx.app.javaHeap.humanReadableByteCount(), Gdx.app.nativeHeap.humanReadableByteCount()
        )
        Assets.font72.draw(batch, debugInfo, 10f, camera.viewportHeight - 10)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        hud.resize(width, height)
    }

    companion object {
        private const val TAG = "GameScreen"

        /** Speed of the bomber at the start of the game. */
        private const val BOMBER_START_SPEED = 10f

        /** Maximum number of skyscrapers generated. */
        private const val MAX_SKYSCRAPER_COUNT = 21

        /** Width of a skyscraper block. */
        private const val SKYSCRAPER_WIDTH = 10

        /** Value of the bonus at the start of each level. */
        private const val LEVEL_BONUS_START = 60

        /** Drop in bonus at each row. */
        private const val LEVEL_BONUS_DROP = 5
    }
}