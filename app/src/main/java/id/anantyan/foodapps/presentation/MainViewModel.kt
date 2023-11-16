package id.anantyan.foodapps.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferencesUseCase: PreferencesUseCase
) : ViewModel() {
    fun getLogin(): Boolean {
        return runBlocking { preferencesUseCase.executeGetLogin().first() }
    }

    fun getTheme(): Flow<Boolean> {
        return preferencesUseCase.executeGetTheme()
    }
}