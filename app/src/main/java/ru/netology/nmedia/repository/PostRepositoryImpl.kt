package ru.netology.nmedia.repository

import retrofit2.Call
import retrofit2.Response
import ru.netology.nmedia.api.PostApi
import ru.netology.nmedia.dto.Post
import java.lang.RuntimeException

class PostRepositoryImpl : PostRepository {

    override fun getAll(callback: PostRepository.GetAllCallback) {
        PostApi.retrofitService.getAll().enqueue(object : retrofit2.Callback<List<Post>> {
            var numberConnectionAttempts = 5
            override fun onResponse(
                call: retrofit2.Call<List<Post>>,
                response: retrofit2.Response<List<Post>>
            ) {
                if (!response.isSuccessful) {
                    if (numberConnectionAttempts > 0) {
                        PostApi.retrofitService.getAll().enqueue(this)
                        --numberConnectionAttempts

                    } else {
                        callback.onError(RuntimeException(response.message()))
                        return
                    }
                } else {
                    callback.onSuccess(response.body().orEmpty())
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Post>>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }

        })

    }

    override fun likedById(id: Long, callback: PostRepository.PostCallback) {
        PostApi.retrofitService.likedById(id).enqueue(object : retrofit2.Callback<Post> {
            var numberConnectionAttempts = 5
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    if (numberConnectionAttempts > 0) {
                        PostApi.retrofitService.likedById(id).enqueue(this)
                        --numberConnectionAttempts
                    } else {
                        callback.onError(RuntimeException(response.message()))
                        return
                    }
                } else callback.onSuccess(response.body() ?: error("body is null"))
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(t as Exception)
            }

        })
    }

    override fun unlikeById(id: Long, callback: PostRepository.PostCallback) {
        PostApi.retrofitService.unlikeById(id).enqueue(object : retrofit2.Callback<Post> {
            var numberConnectionAttempts = 5
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    if (numberConnectionAttempts > 0) {
                        PostApi.retrofitService.unlikeById(id).enqueue(this)
                        --numberConnectionAttempts
                    } else {
                        callback.onError(RuntimeException(response.message()))
                    }
                } else {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }

        })
    }

    override fun removeById(id: Long, callback: PostRepository.IdCall) {
        PostApi.retrofitService.removeById(id).enqueue(object : retrofit2.Callback<Unit> {
            var numberConnectionAttempts = 5
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (!response.isSuccessful) {
                    if (numberConnectionAttempts > 0) {
                        PostApi.retrofitService.removeById(id).enqueue(this)
                        --numberConnectionAttempts
                    } else {
                        callback.onError(RuntimeException(response.message()))
                        return
                    }
                } else callback.onSuccess(Unit)
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }


        })
    }

    override fun save(post: Post, callback: PostRepository.PostCallback) {
        PostApi.retrofitService.save(post).enqueue(object : retrofit2.Callback<Post> {
            var numberConnectionAttempts = 5
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    if (numberConnectionAttempts > 0) {
                        PostApi.retrofitService.save(post).enqueue(this)
                        --numberConnectionAttempts
                    } else {
                        callback.onError(RuntimeException(response.message()))
                        return
                    }
                } else {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }

        })
    }

    override fun saveDraft(draft: String?) {
        TODO("Not yet implemented")
    }

    override fun getDraft(): String? {
        TODO("Not yet implemented")
    }

    override fun shareById(id: Long) {
        TODO("Not yet implemented")
    }
}