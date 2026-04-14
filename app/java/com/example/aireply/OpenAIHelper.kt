package com.example.aireply

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object OpenAIHelper {

    private const val API_KEY = "YOUR_OPENAI_KEY"

    fun getReply(message: String): String {

        val url = URL("https://api.openai.com/v1/chat/completions")

        val json = """
        {
          "model": "gpt-4o-mini",
          "messages": [
            {"role":"system","content":"Reply like me, short casual"},
            {"role":"user","content":"$message"}
          ]
        }
        """

        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.setRequestProperty("Authorization", "Bearer $API_KEY")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.doOutput = true

        conn.outputStream.write(json.toByteArray())

        val response = conn.inputStream.bufferedReader().readText()

        return JSONObject(response)
            .getJSONArray("choices")
            .getJSONObject(0)
            .getJSONObject("message")
            .getString("content")
    }
}