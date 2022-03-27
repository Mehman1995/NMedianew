package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.DraftEntity

interface PostRepository {
    val data : LiveData<List<Post>>

    suspend fun likedById(id: Long)
    suspend fun unlikeById(id: Long)
    suspend fun shareById(id: Long)
    suspend fun removeById(id: Long)
    suspend fun save(post: Post)
    suspend fun saveDraft(draft: String?)
    suspend fun getDraft()
    suspend fun getAll()

}

