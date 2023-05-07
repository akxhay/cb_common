package com.cb.cbtools.util

import android.os.Environment
import android.util.Log
import com.cb.cbtools.constants.Constants.divider
import java.io.*
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
                if (file.exists()) {
                    deleteRecursive(file)
                }
            }
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    private fun deleteRecursive(fileOrDirectory: File) {
        if (fileOrDirectory.isDirectory) {
            for (child in fileOrDirectory.listFiles()!!) {
                deleteRecursive(child)
            }
        }
        fileOrDirectory.delete()
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
            readableSize = fileSize.toString() + "Bytes"
        }
        return readableSize
    }
}