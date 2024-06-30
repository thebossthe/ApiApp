package jp.techacademy.yuu.funakoshi.apiapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import jp.techacademy.yuu.funakoshi.apiapp.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    private var isFavorite = false
    private lateinit var favoriteShop: FavoriteShop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.apply {
            loadUrl(intent.getStringExtra(KEY_URL).toString())
            webViewClient = WebViewClient()
        }

        binding.WebfavoriteImageView.setOnClickListener {
            if (isFavorite) {
                deleteFavorite()
            } else {
                addFavorite()
            }
        }
    }

    private fun updateFavoriteImage() {
        if (isFavorite) {
            binding.WebfavoriteImageView.setImageResource(R.drawable.ic_star_border)
        } else {
            binding.WebfavoriteImageView.setImageResource(R.drawable.ic_star)
        }
    }

    private fun addFavorite() {
        FavoriteShop.insert(FavoriteShop().apply {
            id = favoriteShop.id
            name = favoriteShop.name
            imageUrl = favoriteShop.imageUrl
            url = favoriteShop.url
        })
        isFavorite = true
        updateFavoriteImage()
        Log.d("TEST","Webページでお気に入り追加")
    }

    private fun deleteFavorite() {
        FavoriteShop.delete(favoriteShop.id)
        isFavorite = false
        updateFavoriteImage()
        Log.d("TEST","Webページでお気に入り削除")
    }

    companion object {
        private const val KEY_URL = "key_url"
        private const val KEY_ID = "key_id"

        fun start(activity: Activity, url: String,id: String) {
            activity.startActivity(
                Intent(activity, WebViewActivity::class.java).apply {
                    putExtra(KEY_URL, url)
                    putExtra(KEY_ID, id)
                }
            )
        }
    }
}