package com.example

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

// ----------------- 함수: 유저 생성 -----------------
fun createUser(
    client: OkHttpClient,
    gson: Gson,
    requestBody: CreateUserRequest
): User? {
    val jsonMediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
    val body = gson.toJson(requestBody).toRequestBody(jsonMediaType)
    val request = Request.Builder()
        .url("http://localhost:8080/api/users")
        .post(body)
        .build()

    client.newCall(request).execute().use { response ->
        return if (response.isSuccessful) {
            val responseBody = response.body?.string()
            if (!responseBody.isNullOrEmpty()) {
                val userResponse = gson.fromJson(responseBody, User::class.java)
                userResponse
            } else {
                println("[오류] 유저 생성 응답 본문이 없습니다.")
                null
            }
        } else {
            println("[오류] 유저 생성 실패 code=${response.code}, msg=${response.message}")
            null
        }
    }
}

// ----------------- 함수: 페이지 생성 -----------------
fun createPage(
    client: OkHttpClient,
    gson: Gson,
    userId: Long,
    requestBody: CreatePageRequest
): Long? {
    val jsonMediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
    val body = gson.toJson(requestBody).toRequestBody(jsonMediaType)
    val request = Request.Builder()
        .url("http://localhost:8080/api/users/$userId/pages")
        .post(body)
        .build()

    client.newCall(request).execute().use { response ->
        return if (response.isSuccessful) {
            val responseBody = response.body?.string()
            if (!responseBody.isNullOrEmpty()) {
                val pageResp = gson.fromJson(responseBody, PageResponse::class.java)
                pageResp.pageId
            } else {
                println("[오류] 페이지 생성 응답 본문이 없습니다.")
                null
            }
        } else {
            println("[오류] 페이지 생성 실패 code=${response.code}, msg=${response.message}")
            null
        }
    }
}

// ----------------- 함수: 하이라이트 생성 -----------------
fun createHighlight(
    client: OkHttpClient,
    gson: Gson,
    pageId: Long,
    requestBody: CreateHighlightRequest
) {
    val jsonMediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
    val body = gson.toJson(requestBody).toRequestBody(jsonMediaType)
    val request = Request.Builder()
        .url("http://localhost:8080/api/pages/$pageId/highlights")
        .post(body)
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            println("[오류] 하이라이트 생성 실패 code=${response.code}, msg=${response.message}")
        }
    }
}