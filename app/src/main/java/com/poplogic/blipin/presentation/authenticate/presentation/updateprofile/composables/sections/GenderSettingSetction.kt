package com.poplogic.blipin.presentation.authenticate.presentation.updateprofile.composables.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.R
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.domain.user.model.UserEntity
import com.poplogic.blipin.feature.common.bottom_sheet.RoundedBottomSheet
import com.poplogic.blipin.feature.common.theme.Palette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GenderSettingSection(
    selectedGender: UserEntity.Gender?,
    onGenderChanged: (UserEntity.Gender) -> Unit = {},
) {
    val genderOptions = UserEntity.Gender.entries
    val showGenderSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val tempSelectedGender = remember { mutableStateOf<UserEntity.Gender?>(selectedGender) }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = "性別".hardcoded(),
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
                    .clickable {
                        tempSelectedGender.value = selectedGender
                        showGenderSheet.value = true
                    },
        ) {
            OutlinedTextField(
                value = selectedGender?.uiName ?: "",
                onValueChange = {},
                readOnly = true,
                enabled = false,
                placeholder = {
                    Text(
                        text = "選擇性別".hardcoded(),
                        style =
                            MaterialTheme.typography.bodyMedium.copy(
                                color = Palette.Neutral.neutral300,
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight.Normal,
                            ),
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = false)
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

        if (showGenderSheet.value) {
            RoundedBottomSheet(
                bottomSheetState = sheetState,
                onDismiss = {
                    showGenderSheet.value = false
                },
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 20.dp),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                    ) {
                        IconButton(
                            onClick = {
                                showGenderSheet.value = false
                            },
                            modifier = Modifier.align(Alignment.CenterStart),
                        ) {
                            Image(
                                painter = painterResource(R.drawable.icon_navigation_back_large),
                                contentDescription = "Back",
                            )
                        }
                        Text(
                            text = "性別".hardcoded(),
                            modifier = Modifier.align(Alignment.Center),
                            style =
                                MaterialTheme.typography.titleMedium.copy(
                                    color = Palette.Black,
                                    fontSize = 18.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    genderOptions.forEachIndexed { index, option ->
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp)
                                    .clickable { tempSelectedGender.value = option },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = option.uiName,
                                style =
                                    MaterialTheme.typography.bodyLarge.copy(
                                        color = Palette.Black,
                                        fontSize = 20.sp,
                                        lineHeight = 28.sp,
                                        fontWeight = FontWeight.Normal,
                                    ),
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            RadioButton(
                                selected = tempSelectedGender.value == option,
                                onClick = { tempSelectedGender.value = option },
                                colors =
                                    RadioButtonDefaults.colors(
                                        selectedColor = Palette.Primary.brand,
                                        unselectedColor = Palette.Neutral.neutral200,
                                    ),
                            )
                        }
                        if (index != genderOptions.lastIndex) {
                            HorizontalDivider(color = Palette.Neutral.neutral100)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            tempSelectedGender.value?.let { gender ->
                                onGenderChanged(gender)
                                showGenderSheet.value = false
                            }
                        },
                        enabled = tempSelectedGender.value != null,
                        colors =
                            if (tempSelectedGender.value != null) {
                                ButtonDefaults.buttonColors(
                                    containerColor = Palette.Primary.brand,
                                    contentColor = Color.White,
                                )
                            } else {
                                ButtonDefaults.buttonColors(
                                    containerColor = Palette.Primary.brand.copy(alpha = 0.5f),
                                    contentColor = Color.White.copy(alpha = 0.5f),
                                )
                            },
                        shape = RoundedCornerShape(30.dp),
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                    ) {
                        Text(
                            text = "確定".hardcoded(),
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.W500,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}
