package id.anantyan.foodapps.presentation

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.foodapps.NavGraphMainDirections
import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.updateResources
import id.anantyan.foodapps.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    private fun bindView() {
        val host = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = host.navController
        navController.addOnDestinationChangedListener(this)

        binding.bottomNav.setupWithNavController(navController)

        if (viewModel.getLogin()) {
            val destination = NavGraphMainDirections.actionRootToHomeFragment()
            navController.navigate(destination)
        } else {
            val destination = NavGraphMainDirections.actionRootToLoginFragment()
            navController.navigate(destination)
        }

        viewModel.getTheme().onEach {
            val legacy = if (it) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            val latest = if (it) UiModeManager.MODE_NIGHT_YES else UiModeManager.MODE_NIGHT_NO

            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    val uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
                    uiModeManager.setApplicationNightMode(latest)
                }
                Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> {
                    AppCompatDelegate.setDefaultNightMode(legacy)
                }
            }
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.homeFragment -> binding.bottomNav.isVisible = true
            R.id.profileFragment -> binding.bottomNav.isVisible = true
            R.id.favoriteFragment -> binding.bottomNav.isVisible = true
            R.id.changeProfileFragment -> binding.bottomNav.isVisible = true
            R.id.settingFragment -> binding.bottomNav.isVisible = true
            else -> binding.bottomNav.isVisible = false
        }
    }
}