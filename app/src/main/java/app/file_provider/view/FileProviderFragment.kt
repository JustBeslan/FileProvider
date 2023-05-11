package app.file_provider.view

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.file_provider.databinding.FragmentFileProviderBinding
import app.file_provider.utils.getFileItemsInFolder
import app.file_provider.viewModel.FileProviderViewModel

class FileProviderFragment : Fragment() {

    companion object {
        const val TAG = "FileProviderFragment"
    }

    private lateinit var binding: FragmentFileProviderBinding

    private val viewModel: FileProviderViewModel by viewModels {
        FileProviderViewModel.Companion.FileProviderViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFileProviderBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel

        binding.listFilesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }

        getFileItemsInFolder(Environment.getExternalStorageDirectory())
            ?.let { viewModel.adapter.addAll(*it.toTypedArray()) }

        requireActivity().onBackPressedDispatcher.addCallback(binding.lifecycleOwner) {
            if (viewModel.isRootFile) requireActivity().finish()
            else viewModel.onBackPressed()
        }

        viewModel.currentPath.observe(viewLifecycleOwner) {
            binding.listFilesRecyclerView.scrollToPosition(0)
        }
    }
}