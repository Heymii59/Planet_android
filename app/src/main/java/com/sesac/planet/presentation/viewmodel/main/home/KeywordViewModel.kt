package com.sesac.planet.presentation.viewmodel.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.KeywordResponse
import com.sesac.planet.data.repository.main.home.KeywordRepository
import com.sesac.planet.domain.usecase.main.home.GetKeywordUseCase
import com.sesac.planet.network.main.home.KeywordAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class KeywordViewModel(private val getKeywordUseCase: GetKeywordUseCase) : ViewModel() {
    private val _keywordData = MutableLiveData<Response<KeywordResponse>>()
    val keywordData: LiveData<Response<KeywordResponse>> get() = _keywordData

    init {
        KeywordRepository.keywordService =
            PlanetApplication.getInstance().create(KeywordAPI::class.java)
    }
    
    fun getKeyword(token: String, journeyId: Int) {
        if (_keywordData.value == null) {
            viewModelScope.launch {
                val response = getKeywordUseCase(token, journeyId)
                _keywordData.value = response
            }
        }
    }
}