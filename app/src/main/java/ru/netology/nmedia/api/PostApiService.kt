package ru.netology.nmedia.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository

private const val BASE_URL = "http://10.0.2.2:9999/api/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface PostApiService {
    @GET("posts")
    fun getAll() : Call<List<Post>>

    @POST("posts/{id}/likes")
    fun likedById(@Path("id") id: Long) : Call<Post>

    @DELETE("posts/{id}/likes")
    fun unlikeById(@Path("id") id: Long) : Call<Post>

    @DELETE("posts/{id}")
    fun removeById(@Path("id") id: Long) : Call<Unit>

    @POST("posts")
    fun save(@Body post: Post): Call<Post>
}

object PostApi {
    val retrofitService by lazy {
        retrofit.create(PostApiService::class.java)
    }
}