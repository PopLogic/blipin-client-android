package com.poplogic.blipin.feature.onboard.presentation.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.R
import com.poplogic.blipin.ui.theme.BlipinTheme

@Composable
fun CheckboxWithLabel(
    isCheck: Boolean,
    label: @Composable () -> Unit,
    onCheckableChanged: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .height(48.dp)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures { onCheckableChanged() }
                },
    ) {
        IconButton(
            modifier = Modifier.height(24.dp),
            onClick = onCheckableChanged,
        ) {
            // 1. Animate the color transition
            val tintColor by animateColorAsState(
                targetValue = if (isCheck) MaterialTheme.colorScheme.primary else Color(0xFF888888),
                animationSpec = tween(durationMillis = 300),
                label = "iconTint",
            )

            // 2. Animate the icon swap
            AnimatedContent(
                targetState = isCheck,
                transitionSpec = {
                    // Fades and scales the new icon in, while fading the old one out
                    (
                        fadeIn(
                            animationSpec =
                                tween(
                                    220,
                                    delayMillis = 90,
                                ),
                        ) + scaleIn(initialScale = 0.92f)
                    ).togetherWith(fadeOut(animationSpec = tween(90)))
                },
                label = "iconTransition",
            ) { checked ->
                Icon(
                    painter =
                        painterResource(
                            if (checked) R.drawable.state_true else R.drawable.state_false,
                        ),
                    contentDescription = "checkbox",
                    tint = tintColor,
                )
            }
        }
        Spacer(modifier = Modifier.width(1.dp))
        label()
    }
}

@Preview
@Composable
fun CheckboxWithLabelPreview() {
    BlipinTheme {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0xFF333333)),
            contentAlignment = Alignment.CenterStart,
        ){
            CheckboxWithLabel(
                isCheck = true,
                label = {
                    BasicText(
                        "我同意 Blipin 的使用條款",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                    )
                },
                onCheckableChanged = {},
            )
        }
    }
}
