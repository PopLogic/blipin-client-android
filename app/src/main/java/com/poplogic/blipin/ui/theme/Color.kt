package com.poplogic.blipin.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Orange600 = Color(0xFFFF6600)

val BlipinBrandPrimary = Orange600
val BlipinPrimary100 = Color(0xFFFFF0D3)
val BlipinWhite = Color(0xFFFFFFFF)
val splashBackground = Color(0xFFF2EFED)
val BlipinNeutral50 = Color(0xFFF6F6F6)
val BlipinNeutral600 = Color(0xFF5D5D5D)
val BlipinNeutral800 = Color(0xFF454545)
val BlipinNeutral100 = Color(0xFFE7E7E7)
val BlipinBlack = Color(0xFF000000)

fun Color.hardcoded(): Color = this

class Palette {
    object FoodType {
        val hamburger = Color(0xFFE39B00)
        val softDrink = Color(0xFF0098C7)
        val dessert = Color(0xFFF448A7)
        val noodle = Color(0xFF0139B1)
        val pizza = Color(0xFFFF1216)
        val brunch = Color(0xFFFFA600)
        val fries = Color(0xFFCA8E35)
        val seafood = Color(0xFFFF5126)
        val healthy = Color(0xFF1BB607)
        val japanese = Color(0xFFFF8C12)
        val korean = Color(0xFFC14A4A)
        val streetFood = Color(0xFFB75D37)
        val vegan = Color(0xFF457617)
        val bbq = Color(0xFF532A06)
    }

    object Neutral {
        val neutral50 = BlipinNeutral50
        val neutral100 = BlipinNeutral100
        val neutral200 = Color(0xFFCCCCCC)
        val neutral300 = Color(0xFFB3B3B3)
        val neutral400 = Color(0xFF999999)
        val neutral500 = Color(0xFF808080)
        val neutral600 = BlipinNeutral600
        val neutral700 = Color(0xFF666666)
        val neutral800 = BlipinNeutral800
        val neutral900 = Color(0xFF333333)
        val neutral950 = Color(0xFF1A1A1A)
        val neutralBrand = neutral950
    }

    object Primary {
        val primary50 = BlipinBrandPrimary
        val primary100 = BlipinPrimary100
        val primary200 = Color(0xFFFFE0B2)
        val primary300 = Color(0xFFFFCC80)
        val primary400 = Color(0xFFFFB74D)
        val primary500 = Color(0xFFFFA726)
        val primary600 = Color(0xFFFF9800)
        val primary700 = Color(0xFFFB8C00)
        val primary800 = Color(0xFFF57C00)
        val primary900 = Color(0xFFEF6C00)
        val primary950 = Color(0xFFE65100)
        val brand = BlipinBrandPrimary
    }

    companion object {
        val Success = Color(0xFF4CAF50)
        val Alert = Color(0xFFF44336)
        val Black = BlipinBlack
        val White = BlipinWhite
        val SplashBackground = Color(0xFFF2EFED)
    }
}
