package com.example.aireply

import android.content.Context
import android.media.MediaPlayer
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

object VoiceHelper {

    private const val API_KEY = "YOUR_ELEVENLABS_KEY"
    private const val VOICE_ID = "YOUR_VOICE_ID"

    fun generateVoice(text: String): ByteArray {

        val url = URL("https://api.elevenlabs.io/v1/text-to-speech/$VOICE_ID")

        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.setRequestProperty("xi-api-key", API_KEY)
        conn.setRequestProperty("Content-Type", "application/json")
        conn.doOutput = true

        val json = """{"text":"$text"}"""

        conn.outputStream.write(json.toByteArray())

        return conn.inputStream.readBytes()
    }

    fun playAudio(context: Context, audio: ByteArray) {
        val file = File(context.cacheDir, "voice.mp3")
        file.writeBytes(audio)

        val player = MediaPlayer()
        player.setDataSource(file.absolutePath)
        player.prepare()
        player.start()
    }
}