package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likesCount: Int = 0,
    val likedByMe: Boolean = false,
    val share: Boolean = false,
    val sharesCount: Int = 0,
    val video: String = ""
) {
    fun toDto() =
        Post(id, author, content, published, likesCount, likedByMe, share, sharesCount, video)

    companion object {
        fun fromDto(post: Post) =
            PostEntity(
                post.id,
                post.author,
                post.content,
                post.published,
                post.likes,
                post.likedByMe,
                post.share,
                post.sharesCount,
                post.video
            )
    }
}

@Entity
data class DraftEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val content: String
)


