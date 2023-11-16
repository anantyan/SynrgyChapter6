package id.anantyan.foodapps.presentation.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.foodapps.databinding.FragmentSettingBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SettingFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.btnTheme.setOnCheckedChangeListener { _, b ->
            viewModel.setTheme(b)
        }

        binding.btnTranslate.setOnCheckedChangeListener { _, b ->
            viewModel.setTranslate(b)
        }
    }

    private fun bindObserver() {
        viewModel.getTheme.onEach { bool ->
            binding.btnTheme.isChecked = bool
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)

        viewModel.getTranslate.onEach { bool ->
            binding.btnTranslate.text = if (bool) "Indonesia" else "English"
            binding.btnTranslate.isChecked = bool
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}