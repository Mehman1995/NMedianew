package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.DraftEntity

interface PostRepository {
    fun getAll(): List<Post>
    fun likedById(id: Long)
    fun unlikeById(id: Long)
    fun shareById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
    fun saveDraft(draft: String?)
    fun getDraft() :String?
}