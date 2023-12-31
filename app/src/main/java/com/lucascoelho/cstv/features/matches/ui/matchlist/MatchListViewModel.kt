package com.lucascoelho.cstv.features.matches.ui.matchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucascoelho.core.models.Resource
import com.lucascoelho.core.models.Status
import com.lucascoelho.domain.models.MatchModel
import com.lucascoelho.domain.usecases.IGetMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val getMatchesUseCase: IGetMatchesUseCase,
) : ViewModel() {

    private val mutableStateFlow = MutableStateFlow(true)
    val isFirstLoadDone = mutableStateFlow.asStateFlow()

    private val _matches = MutableStateFlow<Resource<List<MatchModel>>>(Resource.loading(null))
    val matches: Flow<Resource<List<MatchModel>>> = _matches.asStateFlow()

    private val _paginationStatus = MutableStateFlow<Resource<List<MatchModel>>>(Resource.loading(null))
    val paginationStatus: Flow<Resource<List<MatchModel>>> = _paginationStatus.asStateFlow()

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
            }.collect { result ->
                if (result.status == Status.SUCCESS) {
                    currentPage++
                }
                _matches.value = result
                isPageLoading = false
                mutableStateFlow.value = false
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
            }.collect { result ->
                if (result.status == Status.SUCCESS && result.data?.isNotEmpty() == true) {
                    val updatedList = _matches.value.data.orEmpty() + result.data.orEmpty()
                    _matches.value = Resource.success(updatedList)
                    currentPage++
                }
                _paginationStatus.value = result
                isPageLoading = false
            }
        }
    }
}
