package com.example.androidtesting.api

import com.example.androidtesting.model.ApiResponseCls
import com.example.androidtesting.model.ApiUploadData
import com.example.androidtesting.unit.FilesUtils
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface MyPostApi {
    @Headers(FilesUtils.contextType)
    @POST(FilesUtils.POST_REQ)
    suspend fun uploadData(@Body data: ApiUploadData): Response<ApiResponseCls>
}