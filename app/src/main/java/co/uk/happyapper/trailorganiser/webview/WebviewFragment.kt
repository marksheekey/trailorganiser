package co.uk.happyapper.trailorganiser


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import co.uk.happyapper.trailorganiser.global.BaseFragment
import kotlinx.android.synthetic.main.webview_fragment.*

class WebviewFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.webview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.getSettings().setJavaScriptEnabled(true)
        arguments?.getString("url")?.let {

                webView.loadUrl(it)
                webView.webViewClient = mWebViewClient
            }
    }

    private val mWebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
            return super.shouldOverrideKeyEvent(view, event)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            Log.i("webpage",url+"")
            url?.let {
                if(url.contains("YOUR_REDIRECT_URL") && !url.contains("facebook.com")){
                    val uri = Uri.parse(url)
                    uri.getQueryParameter("code")?.let{
                        viewModel.setKomootUserToken(it)
                    }
                    uri.getQueryParameter("error")?.let{
                        uri.getQueryParameter("error_description")?.let{
                            viewModel.setKomootUserToken(null)
                            viewModel.error(it)
                        }

                    }
                    return true
                }
                view?.loadUrl(url)
            }
            return true
        }
    }
}