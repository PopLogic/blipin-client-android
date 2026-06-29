package com.poplogic.blipin.feature.profile.presentation.screens

import com.poplogic.blipin.utils.hardcoded

enum class ProfileInfoList(
    val route: String?,
    val label: String,
    val description: String,
) {
    ConnectedAccounts(
        "connected_accounts",
        "已連接帳戶".hardcoded(),
        "Manage your connected accounts",
    ),
    Reviews(
        "reviews",
        "我的評論".hardcoded(),
        "View and manage your reviews",
    ),
    FrequentPlaces(
        "frequent_places",
        "常用地點".hardcoded(),
        "View and manage your frequent places",
    ),
    Notifications(
        "notifications",
        "通知".hardcoded(),
        "Manage your notification settings",
    ),
    Support(
        "support",
        "更多資訊與支援".hardcoded(),
        "Get help and support",
    ),
    AppVersion(
        null,
        "App版本".hardcoded(),
        "View the current app version",
    ),
}
