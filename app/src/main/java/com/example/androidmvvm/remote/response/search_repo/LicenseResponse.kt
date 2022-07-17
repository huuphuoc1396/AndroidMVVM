package com.example.androidmvvm.remote.response.search_repo


import com.google.gson.annotations.SerializedName

data class LicenseResponse(
    @SerializedName("key")
    val key: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("node_id")
    val nodeId: String? = null,
    @SerializedName("spdx_id")
    val spdxId: String? = null,
    @SerializedName("url")
    val url: String? = null
)