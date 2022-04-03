package web.view.post.intercept.webview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

@SuppressLint("SetJavaScriptEnabled")
class FormDataWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : WebView(context, attrs) {

    private val javaScriptInterface = FormDataWebViewFormDataJSInterface()

    init {
        webChromeClient = android.webkit.WebChromeClient()
        settings.javaScriptEnabled = true
        addJavascriptInterface(
            javaScriptInterface,
            FORM_READER_JS_INTERFACE
        )
    }

    fun getFormData(callback: (Map<String, String?>) -> Unit) {
        evaluateJavascript(JAVASCRIPT_READ_FORM_METHOD.trimMargin()) {
            val data: Map<String, String> = javaScriptInterface.getFormData()
            val keyValue = data.keys
                .filter { key -> key.contains("entry") }
                .map { key -> key to data[key] }
                .filter { (_, value) -> !value.isNullOrBlank() }
            callback.invoke(keyValue.toMap())
        }
    }

    companion object {
        const val FORM_READER_JS_INTERFACE = "FORM_READER_JS_INTERFACE"
        const val JAVASCRIPT_READ_FORM_METHOD: String = """
            function parseForm(form){
              var values='';
              for (var i=0; i<form.elements.length; i++) {
                values+=form.elements[i].name+'='+form.elements[i].value+'&'
              } 
              window.$FORM_READER_JS_INTERFACE.processFormData(values);
            }
                
            (function() { 
              for (var i=0 ; i< document.forms.length ; i++){
                parseForm(document.forms[i]);
              };
            })()
             """
    }
}