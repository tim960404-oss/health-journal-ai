# 智慧健康日誌與風險評估系統
# Smart Health Journal & Risk Assessment System

使用者每天記錄睡眠時數、步數、心情分數，系統透過決策樹自動分類健康風險等級（低／中／高）。

## 資料流向

```
前端 fetch() → HealthLogController → HealthLogService → DecisionTreeService → SQLite → 回傳前端
                                                      → GET /health-logs/risk → 即時風險等級
```

## 技術棧

| 層級 | 技術 |
|------|------|
| 後端框架 | Spring Boot 3.2.4 (Java 17) |
| 資料存取 | Spring Data JPA + Hibernate |
| 資料庫 | SQLite (sqlite-jdbc 3.45.1.0) |
| 前端 | 原生 HTML5 / CSS3 / JavaScript + Chart.js |
| 部署 | Docker + Render |

## 本地啟動

```bash
export GEMINI_API_KEY=your_key_here
cd health-journal-ai
mvn spring-boot:run
```

開啟瀏覽器：http://localhost:8080

## Render 雲端部署

1. 將專案推送至 GitHub
2. Render → New Web Service → 選擇 repo
3. 環境變數設定：`GEMINI_API_KEY=your_key`
4. Build Command: 留空（使用 Dockerfile）
5. Render 自動偵測 Dockerfile 並部署

## 決策樹邏輯

```
sleep_hours < 6
  ├── steps < 3000 → HIGH
  └── steps >= 3000
        ├── mood_score <= 4 → HIGH
        └── mood_score > 4  → MEDIUM
sleep_hours >= 6
  ├── steps < 5000 → MEDIUM
  └── steps >= 5000
        ├── mood_score >= 6 → LOW
        └── mood_score < 6  → MEDIUM
```
