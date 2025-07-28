package com.example.financial_control.domain.models.side_menu

import com.example.financial_control.R
data class SideMenuItemModel(val iconPainter: Int, val titleId: Int) {
    companion object {
        val items = listOf(
            SideMenuItemModel(R.drawable.category, R.string.module_categories),
        )
    }
}