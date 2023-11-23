package id.anantyan.foodapps.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.common.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import id.anantyan.foodapps.domain.repository.UserUseCase
import id.anantyan.foodapps.domain.repository.UsersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferencesUseCase: PreferencesUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {
    private var _showProfile: MutableStateFlow<UIState<List<ProfileItemModel>>> = MutableStateFlow(UIState.Loading())
    private var _showPhoto: MutableLiveData<String> = MutableLiveData()

    val showProfile: StateFlow<UIState<List<ProfileItemModel>>> = _showProfile
    val showPhoto: LiveData<String> = _showPhoto

    @OptIn(ExperimentalCoroutinesApi::class)
    fun showPhoto() {
        viewModelScope.launch {
            preferencesUseCase.executeGetUserId().flatMapLatest {
                userUseCase.executeProfile(it)
            }.collect { state ->
                _showPhoto.postValue(state.data?.image ?: "")
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun showProfile() {
        viewModelScope.launch {
            preferencesUseCase.executeGetUserId().flatMapLatest {
                userUseCase.executeProfile(it)
            }.collect { state ->
                _showProfile.value = when (state) {
                    is UIState.Loading -> { UIState.Loading() }
                    is UIState.Success -> {
                        UIState.Success(
                            listOf(
                                ProfileItemModel(R.drawable.ic_key_id, R.string.txt_id, state.data?.id.toString()),
                                ProfileItemModel(R.drawable.ic_shield_person, R.string.txt_username, state.data?.username),
                                ProfileItemModel(R.drawable.ic_email, R.string.txt_email, state.data?.email)
                            )
                        )
                    }
                    is UIState.Error -> { UIState.Error(null, state.message!!) }
                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            preferencesUseCase.executeSetUsrId(-1)
            preferencesUseCase.executeSetLogin(false)
        }
    }
}