package id.anantyan.foodapps.presentation.profile

import android.net.Uri
import android.os.Build
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
import coil.load
import coil.size.ViewSizeResolver
import com.app.imagepickerlibrary.ImagePicker
import com.app.imagepickerlibrary.ImagePicker.Companion.registerImagePicker
import com.app.imagepickerlibrary.listener.ImagePickerResultListener
import com.app.imagepickerlibrary.model.PickExtension
import com.app.imagepickerlibrary.model.PickerType
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.foodapps.common.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.common.byteArray
import id.anantyan.foodapps.common.createListDialog
import id.anantyan.foodapps.common.deleteAllPath
import id.anantyan.foodapps.common.path
import id.anantyan.foodapps.presentation.NavGraphMainDirections
import id.anantyan.foodapps.presentation.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment(), ImagePickerResultListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private val imagePicker: ImagePicker by lazy { registerImagePicker(this) }
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

        viewModel.showPhoto.observe(viewLifecycleOwner) { photoUri ->
            if (photoUri.isNotEmpty()) {
                binding.imgProfile.load(photoUri) {
                    crossfade(true)
                    placeholder(R.drawable.img_loading_1x1)
                    error(R.drawable.img_not_found_1x1)
                    size(ViewSizeResolver(binding.imgProfile))
                }
            }
        }

        viewModel.changePhoto.observe(viewLifecycleOwner) { state ->
            if (state) {
                Toast.makeText(requireContext(), "Berhasil di simpan!", Toast.LENGTH_LONG).show()
                requireContext().deleteAllPath()
            } else {
                Toast.makeText(requireContext(), "Gagal di simpan!", Toast.LENGTH_LONG).show()
                requireContext().deleteAllPath()
            }
        }

        viewModel.showProfile()
        viewModel.showPhoto()
    }

    private fun bindView() {
        binding.toolbar.title = getString(R.string.txt_profile)
        binding.toolbar.isTitleCentered = true

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.rvProfile.setHasFixedSize(true)
        binding.rvProfile.itemAnimator = DefaultItemAnimator()
        binding.rvProfile.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProfile.isNestedScrollingEnabled = true

        binding.btnChange.setOnClickListener {
            val list = listOf(getString(R.string.txt_photo_profile), getString(R.string.txt_biodata))
            MaterialAlertDialogBuilder(requireContext()).createListDialog(
                title = getString(R.string.txt_choose_to_change),
                items = list
            ) { item ->
                when (item) {
                    list[0] -> {
                        dialogChooseLoadPhoto()
                    }
                    list[1] -> {
                        val destination = ProfileFragmentDirections.actionProfileFragmentToChangeProfileFragment()
                        findNavController().navigate(destination)
                    }
                }
            }
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

    private fun dialogChooseLoadPhoto() {
        val list = listOf(getString(R.string.txt_camera), getString(R.string.txt_choose_photo))
        MaterialAlertDialogBuilder(requireContext()).createListDialog(
            title = getString(R.string.txt_choose_load_photo),
            items = list
        ) { item ->
            when (item) {
                list[0] -> {
                    chooseLoadPhoto(true)
                }
                list[1] -> {
                    chooseLoadPhoto(false)
                }
            }
        }
    }

    private fun chooseLoadPhoto(value: Boolean) {
        imagePicker
            .title(getString(R.string.txt_choose_load_photo))
            .multipleSelection(false, 15)
            .showCountInToolBar(true)
            .showFolder(true)
            .cameraIcon(false)
            .doneIcon(true)
            .allowCropping(true)
            .compressImage(true)
            .maxImageSize(2)
            .extension(PickExtension.ALL)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            imagePicker.systemPicker(true)
        }
        if (value) {
            imagePicker.open(PickerType.CAMERA)
        } else {
            imagePicker.open(PickerType.GALLERY)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onImagePick(uri: Uri?) {
        uri?.let {
            val path = uri.path(requireContext())
            viewModel.changePhoto(path ?: "")
            binding.imgProfile.load(path) {
                crossfade(true)
                placeholder(R.drawable.img_loading_1x1)
                error(R.drawable.img_not_found_1x1)
                size(ViewSizeResolver(binding.imgProfile))
            }
        }
    }

    override fun onMultiImagePick(uris: List<Uri>?) { }
}