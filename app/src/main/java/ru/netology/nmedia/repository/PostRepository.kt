package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.DraftEntity

interface PostRepository {
    fun getAll(): List<Post>
    fun likedById(id: Long, callback: PostCallback)
    fun unlikeById(id: Long, callback: PostCallback)
    fun shareById(id: Long)
    fun removeById(id: Long, callback: IdCall)
    fun save(post: Post, callback: PostCallback)
    fun saveDraft(draft: String?)
    fun getDraft() :String?
    fun getAllAsync(callback: GetAllCallback)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>) {}
        fun onError(e: Exception) {}
    }

    interface PostCallback {
        fun onSuccess(post: Post) {}
        fun onError(e: Exception) {}
    }

    interface IdCall{
        fun onSuccess(id: Long) {}
        fun onError(e: Exception) {}
    }

}