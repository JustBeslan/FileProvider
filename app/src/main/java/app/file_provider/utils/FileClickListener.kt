package app.file_provider.utils

import app.file_provider.model.FileItem

class FileClickListener(val fileClickListener: (FileItem) -> Unit) {
    fun onClick(fileItem: FileItem) = fileClickListener(fileItem)
}