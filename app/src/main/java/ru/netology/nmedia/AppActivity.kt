package ru.netology.nmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) return@let
            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) Snackbar.make(
                binding.root, R.string.error_empty_content,
                LENGTH_INDEFINITE
            ).setAction(android.R.string.ok) {
                finish()
            }.show()
        }
    }
}