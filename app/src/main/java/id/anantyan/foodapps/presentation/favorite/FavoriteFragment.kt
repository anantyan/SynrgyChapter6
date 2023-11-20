package id.anantyan.foodapps.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.foodapps.common.calculateSpanCount
import id.anantyan.foodapps.databinding.FragmentFavoriteBinding
import id.anantyan.foodapps.domain.model.FoodModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()
    @Inject lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback())
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.toolbar.title = "Bookmark"
        binding.toolbar.isTitleCentered = true

        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.layoutManager = StaggeredGridLayoutManager(requireActivity().windowManager.calculateSpanCount(), RecyclerView.VERTICAL)
        binding.rvFavorite.itemAnimator = DefaultItemAnimator()
        binding.rvFavorite.isNestedScrollingEnabled = true
        binding.rvFavorite.adapter = favoriteAdapter

        favoriteAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        favoriteAdapter.onClick { _, item ->
            val destination = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(item.id ?: -1)
            findNavController().navigate(destination)
        }
    }

    private fun bindObserver() {
        viewModel.results().onEach { state: List<FoodModel> ->
            if (state.isNotEmpty()) {
                favoriteAdapter.submitList(state)
                binding.imgViewFavorite.isVisible = false
            } else {
                favoriteAdapter.submitList(emptyList())
                binding.imgViewFavorite.isVisible = true
            }
        }.flowWithLifecycle(viewLifecycleOwner.lifecycle).launchIn(viewLifecycleOwner.lifecycleScope)
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