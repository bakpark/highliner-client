package com.example

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

// ----------------- 데이터 클래스 (예시) -----------------
data class CreatePageRequest(
    val url: String,
    val title: String,
    val mentions: List<String>? = null,
    val visibility: String
) {

    companion object {
        fun random(users: List<User>, userId: Long, repeat: Int): CreatePageRequest {
            val random = Random.nextInt(100)
            if (random < 5) {
                return CreatePageRequest(
                    url = "https://public.kr",
                    title = "public page $repeat",
                    visibility = "public"
                )
            } else if (random < 80) {
                return CreatePageRequest(
                    url = "https://private.kr",
                    title = "private page $repeat",
                    visibility = "private"
                )
            } else {
                return CreatePageRequest(
                    url = "https://mentioned.kr",
                    title = "mentioned page $repeat",
                    mentions = users.filter { it.userId != userId }
                        .map { it.username }
                        .asSequence().shuffled()
                        .take(Random.nextInt(5)+1).toList(),
                    visibility = "mentioned"
                )
            }
        }
    }
}

data class CreateHighlightRequest(
    val text: String,
    val color: String
)

data class CreateUserRequest(
    val name: String,
    val username: String
)

// 서버에서 반환되는 페이지 정보의 JSON 응답 예시
data class PageResponse(
    @SerializedName("webPageId")
    val pageId: Long
)

data class User(
    val userId: Long,
    val username: String
)