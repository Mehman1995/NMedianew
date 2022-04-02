package ru.netology.nmedia.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import androidx.lifecycle.*
import okhttp3.MultipartBody
import ru.netology.nmedia.api.PostApi
import ru.netology.nmedia.dto.User
import ru.netology.nmedia.error.ApiError
import ru.netology.nmedia.error.NetworkError
import ru.netology.nmedia.error.UnknownError
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.model.PhotoModel
import ru.netology.nmedia.repository.AuthRepository
import java.io.File
import java.io.IOException

class SignUpViewModel : ViewModel() {
    private val _data = MutableLiveData<User>()
    val data: LiveData<User>
        get() = _data

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    private val repository = AuthRepository()

    fun registrationUser(login: String, password: String, name: String) {
        viewModelScope.launch {
            try {
                val user = repository.registrationUser(login, password, name)
                _data.value = user
            } catch (e: Exception) {
                _dataState.postValue(FeedModelState(errorRegistration = true))
            }
        }

    }



}
