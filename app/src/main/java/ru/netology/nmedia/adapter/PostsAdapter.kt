package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.AttachmentType
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.Utils

interface PostCallback {
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun remove(post: Post)
    fun edit(post: Post)
    fun onVideo(post: Post)
    fun onPost(post: Post)
    fun onImage(post: Post)
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

            menu.visibility = if (post.ownedByMe) View.VISIBLE else View.INVISIBLE


            Glide.with(avatar)
                .load("http://10.0.2.2:9999/avatars/${post.authorAvatar}")
                .circleCrop()
                .placeholder(R.drawable.ic_avatar_placeholder)
                .timeout(10_000)
                .error(R.drawable.ic_error)
                .into(avatar)


            when (post.attachment?.type) {
                AttachmentType.IMAGE -> {
                    Glide.with(viewForImage)
                        .load("http://10.0.2.2:9999/media/${post.attachment?.url}")
                        .timeout(10_000)
                        .into(viewForImage)
                }
            }
            viewForImage.isVisible = post.attachment?.type == AttachmentType.IMAGE



                if (!post.video.isNullOrBlank()) {
                    group.visibility = View.VISIBLE
                }

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

            viewForImage.setOnClickListener {
                postCallback.onImage(post)
            }

                menu.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.post_options)
                        menu.setGroupVisible(R.id.owned, post.ownedByMe)
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