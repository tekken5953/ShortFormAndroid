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

    data class Feed(
        val userName: String,
        val userProfile: Int?,
        val feedImg: Int?
    )

    data class Story(
        val userProfile: Int,
        val userName: String
    )

    data class People(
        val img: Int?,
        val name: String,
        val content: String?
    )

    data class Post(
        val img: Int?,
        var isSelected: Boolean = false
    )
}