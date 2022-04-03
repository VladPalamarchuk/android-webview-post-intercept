package web.view.post.intercept.webview

import android.webkit.JavascriptInterface

@Suppress("unused")
class FormDataWebViewFormDataJSInterface {

    private val formDataMap = mutableMapOf<String, String>()

    fun getFormData(): Map<String, String> = formDataMap

    fun resetFormData() = formDataMap.clear()

    @JavascriptInterface
    fun processFormData(data: String) {
        val values = data.split("&".toRegex()).toTypedArray()
        for (pair in values) {
            val formKey = pair.substring(
                startIndex = 0,
                endIndex = pair.indexOfFirst { it == '=' }
            )
            val formValue = pair.substring(
                startIndex = pair.indexOfFirst { it == '=' } + 1,
                endIndex = pair.length
            )
            formDataMap[formKey] = formValue
        }
    }
}
