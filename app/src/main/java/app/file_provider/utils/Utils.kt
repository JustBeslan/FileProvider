package app.file_provider.utils

import android.view.View
import app.file_provider.model.FileItem
import com.google.android.material.snackbar.Snackbar
import java.io.File

fun showSnackBar(
    view: View,
    message: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackBar = Snackbar.make(view, message, length)
    if (actionMessage != null)
        snackBar.setAction(actionMessage) { action(it) }
    snackBar.show()
}

fun getFileItemsInFolder(file: File): List<FileItem>? =
    file.listFiles()
        ?.filterNot { it.name.startsWith('.') }
        ?.sortedByDescending { it.isDirectory }
        ?.map { FileItem(it) }
