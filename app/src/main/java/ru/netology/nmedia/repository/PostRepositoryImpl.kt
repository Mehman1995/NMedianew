package ru.netology.nmedia.repository

import ru.netology.nmedia.api.Api
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.entity.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.netology.nmedia.dto.*
import ru.netology.nmedia.entity.toDto
import ru.netology.nmedia.error.ApiError
import ru.netology.nmedia.error.AppError
import ru.netology.nmedia.error.NetworkError
import ru.netology.nmedia.error.UnknownError
import java.io.IOException


class PostRepositoryImpl(private val postDao: PostDao) : PostRepository {

    override val data: Flow<List<Post>> = postDao.getAll()
        .map { it.toDto() }
        .flowOn(Dispatchers.Default)


    override suspend fun getAll() {
        try {
            val response = Api.retrofitService.getAll()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw Exception()
            postDao.insert(body.toEntity().map {
                it.copy(viewed = true)
            })
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun getNewPosts() {
        try {
            postDao.setPostsViewed()
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }
    }

    override suspend fun saveWithAttachment(post: Post, upload: MediaUpload) {
        try {
            val media = upload(upload)
            val postWithAttachment =
                post.copy(
                    attachment = Attachment(
                        url = media.id,
                        description = "",
                        type = AttachmentType.IMAGE
                    )
                )
            save(postWithAttachment)
        } catch (e: AppError) {
            throw e
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }
    }

    override suspend fun upload(upload: MediaUpload): Media {
        try {
            val media = MultipartBody.Part.createFormData(
                "file",
                upload.file.name,
                upload.file.asRequestBody()
            )

            val response = Api.retrofitService.upload(media)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            return response.body() ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }
    }


    override fun getNewerCount(id: Long): Flow<Int> = flow {
        while (true) {
            delay(10_000L)

            val response = Api.retrofitService.getNewer(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw Exception()
            postDao.insert(body.toEntity().map {
                it.copy(viewed = false)
            })
            emit(body.size)
        }
    }
        .catch { e -> throw AppError.from(e) }
        .flowOn(Dispatchers.Default)

    override suspend fun likedById(id: Long) {
        try {
            postDao.likedById(id)
            postDao.setPostsViewed()
            val response = Api.retrofitService.likedById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            postDao.insert(PostEntity.fromDto(body).copy(viewed = true))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun unlikeById(id: Long) {
        try {
            postDao.unlikedById(id)
            val response = Api.retrofitService.unlikeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val data = response.body() ?: throw ApiError(response.code(), response.message())
            postDao.insert(PostEntity.fromDto(data).copy(viewed = true))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun removeById(id: Long) {
        try {
            postDao.removeById(id)
            val response = Api.retrofitService.removeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun save(post: Post) {
        try {
            val response = Api.retrofitService.save(post)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            postDao.insert(PostEntity.fromDto(body).copy(viewed = true))

        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun shareById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun saveDraft(draft: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun getDraft() {
        TODO("Not yet implemented")
    }

}


