package id.anantyan.foodapps.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.domain.model.UserModel
import id.anantyan.foodapps.domain.repository.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private var _register: MutableStateFlow<UIState<Int>> = MutableStateFlow(UIState.Loading())

    val register: StateFlow<UIState<Int>> = _register

    fun register(user: UserModel) {
        viewModelScope.launch {
            userUseCase.executeRegister(user).collect {
                _register.value = it
            }
        }
    }
}