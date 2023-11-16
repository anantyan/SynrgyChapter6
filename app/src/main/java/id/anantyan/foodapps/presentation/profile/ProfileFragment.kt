package id.anantyan.foodapps.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.foodapps.NavGraphMainDirections
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    @Inject lateinit var adapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback())
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.showProfile()
        viewModel.showProfile.onEach { state ->
            when (state) {
                is UIState.Loading -> {}
                is UIState.Success -> {
                    adapter.submitList(state.data)
                    binding.rvProfile.adapter = adapter
                }
                is UIState.Error -> {
                    Toast.makeText(requireContext(), getString(state.message!!), Toast.LENGTH_LONG).show()
                }
            }
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
    }

    private fun bindView() {
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.rvProfile.setHasFixedSize(true)
        binding.rvProfile.itemAnimator = DefaultItemAnimator()
        binding.rvProfile.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProfile.isNestedScrollingEnabled = true

        binding.btnChange.setOnClickListener {
            val destination = ProfileFragmentDirections.actionProfileFragmentToChangeProfileFragment()
            findNavController().navigate(destination)
        }
        binding.btnSettings.setOnClickListener {
            val destination = ProfileFragmentDirections.actionProfileFragmentToSettingFragment()
            findNavController().navigate(destination)
        }
        binding.btnLogout.setOnClickListener {
            viewModel.logOut()
            val destination = NavGraphMainDirections.actionRootToLoginFragment()
            findNavController().navigate(destination)
        }
    }

    private fun onBackPressedCallback() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}