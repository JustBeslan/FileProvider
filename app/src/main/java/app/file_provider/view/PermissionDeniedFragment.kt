package app.file_provider.view

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import app.file_provider.R
import app.file_provider.databinding.FragmentPermissionDeniedBinding

class PermissionDeniedFragment : Fragment() {

    companion object {
        const val TAG = "PermissionDeniedFragment"
    }

    private lateinit var binding: FragmentPermissionDeniedBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted)
                parentFragmentManager.commit {
                    replace<FileProviderFragment>(R.id.mainFrameLayout, FileProviderFragment.TAG)
                }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionDeniedBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tryAgainButton.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}