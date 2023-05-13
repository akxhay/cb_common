package com.cb.cbtools.util

import android.os.Environment
import android.os.StatFs
import android.util.Log
import com.cb.cbtools.constants.Constants.divider
import java.io.*
import java.text.DecimalFormat
import java.util.regex.Pattern


object FileUtil {
    private val TAG = this::class.java.name

    fun getPathFromString(path: String): String {
        return Environment.getExternalStoragePublicDirectory(path).toString()
    }


    fun fileIsIncomplete(fileStr: String): Boolean {
        val file = File(fileStr)
        if (file.extension == "i" ||
            file.extension == "in" ||
            file.extension == "inc" ||
            file.extension == "inco" ||
            file.extension == "incom" ||
            file.extension == "incomp" ||
            file.extension == "incompl" ||
            file.extension == "incomple" ||
            file.extension == "incomplet" ||
            file.extension == "incomplete" ||
            fileStr.startsWith(".nomedia")
        ) {
            return true
        }
        return false
    }

    fun moveFile(
        inPath: String, outPath: String, fileName: String, onFailure: (Exception) -> Unit
    ) {
        try {
            val fileCheck = File(inPath)
            if (!fileCheck.exists()) {
                Log.d(TAG, "removeFile: file does not exist : $inPath/$fileName")
                return
            }
            val dir = File(outPath)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val outputFile = File("$outPath/$fileName")
            if (outputFile.exists()) {
                Log.d(TAG, "moveFiles: outputFile exist : $fileName")
                return
            }
            Log.d(TAG, "moveFiles: outputFile does not exist : $fileName")
            val inputStream: InputStream = FileInputStream(fileCheck)
            val outputStream: OutputStream = FileOutputStream(outputFile)
            val buffer = ByteArray(1024)
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }
            inputStream.close()
            outputStream.flush()
            outputStream.close()
        } catch (e: FileNotFoundException) {
            onFailure(e)

        } catch (e: Exception) {
            onFailure(e)

        }
    }


    fun renameFile(
        currentFile: String,
        newFile: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit

    ) {
        try {
            val cFile = File(currentFile)
            val nFile = File(newFile)
            if (cFile.exists()) {
                cFile.renameTo(nFile)
                onSuccess()
            }
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    fun deleteFile(
        filePaths: List<String>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit

    ) {
        try {
            for (filePath in filePaths) {
                val file = File(filePath)
                if (file.isDirectory) {
                    file.deleteRecursively()
                } else {
                    file.delete()
                }
            }
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    fun createFolder(
        path: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit

    ) {
        try {
            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    fun createFile(
        filePath: String,
        text: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val outputStream: OutputStream?
        try {
            val file = File(filePath)
            if (!file.exists()) {
                outputStream = FileOutputStream(file)
                outputStream.write(text.toByteArray())
                outputStream.flush()
                outputStream.close()
            }
            onSuccess()
        } catch (e: FileNotFoundException) {
            onFailure(e)
        } catch (e: java.lang.Exception) {
            onFailure(e)
        }
    }

    fun createNewFile(
        fileName: String,
        path: String,
        callback: (result: Boolean, message: String) -> Unit
    ) {
        val fileAlreadyExists = File(path).listFiles()!!.map { it.name }.contains(fileName)
        if (fileAlreadyExists) {
            callback(false, "'${fileName}' already exists.")
        } else {
            val file = File(path, fileName)
            try {
                val result = file.createNewFile()

                callback(
                    result,
                    if (result) {
                        "File '${fileName}' created successfully."
                    } else {
                        "Unable to create file '${fileName}'."
                    }
                )

            } catch (e: Exception) {
                callback(false, "Unable to create file. Please try again.")
                e.printStackTrace()
            }
        }
    }

    fun createNewFolder(
        folderName: String,
        path: String,
        callback: (result: Boolean, message: String) -> Unit
    ) {
        val folderAlreadyExists = File(path).listFiles()!!.map { it.name }.contains(folderName)
        if (folderAlreadyExists) {
            callback(false, "'${folderName}' already exists.")
        } else {
            val file = File(path, folderName)
            try {
                val result = file.mkdir()
                callback(
                    result,
                    if (result) {
                        "Folder '${folderName}' created successfully."
                    } else {
                        "Unable to create folder '${folderName}'."
                    }
                )
            } catch (e: Exception) {
                callback(false, "Unable to create folder. Please try again.")
                e.printStackTrace()
            }
        }
    }

    fun isValidFilename(text: String): Boolean {
        val filenameRegex =
            Pattern.compile("[\\\\\\/:\\*\\?\"<>\\|\\x01-\\x1F\\x7F]", Pattern.CASE_INSENSITIVE)
        return !filenameRegex.matcher(text).find() && "." != text && ".." != text
    }

    fun copyFile(
        inputPath: String,
        outputDir: String,
        fileName: String,
        onSuccess: () -> Unit,

        onFailure: (Exception) -> Unit
    ) {
        try {
            val dir = File(outputDir)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val outputPath = outputDir + divider + fileName
            if (inputPath != outputPath) {
                val sourceLocation = File(inputPath)
                val targetLocation = File(outputPath)
                if (sourceLocation.exists()) {
                    copyRecursive(sourceLocation, targetLocation)
                }
            }
            onSuccess()
        } catch (e: FileNotFoundException) {
            onFailure(e)

        } catch (e: java.lang.Exception) {
            onFailure(e)

        }
    }

    private fun copyRecursive(sourceLocation: File, targetLocation: File) {
        if (sourceLocation.isDirectory) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir()
            }
            val children = sourceLocation.list()
            for (i in sourceLocation.listFiles().indices) {
                copyRecursive(
                    File(sourceLocation, children[i]),
                    File(targetLocation, children[i])
                )
            }
        } else {
            val inputStream = FileInputStream(sourceLocation)
            val outputStream = FileOutputStream(targetLocation)
            val buffer = ByteArray(1024)
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }
            inputStream.close()
            // write the output file
            outputStream.flush()
            outputStream.close()
        }
    }




    fun getFreeMemory(path: File?): Long {
        if (null != path && path.exists() && path.isDirectory) {
            val stats = StatFs(path.absolutePath)
            return stats.availableBlocksLong * stats.blockSizeLong
        }
        return -1
    }

    fun getTotalMemory(path: File?): Long {
        if (null != path && path.exists() && path.isDirectory) {
            val stats = StatFs(path.absolutePath)
            return stats.blockCountLong * stats.blockSizeLong
        }
        return -1
    }




    fun isExternalStorageReadOnly(): Boolean {
        val extStorageState: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
    }

    fun isExternalStorageAvailable(): Boolean {
        val extStorageState: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == extStorageState
    }

    fun getReadableSize(fileSize: Long): String {
        val readableSize: String
        if (fileSize > 1024) {
            val fileSizeInKB = fileSize / 1024
            val remainIngB = fileSize % 1024
            readableSize = if (fileSizeInKB > 1024) {
                val fileSizeInMB = fileSizeInKB / 1024
                val remainIngKB = fileSizeInKB % 1024
                if (fileSizeInMB > 1024) {
                    val fileSizeInGB = fileSizeInMB / 1024
                    val remainIngMB = fileSizeInMB % 1024
                    "$fileSizeInGB.$remainIngMB GB"
                } else {
                    "$fileSizeInMB.$remainIngKB MB"
                }
            } else {
                "$fileSizeInKB.$remainIngB KB"
            }
        } else {
            readableSize = "$fileSize Bytes"
        }
        return readableSize
    }

    fun bytesToHuman(totalBytes: Long): String? {
        val symbols = arrayOf("B", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB")
        var scale = 1L
        for (symbol in symbols) {
            if (totalBytes < scale * 1024L) {
                return java.lang.String.format(
                    "%s %s",
                    DecimalFormat("#.##").format(totalBytes.toDouble() / scale),
                    symbol
                )
            }
            scale *= 1024L
        }
        return "-1 B"
    }

}