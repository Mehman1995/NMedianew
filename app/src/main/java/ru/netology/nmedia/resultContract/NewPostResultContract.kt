package ru.netology.nmedia.resultContract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.activity.NewPostFragment


class NewPostResultContract : ActivityResultContract<String, String?>() {

    override fun createIntent(context: Context, input: String): Intent =
        Intent(context, NewPostFragment::class.java).apply {
            putExtra(Intent.EXTRA_TEXT, input)
        }

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) intent?.getStringExtra(Intent.EXTRA_TEXT)
        else null

}