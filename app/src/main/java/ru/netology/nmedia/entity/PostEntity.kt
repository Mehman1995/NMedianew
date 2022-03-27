package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import ru.netology.nmedia.dto.Attachment
import ru.netology.nmedia.dto.AttachmentType
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: String,
    val likesCount: Int = 0,
    val likedByMe: Boolean = false,
    val share: Boolean = false,
    val sharesCount: Int = 0,
    val video: String? = "",
    val viewed: Boolean = false
) {
    fun toDto() =
        Post(
            id,
            author,
            authorAvatar,
            content,
            published,
            likesCount,
            likedByMe,
            share,
            sharesCount
        )

    companion object {
        fun fromDto(post: Post) =
            PostEntity(
                post.id,
                post.author,
                post.authorAvatar,
                post.content,
                post.published,
                post.likes,
                post.likedByMe,
                post.share,
                post.sharesCount,
                post.video,
                post.viewed
            )
    }
}

@Entity
data class DraftEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val content: String
)

fun List<PostEntity>.toDto() = map(PostEntity::toDto)
fun List<Post>.toEntity() = map(PostEntity::fromDto)

