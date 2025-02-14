# Highliner-Clinet

하이라이너 프로젝트의 클라이언트입니다.
샘플 데이터 생성에 사용합니다.

### Highliner의 데이터 생성 API를 호출합니다.
```
POST /api/users
POST /api/users/{userId}/pages
POST /api/pages/{pageId}/highlights
```

### Highliner Client Instance 
- Client Instance 갯수를 지정하여 병렬로 API를 호출합니다.
- Client 는 각각 설정된 User 그룹을 생성하고, 각 User 그룹에서 페이지를 생성하고 멘션합니다.
- Page 생성은 User 그룹에서 랜덤하게 선출된 User가 생성합니다.
- Page 의 공개범위는 Public 5 : Private 80 : Mention 15 의 비율로 생성됩니다.
- Page 의 공개범위가 mention 인 경우, User 그룹에서 랜덤하게 최대 5명을 멘션합니다.
- Highlight 는 각 Page 별로 최대 30개씩 생성됩니다.
