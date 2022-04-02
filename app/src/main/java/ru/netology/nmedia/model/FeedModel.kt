package ru.netology.nmedia.model

import android.telephony.NetworkRegistrationInfo
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.enumeration.RetryType

data class FeedModel(
    val posts: List<Post> = emptyList(),
    val empty: Boolean = false
)

data class FeedModelState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val refreshing: Boolean = false,
    val retryType: RetryType? = null,
    val retryId: Long = 0,
    val retryPost: Post? = null,
    val errorLogin: Boolean = false,
    val errorRegistration: Boolean = false
)