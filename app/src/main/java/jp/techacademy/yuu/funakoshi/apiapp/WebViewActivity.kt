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
    private lateinit var Shop: Shop
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
                deleteFavorite(Shop.id)
            } else {
                addFavorite(Shop)
            }
        }
    }

    private fun updateFavoriteImage(isFavorite:Boolean) {
        if (isFavorite) {
            binding.WebfavoriteImageView.setImageResource(R.drawable.ic_star_border)
        } else {
            binding.WebfavoriteImageView.setImageResource(R.drawable.ic_star)
        }
    }

    private fun addFavorite(shop: Shop) {
        FavoriteShop.insert(FavoriteShop().apply {
            id = shop.id
            name = shop.name
            imageUrl = shop.logoImage
            url = shop.couponUrls.sp.ifEmpty { shop.couponUrls.pc }
        })
        updateFavoriteImage(true)
        Log.d("TEST","Webページでお気に入り追加")
    }

    private fun deleteFavorite(id: String) {
        FavoriteShop.delete(id)
        updateFavoriteImage(false)
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