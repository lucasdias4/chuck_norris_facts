package com.lucasdias.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun ImageView.loadUrl(
    url: String? = null,
    placeHolderId: Int? = null,
    onLoadCompleted: () -> Unit = {},
    onError: () -> Unit = {}
) {
    val requestBuilder = Glide.with(context)
        .`as`(Drawable::class.java)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onError()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadCompleted()
                return false
            }
        })

    placeHolderId?.let {
        requestBuilder
            .placeholder(placeHolderId)
            .error(placeHolderId)
    }

    requestBuilder
        .load(url)
        .into(this)
}
