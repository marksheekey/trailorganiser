package co.uk.happyapper.trailorganiser


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import co.uk.happyapper.trailorganiser.global.BaseFragment
import co.uk.happyapper.trailorganiser.komoot.KomootLoginViewModel
import co.uk.happyapper.trailorganiser.komoot.redirect_url
import kotlinx.android.synthetic.main.webview_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class KomootLoginFragment : BaseFragment() {

    private val thisViewModel: KomootLoginViewModel by viewModel()

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

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            Log.i("webpage",url+"")
            url?.let {
                if(url.contains(redirect_url) && !url.contains("facebook.com")){
                    val uri = Uri.parse(url)
                    uri.getQueryParameter("code")?.let{
                        thisViewModel.gotUserToken(it)
                    }
                    uri.getQueryParameter("error")?.let{
                        uri.getQueryParameter("error_description")?.let{
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