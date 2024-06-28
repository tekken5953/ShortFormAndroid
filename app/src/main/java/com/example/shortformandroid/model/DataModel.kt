package com.example.shortformandroid.model

object DataModel {
    data class Video(
        val profile: Int?,
        var isLike: Boolean = false,
        val userName: String?,
        val videoName: String?,
        val videoInfo: String?,
        var likeValue: Int,
        var commentValue: Int,
        val videoPath: Int?
    )
}