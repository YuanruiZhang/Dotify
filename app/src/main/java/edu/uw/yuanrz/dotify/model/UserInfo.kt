package edu.uw.yuanrz.dotify.model

data class UserInfo(
    val username: String,
    val firstName: String,
    val lastName: String,
    val hasNose: Boolean,
    val platform: Float,
    val profilePicURL:String,
)
