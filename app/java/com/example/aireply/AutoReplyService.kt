package com.example.aireply

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.os.Bundle

class AutoReplyService : AccessibilityService() {

    companion object {
        var replyText: String = ""
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {

        if (event.packageName == "com.whatsapp") {

            val root = rootInActiveWindow ?: return

            val input = root.findAccessibilityNodeInfosByViewId(
                "com.whatsapp:id/entry"
            )

            val send = root.findAccessibilityNodeInfosByViewId(
                "com.whatsapp:id/send"
            )

            if (input.isNotEmpty()) {
                val bundle = Bundle()
                bundle.putCharSequence(
                    AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    replyText
                )
                input[0].performAction(
                    AccessibilityNodeInfo.ACTION_SET_TEXT, bundle
                )
            }

            if (send.isNotEmpty()) {
                send[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }
    }

    override fun onInterrupt() {}
}