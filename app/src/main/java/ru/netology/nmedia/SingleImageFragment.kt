package ru.netology.nmedia


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_single_image.*
import ru.netology.nmedia.databinding.FragmentSingleImageBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.Utils
import ru.netology.nmedia.viewmodel.PostViewModel


class SingleImageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Меняем цвет AppBar на черный
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            val statusBarColor = ContextCompat.getColor(requireActivity(), R.color.black)
            activity.window.statusBarColor = statusBarColor
            activity.supportActionBar?.setBackgroundDrawable(ColorDrawable(statusBarColor))
        }

        val binding = FragmentSingleImageBinding.inflate(inflater, container, false)


        val url = "http://10.0.2.2:9999/media/${arguments?.getString("url")}"

        Glide.with(this)
            .load(url)
            .into(binding.imageView)

        return binding.root
    }

    // возвращаем цвет AppBar при выходе
    override fun onDestroyView() {
        super.onDestroyView()
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            val statusBarColor = ContextCompat.getColor(requireActivity(), R.color.black)
            val actionBarColor = ContextCompat.getColor(requireActivity(), R.color.black)
            activity.window.statusBarColor = statusBarColor
            activity.supportActionBar?.setBackgroundDrawable(ColorDrawable(actionBarColor))
        }
    }
}