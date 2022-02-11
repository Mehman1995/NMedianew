package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.Utils

interface PostCallback {
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun remove(post: Post)
    fun edit(post: Post)
    fun onVideo(post: Post)
    fun onPost(post: Post)
}

class PostsAdapter(private val postCallback: PostCallback) :
    ListAdapter<Post, PostViewHolder>(PostsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, postCallback)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val postCallback: PostCallback
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {

        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            like.text = Utils.reductionInNumbers(post.likes)
            share.text = Utils.reductionInNumbers(post.sharesCount)
            like.isChecked = post.likedByMe

            if (!post.video.isNullOrBlank()) group.visibility = View.VISIBLE //можно ли сделать так,
            // чтобы не отображалось видео вью или только с изменениями на серваке?

            like.setOnClickListener {
                postCallback.onLike(post)
            }

            share.setOnClickListener {
                postCallback.onShare(post)
            }

            play.setOnClickListener {
                postCallback.onVideo(post)
            }

            viewForVideo.setOnClickListener {
                postCallback.onVideo(post)
            }

            content.setOnClickListener {
                postCallback.onPost(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_options)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.post_remove -> {
                                postCallback.remove(post)
                                true
                            }
                            R.id.post_edit -> {
                                postCallback.edit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }



        }
    }
}

class PostsDiffCallback : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}