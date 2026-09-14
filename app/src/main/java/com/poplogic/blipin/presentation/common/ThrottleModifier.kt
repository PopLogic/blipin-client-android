package com.poplogic.blipin.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * A modifier that applies throttle behavior to prevent rapid repeated clicks.
 *
 * @param throttleMs The time window in milliseconds during which subsequent clicks are ignored.
 *                   Defaults to 300ms.
 * @param onClick The callback to invoke when a click passes the throttle check.
 */
fun Modifier.throttleClick(
    throttleMs: Long = 300L,
    onClick: () -> Unit,
): Modifier =
    composed {
        val lastClickTime = remember { LongArray(1) }

        clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            enabled = true,
            onClick = {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime[0] >= throttleMs) {
                    lastClickTime[0] = currentTime
                    onClick()
                }
            },
        )
    }

/**
 * Extension function to throttle a lambda click handler.
 * Returns a new lambda that will only execute if called after the throttle period.
 *
 * @param throttleMs The time window in milliseconds during which subsequent calls are ignored.
 *                   Defaults to 300ms.
 */
fun (() -> Unit).throttle(throttleMs: Long = 300L): () -> Unit {
    var lastInvokeTime = 0L

    return {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastInvokeTime >= throttleMs) {
            lastInvokeTime = currentTime
            this()
        }
    }
}
