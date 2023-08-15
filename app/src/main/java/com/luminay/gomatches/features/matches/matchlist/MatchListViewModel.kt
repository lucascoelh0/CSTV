package com.luminay.gomatches.features.matches.matchlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.models.Resource
import com.example.core.models.Status
import com.example.domain.models.MatchModel
import com.example.domain.usecases.IGetMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val getMatchesUseCase: IGetMatchesUseCase,
) : ViewModel() {

    private val _matches = MutableStateFlow<Resource<List<MatchModel>>>(Resource.loading(null))
    val matches: Flow<Resource<List<MatchModel>>> = _matches.asStateFlow()

    private val _pagingStatus = MutableStateFlow<Resource<List<MatchModel>>>(Resource.loading(null))
    val pagingStatus: Flow<Resource<List<MatchModel>>> = _pagingStatus.asStateFlow()

    private var currentPage = 1
    private var isPageLoading = false

    init {
        fetchData()
    }

    fun fetchData() {
        if (isPageLoading) return
        isPageLoading = true
        currentPage = 1

        viewModelScope.launch {
            flow {
                emit(Resource.loading(null))
                emitAll(getMatchesUseCase(1))
            }.catch {
                Log.e("MatchListViewModel", "fetchData: ", it)
            }.collect { result ->
                if (result.status == Status.SUCCESS) {
                    currentPage++
                }
                _matches.value = result
                isPageLoading = false
            }
        }
    }

    fun fetchNextPage() {
        if (isPageLoading) return
        isPageLoading = true

        viewModelScope.launch {
            flow {
                emit(Resource.loading(null))
                emitAll(getMatchesUseCase(currentPage))
            }.catch {
                Log.e("MatchListViewModel", "fetchNextPage: ", it)
            }.collect { result ->
                if (result.status == Status.SUCCESS && result.data?.isNotEmpty() == true) {
                    val updatedList = _matches.value.data.orEmpty() + result.data.orEmpty()
                    _matches.value = Resource.success(updatedList)
                    currentPage++
                }
                _pagingStatus.value = result
                isPageLoading = false
            }
        }
    }
}
