package com.poplogic.blipin.feature.onboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.poplogic.blipin.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

val ONBOARD_TAB_DATA =
    listOf(
        OnboardUiModel(
            title = "隨時隨地\n找到你想吃的餐車",
            description = "Blipin 帶你發現附近的移動美味",
            buttonText = "下一步",
            lottieRes = R.raw.intro_1,
        ),
        OnboardUiModel(
            title = "收藏最愛\n追蹤最新出沒",
            description = "再也不錯過你愛的餐車",
            buttonText = "下一步",
            lottieRes = R.raw.intro_2,
        ),
        OnboardUiModel(
            title = "輕鬆搜尋\n找到想吃的口味",
            description = "用地點、時間或餐點類型篩選結果",
            buttonText = "開始使用",
            lottieRes = R.raw.intro_3,
        ),
    )

data class OnboardUiState(
    val tabs: List<OnboardUiModel> = ONBOARD_TAB_DATA,
)

@KoinViewModel
class OnboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardUiState())
    val uiState: StateFlow<OnboardUiState> = _uiState.asStateFlow()
}
