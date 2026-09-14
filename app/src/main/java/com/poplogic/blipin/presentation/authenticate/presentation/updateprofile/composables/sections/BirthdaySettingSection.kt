package com.poplogic.blipin.presentation.authenticate.presentation.updateprofile.composables.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.theme.Palette
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdaySettingSection(
    birthday: Instant? = null,
    onBirthdayChanged: (Instant) -> Unit = {},
) {
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = birthday?.toEpochMilliseconds())
    val showDatePickerDialog = remember { mutableStateOf(false) }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = "生日".hardcoded(),
            style =
                MaterialTheme.typography.bodyMedium.copy(
                    color = Palette.Neutral.neutral500,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Medium,
                ),
        )

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable { showDatePickerDialog.value = true },
        ) {
            OutlinedTextField(
                value = birthday?.toDisplayDate().orEmpty(),
                onValueChange = {},
                readOnly = true,
                enabled = false,
                placeholder = {
                    Text(
                        text = "請輸入西元出生年月日".hardcoded(),
                        style =
                            MaterialTheme.typography.bodyMedium.copy(
                                color = Palette.Neutral.neutral300,
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight.Normal,
                            ),
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors =
                    OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = Palette.Neutral.neutral300,
                        disabledTextColor = Palette.Black,
                        disabledPlaceholderColor = Palette.Neutral.neutral300,
                        disabledTrailingIconColor = Palette.Neutral.neutral500,
                        disabledLeadingIconColor = Palette.Neutral.neutral500,
                        disabledContainerColor = Palette.White,
                    ),
            )
        }
    }

    if (showDatePickerDialog.value) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                            onBirthdayChanged(Instant.fromEpochMilliseconds(selectedDateMillis))
                        }
                        showDatePickerDialog.value = false
                    },
                ) {
                    Text("確定")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerDialog.value = false }) {
                    Text("取消")
                }
            },
        ) {
            DatePicker(
                modifier = Modifier.fillMaxWidth(),
                state = datePickerState,
            )
        }
    }
}

private fun Instant.toDisplayDate(): String {
    val date = toLocalDateTime(TimeZone.currentSystemDefault()).date
    return buildString {
        append(date.year)
        append('/')
        append(date.monthNumber.toString().padStart(2, '0'))
        append('/')
        append(date.dayOfMonth.toString().padStart(2, '0'))
    }
}
