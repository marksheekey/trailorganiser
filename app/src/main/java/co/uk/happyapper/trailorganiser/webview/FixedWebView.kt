package co.uk.happyapper.trailorganiser.webview

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebView

class FixedWebView : WebView {
    constructor(context: Context) : super(getFixedContext(context)) {}
    constructor(context: Context, attrs: AttributeSet?) : super(getFixedContext(context), attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(getFixedContext(context), attrs, defStyleAttr) {}

    companion object {
        // To fix Android Lollipop WebView problem create a new configuration on that Android version only
        private fun getFixedContext(context: Context): Context {
            return if (Build.VERSION.SDK_INT == 21 || Build.VERSION.SDK_INT == 22) context.createConfigurationContext(Configuration()) else context
        }
    }
}