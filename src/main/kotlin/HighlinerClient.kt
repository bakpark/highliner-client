package com.example

import com.google.gson.Gson
import kotlinx.coroutines.coroutineScope
import okhttp3.OkHttpClient

class HighlinerClient(
    private val instanceId: Int,
    private val userSize: Int,
    private val pageSize: Int
) {
    val client = OkHttpClient()
    val gson = Gson()

    val users = mutableListOf<User>()


    suspend fun start() {
        coroutineScope {
            for (i in 1..userSize) {
                val user = createUser(client, gson, CreateUserRequest("user$instanceId-$i", "@user$instanceId-$i"))
                if (user != null) {
                    users.add(user)
                }
            }

            repeat(pageSize) { pageRepeat ->
                // (1) 유저 ID 무작위 선택
                val userId = users.random().userId

                // (2) 페이지 생성 (POST /api/users/{userId}/pages)
                val pageRequestBody = CreatePageRequest.random(users, userId, pageRepeat)

                val pageId = createPage(client, gson, userId, pageRequestBody)
                if (pageId == null) {
                    println("[오류] 페이지 생성 실패 (userId=$userId), 다음 반복으로 넘어갑니다.")
                    return@repeat
                }

                // (3) 하이라이트 30건 생성 (POST /api/pages/{pageId}/highlights)
                repeat(30) { highlightRepeat ->
                    val highlightRequestBody = CreateHighlightRequest(
                        text = "$instanceId-$pageRepeat-$highlightRepeat highlighted",
                        color = "#000000"
                    )
                    createHighlight(client, gson, pageId, highlightRequestBody)
                }
            }
        }
    }
}