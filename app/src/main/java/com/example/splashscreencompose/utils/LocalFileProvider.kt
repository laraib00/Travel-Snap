package com.example.splashscreencompose.utils

import android.content.Context
import android.provider.MediaStore

object LocalFileProvider {

    /*suspend fun fetchLocalVideos(context: Context): List<LocalVideos> =
        withContext(Dispatchers.IO) {
            val localVideosList = mutableListOf<LocalVideos>()
            val projection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.ALBUM,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.MIME_TYPE
            )

            val cursor = context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
            )

            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val titleColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)
                val dataColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
                val durationColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                val folderColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)
                val dateModifiedColumn =
                    it.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED)
                val mimeTypeColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)

                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val title = it.getString(titleColumn)
                    val data = it.getString(dataColumn)
                    val size: Float = it.getFloat(sizeColumn)
                    val duration = it.getLong(durationColumn)
                    val folder = it.getString(folderColumn)
                    val modifiedDate = it.getString(dateModifiedColumn)
                    val mimeType = it.getString(mimeTypeColumn)
                    val video =
                        LocalVideos(id, title, data, size, duration, folder, modifiedDate, mimeType)
                    localVideosList.add(video)
                }
            }

            return@withContext localVideosList
        }*/

    fun fetchLocalImages(context: Context): List<LocalImages>
        // withContext(Dispatchers.IO)
        {
            val localImagesList = mutableListOf<LocalImages>()
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.MIME_TYPE
            )

            val cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
            )

            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val titleColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE)
                val dataColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                val dateModifiedColumn =
                    it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)
                val mimeTypeColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)

                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val title = it.getString(titleColumn)
                    val data = it.getString(dataColumn)
                    val size: Float = it.getFloat(sizeColumn)
                    val modifiedDate = it.getString(dateModifiedColumn)
                    val mimeType = it.getString(mimeTypeColumn)
                    val image = LocalImages(id, title, data, size, modifiedDate, mimeType)
                    localImagesList.add(image)
                }
            }

            return localImagesList
        }
}