package app.file_provider.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.file_provider.databinding.FileItemBinding
import app.file_provider.model.FileItem
import app.file_provider.utils.FileClickListener
import app.file_provider.utils.getFileItemsInFolder
import java.io.File
import java.text.SimpleDateFormat

@BindingAdapter("fileIcon")
fun setIconDrawable(imageView: ImageView, drawableId: Int) =
    imageView.setImageDrawable(
        ResourcesCompat.getDrawable(imageView.resources, drawableId, null)
    )

@BindingAdapter("createDate")
fun setCreateDate(textView: TextView, timeMills: Long) {
    textView.text = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss",
        textView.resources.configuration.locales[0]
    ).format(timeMills)
}

class ListFilesAdapter(private val fileClickListener: FileClickListener)
    : RecyclerView.Adapter<ListFilesAdapter.ViewHolder>() {

    private val listFileItems = mutableListOf<FileItem>()

    inner class ViewHolder(private val binding: FileItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind (fileItem: FileItem) {
                binding.file = fileItem
                binding.clickListener = fileClickListener
                binding.executePendingBindings()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = FileItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(listFileItems[position])

    override fun getItemCount() = listFileItems.size

    fun addAll(vararg files: FileItem) {
        files.forEach {
            listFileItems.add(it)
            notifyItemInserted(listFileItems.lastIndex)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceAll(file: File) =
        getFileItemsInFolder(file)?.let {
            listFileItems.clear()
            listFileItems.addAll(it)
            notifyDataSetChanged()
        }
}