package app.file_provider.viewModel

import android.os.Environment
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import app.file_provider.adapters.ListFilesAdapter
import app.file_provider.utils.FileClickListener
import java.io.File

@BindingAdapter("adapter")
fun setAdapter(recyclerView: RecyclerView,
               adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
    recyclerView.adapter = adapter
}

class FileProviderViewModel: ViewModel() {

    companion object {
        class FileProviderViewModelFactory: ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(FileProviderViewModel::class.java))
                    return FileProviderViewModel() as T
                throw IllegalArgumentException("Unknown viewModel class")
            }
        }
        private val rootFile: File = Environment.getExternalStorageDirectory()
    }

    private var currentFile: File = rootFile

    private var _currentPath = MutableLiveData("")
    val currentPath: LiveData<String>
        get() = _currentPath

    val isRootFile: Boolean
        get() = currentFile == rootFile

    private val fileClickListener: FileClickListener = FileClickListener { fileItem ->
        if (fileItem.isDirectory) move(File(fileItem.path))
    }

    fun onBackPressed() = move(currentFile.parentFile as File)

    private fun move(newCurrentFile: File) {
        currentFile = newCurrentFile
        _currentPath.value = newCurrentFile.absolutePath.removePrefix(rootFile.absolutePath)
        adapter.replaceAll(currentFile)
    }

    val adapter = ListFilesAdapter(fileClickListener)
}