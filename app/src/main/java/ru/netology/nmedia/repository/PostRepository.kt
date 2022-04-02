package ru.netology.nmedia.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.Media
import ru.netology.nmedia.dto.MediaUpload
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.User
import ru.netology.nmedia.entity.DraftEntity
import java.io.File
import java.net.URI

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
   suspend fun saveWithAttachment(post: Post, upload: MediaUpload)
   suspend fun upload(upload: MediaUpload) : Media

}

