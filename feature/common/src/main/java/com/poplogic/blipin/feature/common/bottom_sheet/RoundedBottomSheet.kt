package com.poplogic.blipin.feature.common.bottom_sheet

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.feature.common.theme.Palette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundedBottomSheet(
    bottomSheetState: SheetState,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        sheetState = bottomSheetState,
        containerColor = Palette.White,
        dragHandle = null,
        tonalElevation = 5.dp,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        onDismissRequest = onDismiss,
        properties =
            ModalBottomSheetProperties(
                shouldDismissOnBackPress = false,
            ),
    ) {
        content()
    }
}
