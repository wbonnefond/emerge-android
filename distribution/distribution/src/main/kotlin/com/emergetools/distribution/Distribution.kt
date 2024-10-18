package com.emergetools.distribution

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.emergetools.distribution.internal.DistributionInternal
import kotlinx.serialization.Serializable
import okhttp3.OkHttpClient

/**
 * The public Android SDK for Emerge Tools Build Distribution.
 */
object Distribution {
  /**
   * Initialize build distribution. This should be called once in each process.
   * This method may be called from any thread with a Looper. It is safe to
   * call this from the main thread. Options may be passed if you want to override the default values.
   * @param context Android context
   * @param options Override distribution settings
   */
  fun init(context: Context, options: DistributionOptions? = null) {
    DistributionInternal.init(context, options ?: DistributionOptions())
  }

  /**
   *
   */
  fun isEnabled(context: Context): Boolean {
    return DistributionInternal.isEnabled(context)
  }

  /**
   * Check to see if an updated version of the current app exists.
   */
  suspend fun checkForUpdate(context: Context): UpdateStatus {
    return DistributionInternal.checkForUpdate(context)
  }

  /**
   * Download the provided update.
   */
  fun downloadUpdate(context: Context, info: UpdateInfo) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(info.downloadUrl))
    context.startActivity(browserIntent)
  }
}

/**
 * Optional settings for build distribution.
 */
data class DistributionOptions(
  /**
   * Pass an existing OkHttpClient. If null Distribution will create it's own OkHttpClient.
   * This allows reuse of existing OkHttpClient thread pools etc.
   */
  val okHttpClient: OkHttpClient? = null,

  /**
   * Emerge Tools build tag used to find the desired next build.
   */
  val tag: String? = null,
)

@Serializable
data class UpdateInfo(
  val id: String,
  val tag: String,
  val version: String,
  val appId: String,
  val downloadUrl: String,
)

sealed class UpdateStatus {
  class Error(val message: String) : UpdateStatus()
  class NewRelease(val info: UpdateInfo) : UpdateStatus()
  object UpToDate : UpdateStatus()
}
