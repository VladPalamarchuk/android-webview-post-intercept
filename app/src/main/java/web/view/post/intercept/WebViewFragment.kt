package web.view.post.intercept

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import web.view.post.intercept.databinding.WebViewFragmentBinding

class WebViewFragment : Fragment() {

    private lateinit var binding: WebViewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = WebViewFragmentBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSc0bkMESKGBBpk35AT0JvM8trw1luTO9Am-uBysACpl2tGXkg/viewform?usp=sf_link")
        binding.getFormDatBtn.setOnClickListener {
            binding.webView.getFormData {
                AlertDialog.Builder(requireContext())
                    .setItems(
                        it.map { (key, value) -> "$key is: $value" }.toTypedArray(),
                        null
                    ).show()
            }
        }
    }
}