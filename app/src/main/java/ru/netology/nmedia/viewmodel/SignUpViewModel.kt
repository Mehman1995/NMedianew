package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import androidx.lifecycle.*
import ru.netology.nmedia.dto.User
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.repository.AuthRepository

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