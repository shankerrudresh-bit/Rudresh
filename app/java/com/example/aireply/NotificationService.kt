package com.example.aireply

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {

        val pkg = sbn.packageName

        if (pkg.contains("whatsapp") || pkg.contains("instagram")) {

            val msg = sbn.notification.extras
                .getCharSequence("android.text")?.toString() ?: return

            Thread {
                val reply = OpenAIHelper.getReply(msg)

                // Voice
                val audio = VoiceHelper.generateVoice(reply)
                VoiceHelper.playAudio(this, audio)

                // Auto send
                AutoReplyService.replyText = reply
            }.start()
        }
    }
}