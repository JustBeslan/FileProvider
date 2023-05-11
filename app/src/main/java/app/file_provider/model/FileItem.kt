package app.file_provider.model

import app.file_provider.R
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes

data class FileItem(
    val name: String,
    val type: String,
    val path: String,
    private val attributes: BasicFileAttributes,
    val isDirectory: Boolean,
) {

    private val size: String
        get() = when {
            attributes.size() < 1024 -> "${attributes.size()} B"
            attributes.size() < 1024 * 1024 -> "${attributes.size()/1024 } KB"
            attributes.size() < 1024 * 1024 * 1024 -> "${attributes.size()/(1024 * 1024) } MB"
            else -> "${attributes.size()/(1024 * 1024 * 1024) } GB"
        }

    val createDate: Long
        get() = attributes.creationTime().toMillis()

    val iconDrawable: Int
        get() =
            if (isDirectory) R.drawable.folder_icon
            else when(type) {
                "bmp" -> R.drawable.bmp_icon
                "zip" -> R.drawable.compressed_file_icon
                "rar" -> R.drawable.compressed_file_icon
                "7z" -> R.drawable.compressed_file_icon
                "css" -> R.drawable.css_icon
                "csv" -> R.drawable.csv_icon
                "doc" -> R.drawable.doc_icon
                "docx" -> R.drawable.docx_icon
                "exe" -> R.drawable.exe_icon
                "gif" -> R.drawable.gif_icon
                "heic" -> R.drawable.heic_icon
                "html" -> R.drawable.html_icon
                "jpg" -> R.drawable.jpg_icon
                "jpeg" -> R.drawable.jpg_icon
                "js" -> R.drawable.js_icon
                "json" -> R.drawable.json_icon
                "mp3" -> R.drawable.mp3_icon
                "mp4" -> R.drawable.mp4_icon
                "pdf" -> R.drawable.pdf_icon
                "php" -> R.drawable.php_icon
                "png" ->R.drawable.png_icon
                "ppt" -> R.drawable.ppt_icon
                "pptx" -> R.drawable.pptx_icon
                "py" -> R.drawable.py_icon
                "sql" -> R.drawable.sql_icon
                "svg" -> R.drawable.svg_icon
                "txt" -> R.drawable.txt_icon
                "wav" -> R.drawable.wav_icon
                "xls" -> R.drawable.xls_icon
                "xlsx" -> R.drawable.xlsx_icon
                "xml" -> R.drawable.xml_icon
                else -> R.drawable.file_icon
            }

    val hash: String? = null

    private var countSubFiles: String? = null

    val additionalInfo: String
        get() {
            var info = size
            if (isDirectory)
                info += " ($countSubFiles)"
            return size
        }

    constructor(file: File): this(
        name = file.name,
        type = file.extension,
        path = file.absolutePath,
        attributes = Files.readAttributes(file.toPath(), BasicFileAttributes::class.java),
        isDirectory = file.isDirectory
    ) {
        if (isDirectory)
            countSubFiles = "El-s: ${file.listFiles()?.size}"
    }
}
