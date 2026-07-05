package com.poplogic.blipin.feature.explore.domain

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.poplogic.blipin.R
import com.poplogic.blipin.ui.theme.Palette

enum class FoodType(
    @field:StringRes val displayStringRes: Int,
    @field:DrawableRes val iconRes: Int,
    @field:DrawableRes val activatedIconRes: Int,
    val color: Color,
) {
    Hamburger(
        R.string.food_type_hamburger,
        R.drawable.burger,
        R.drawable.burger_activated,
        Palette.FoodType.hamburger,
    ),
    Healthy(
        R.string.food_type_healthy,
        R.drawable.healthy,
        R.drawable.healthy_activated,
        Palette.FoodType.healthy,
    ),
    Brunch(
        R.string.food_type_brunch,
        R.drawable.brunch,
        R.drawable.brunch_activated,
        Palette.FoodType.brunch,
    ),
    SoftDrink(
        R.string.food_type_soft_drink,
        R.drawable.soft_drink,
        R.drawable.soft_drink_activated,
        Palette.FoodType.softDrink,
    ),
    Dessert(
        R.string.food_type_dessert,
        R.drawable.dessert,
        R.drawable.dessert_activated,
        Palette.FoodType.dessert,
    ),
    Noodle(
        R.string.food_type_noodle,
        R.drawable.noodle,
        R.drawable.noodle_activated,
        Palette.FoodType.noodle,
    ),
    Pizza(
        R.string.food_type_pizza,
        R.drawable.pizza,
        R.drawable.pizza_activated,
        Palette.FoodType.pizza,
    ),
    Fries(
        R.string.food_type_fries,
        R.drawable.fries,
        R.drawable.fries_activated,
        Palette.FoodType.fries,
    ),
    Seafood(
        R.string.food_type_seafood,
        R.drawable.seafood,
        R.drawable.seafood_activated,
        Palette.FoodType.seafood,
    ),
    Japanese(
        R.string.food_type_japanese,
        R.drawable.japanese,
        R.drawable.japanese_activated,
        Palette.FoodType.japanese,
    ),
    Korean(
        R.string.food_type_korean,
        R.drawable.korean,
        R.drawable.korean_activated,
        Palette.FoodType.korean,
    ),
    StreetFood(
        R.string.food_type_street_food,
        R.drawable.street_food,
        R.drawable.street_food_activated,
        Palette.FoodType.streetFood,
    ),
    Vegan(
        R.string.food_type_vegan,
        R.drawable.vegan,
        R.drawable.vegan_activated,
        Palette.FoodType.vegan,
    ),
    BBQ(
        R.string.food_type_bbq,
        R.drawable.bbq,
        R.drawable.bbq_activated,
        Palette.FoodType.bbq,
    ),
}
