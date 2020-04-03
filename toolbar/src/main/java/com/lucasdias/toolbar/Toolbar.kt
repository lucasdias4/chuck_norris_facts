package com.lucasdias.toolbar

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.lucasdias.extensions.gone
import com.lucasdias.extensions.visible

/**
 * Este módulo tem como objetivo ser uma biblioteca. Logo, seus métodos existem independente
 * de serem utilizados pelo client, já que a lib não tem noção do contexto externo.
 **/

class Toolbar {

    companion object {

        private const val NO_PADDING = 0

        fun initializeToolbar(
            activity: Activity,
            toolbarContainer: FrameLayout,
            supportActionBar: ActionBar?
        ) {
            supportActionBar?.hide()
            val inflater = activity.layoutInflater
            val view = inflater.inflate(R.layout.toolbar_layout, null)
            toolbarContainer.addView(view, 0)
        }

        fun setToolbarColor(activity: Activity, toolbarColor: Int) {
            val toolbarLayout =
                activity.findViewById<AppBarLayout>(R.id.app_bar_layout_toolbar_layout)
            val color = ContextCompat.getColor(activity, toolbarColor)
            toolbarLayout.setBackgroundColor(color)
        }

        fun setToolbarIcon(activity: Activity, icon: Drawable) {
            val toolbarIcon =
                activity.findViewById<ImageView>(R.id.icon_toolbar_content_layout)
            toolbarIcon.setPadding(NO_PADDING, NO_PADDING, NO_PADDING, NO_PADDING)
            toolbarIcon.setImageDrawable(icon)
            toolbarIcon.visible()
        }

        fun removeToolbarIcon(activity: Activity) {
            val toolbarIcon =
                activity.findViewById<ImageView>(R.id.icon_toolbar_content_layout)
            val toolbarTitle =
                activity.findViewById<TextView>(R.id.title_toolbar_content_layout)
            val toolbarTitlePaddingLeft =
                activity
                    .resources
                    .getDimension(R.dimen.title_without_icon_padding_start_toolbar_content_layout)
                    .toInt()
            toolbarIcon.setPadding(NO_PADDING, NO_PADDING, NO_PADDING, NO_PADDING)
            toolbarTitle.setPadding(toolbarTitlePaddingLeft, NO_PADDING, NO_PADDING, NO_PADDING)
            toolbarIcon.gone()
        }

        fun setToolbarTitle(activity: Activity, title: String) {
            val toolbarTitle =
                activity.findViewById<TextView>(R.id.title_toolbar_content_layout)
            toolbarTitle.text = title
            toolbarTitle.visible()
        }

        fun removeToolbarTitle(activity: Activity) {
            val toolbarTitle =
                activity.findViewById<TextView>(R.id.title_toolbar_content_layout)
            toolbarTitle.gone()
        }

        fun setToolbarBackButton(activity: Activity) {
            val toolbarIcon =
                activity.findViewById<ImageView>(R.id.icon_toolbar_content_layout)
            val toolbarBackButton =
                activity.findViewById<ImageView>(R.id.back_button_toolbar_content_layout)
            val toolbarIconPaddingLeft =
                activity
                    .resources
                    .getDimension(
                        R.dimen.icon_padding_start_with_back_button_toolbar_content_layout
                    )
                    .toInt()

            toolbarIcon.setPadding(toolbarIconPaddingLeft, NO_PADDING, NO_PADDING, NO_PADDING)
            backButtonClickSetup(activity = activity, toolbarBackButton = toolbarBackButton)
            toolbarBackButton.visible()
        }

        fun setToolbarTitleStyle(activity: Activity, toolbarTitleStyle: Int) {
            val toolbarTitle =
                activity.findViewById<TextView>(R.id.title_toolbar_content_layout)
            setTextAppearance(
                toolbarTitle = toolbarTitle,
                toolbarTitleStyle = toolbarTitleStyle,
                activity = activity
            )
        }

        fun backButtonClickSetup(activity: Activity, toolbarBackButton: ImageView) {
            toolbarBackButton.setOnClickListener {
                activity.finish()
            }
        }

        fun removeToolbarBackButton(activity: Activity) {
            val toolbarBackButton =
                activity.findViewById<ImageView>(R.id.back_button_toolbar_content_layout)
            toolbarBackButton.setPadding(NO_PADDING, NO_PADDING, NO_PADDING, NO_PADDING)
            toolbarBackButton.gone()
        }

        private fun setTextAppearance(
            toolbarTitle: TextView,
            toolbarTitleStyle: Int,
            activity: Activity
        ) {
            if (Build.VERSION.SDK_INT < 23) {
                toolbarTitle.setTextAppearance(activity, toolbarTitleStyle)
            } else {
                toolbarTitle.setTextAppearance(toolbarTitleStyle)
            }
        }
    }
}
