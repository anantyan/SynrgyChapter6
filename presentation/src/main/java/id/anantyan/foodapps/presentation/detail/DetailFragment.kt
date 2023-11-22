package id.anantyan.foodapps.presentation.detail

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import com.google.android.material.divider.MaterialDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.foodapps.common.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.common.calculateSpanCountSmall
import id.anantyan.foodapps.presentation.databinding.FragmentDetailBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    @Inject lateinit var ingredientsAdapter: DetailIngredientsAdapter
    @Inject lateinit var instructionsAdapter: DetailInstructionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback())
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.toolbar.setOnMenuItemClickListener(this)
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_keyboard_backspace)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        divider.dividerInsetStart = 32
        divider.dividerInsetEnd = 32
        divider.isLastItemDecorated = false

        binding.rvIngredients.setHasFixedSize(true)
        binding.rvIngredients.addItemDecoration(divider)
        binding.rvIngredients.layoutManager = GridLayoutManager(requireContext(), requireActivity().windowManager.calculateSpanCountSmall())
        binding.rvIngredients.itemAnimator = DefaultItemAnimator()
        binding.rvIngredients.isNestedScrollingEnabled = false
        binding.rvIngredients.adapter = ingredientsAdapter

        binding.rvInstructions.setHasFixedSize(true)
        binding.rvInstructions.addItemDecoration(divider)
        binding.rvInstructions.layoutManager = GridLayoutManager(requireContext(), requireActivity().windowManager.calculateSpanCountSmall())
        binding.rvInstructions.itemAnimator = DefaultItemAnimator()
        binding.rvInstructions.isNestedScrollingEnabled = false
        binding.rvInstructions.adapter = instructionsAdapter

        ingredientsAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        instructionsAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun bindObserver() {
        viewModel.checkFood(args.idRecipe)

        viewModel.stateBookmarked.onEach { state: Boolean ->
            if (state) {
                binding.toolbar.menu.findItem(R.id.unbookmark).isVisible = false
                binding.toolbar.menu.findItem(R.id.bookmark).isVisible = true
            } else {
                binding.toolbar.menu.findItem(R.id.unbookmark).isVisible = true
                binding.toolbar.menu.findItem(R.id.bookmark).isVisible = false
            }
        }.flowWithLifecycle(viewLifecycleOwner.lifecycle).launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.result(args.idRecipe).onEach { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.contentDetail.isVisible = false
                    binding.imgViewFavorite.isVisible = false
                }
                is UIState.Success -> {
                    binding.progressBar.isVisible = false
                    binding.contentDetail.isVisible = true
                    binding.imgViewFavorite.isVisible = false
                    binding.toolbar.title = state.data?.title
                    binding.txtTitle.text = state.data?.title
                    binding.imgDetail.load(state.data?.image) {
                        crossfade(true)
                        placeholder(R.drawable.img_loading)
                        error(R.drawable.img_not_found)
                        size(ViewSizeResolver(binding.imgDetail))
                    }
                    @Suppress("SetTextI18n")
                    binding.txtSourceName.text = "by " + state.data?.sourceName
                    @Suppress("SetTextI18n")
                    binding.txtServings.text = state.data?.servings.toString() + " Servings"
                    @Suppress("SetTextI18n")
                    binding.txtPrepareOnMinutes.text = state.data?.readyInMinutes.toString() + " Ready in Minutes"
                    binding.txtSummary.text = Html.fromHtml(state.data?.summary, Html.FROM_HTML_MODE_COMPACT)
                    ingredientsAdapter.submitList(state.data?.extendedIngredients ?: emptyList())
                    if (!state.data?.analyzedInstructions.isNullOrEmpty()) {
                        instructionsAdapter.submitList(state.data?.analyzedInstructions?.get(0)?.steps ?: emptyList())
                    } else {
                        instructionsAdapter.submitList(emptyList())
                    }
                }
                is UIState.Error -> {
                    binding.progressBar.isVisible = false
                    binding.contentDetail.isVisible = false
                    binding.imgViewFavorite.isVisible = true
                }
            }
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
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

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.unbookmark -> {
                viewModel.bookmark(args.idRecipe)
                true
            }
            R.id.bookmark -> {
                viewModel.unbookmark(args.idRecipe)
                true
            }
            else -> false
        }
    }
}