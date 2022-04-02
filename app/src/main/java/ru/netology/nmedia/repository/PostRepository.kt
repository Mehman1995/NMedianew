package ru.netology.nmedia.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.DraftEntity

interface PostRepository {
    val data: Flow<List<Post>>

    suspend fun likedById(id: Long)
    suspend fun unlikeById(id: Long)
    suspend fun shareById(id: Long)
    suspend fun removeById(id: Long)
    suspend fun save(post: Post)
    suspend fun saveDraft(draft: String?)
    suspend fun getDraft()
    suspend fun getAll()

    fun getNewerCount(id: Long): Flow<Int>
   suspend fun getNewPosts()
}

