package com.poplogic.blipin.presentation.profile.presentation.screens

import com.poplogic.blipin.common.base.environment.Environment
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.route.Routes

enum class ProfileInfoList(
    val route: String?,
    val label: String,
    val description: String,
    val needSignIn: Boolean,
    val allowedEnvironment: Set<Environment>,
) {
    ConnectedAccounts(
        Routes.CONNECTED_ACCOUNT,
        "已連接帳戶".hardcoded(),
        "Manage your connected accounts",
        true,
        setOf(Environment.DEVELOPMENT, Environment.STAGING, Environment.PRODUCTION),
    ),
    NotificationSetting(
        Routes.NOTIFICATION_SETTING,
        "通知".hardcoded(),
        "Manage your notification settings",
        true,
        setOf(Environment.DEVELOPMENT, Environment.STAGING, Environment.PRODUCTION),
    ),
    Support(
        Routes.SUPPORT,
        "更多資訊與支援".hardcoded(),
        "Get help and support",
        false,
        setOf(Environment.DEVELOPMENT, Environment.STAGING, Environment.PRODUCTION),
    ),
    AppVersion(
        null,
        "App版本".hardcoded(),
        "View the current app version",
        false,
        setOf(Environment.DEVELOPMENT, Environment.STAGING, Environment.PRODUCTION),
    ),
    BlipinDeveloper(
        Routes.BLIPIN_DEVELOPER,
        "Blipin Developer".hardcoded(),
        "Learn more about Blipin development",
        false,
        setOf(Environment.DEVELOPMENT, Environment.STAGING),
    ),
}
