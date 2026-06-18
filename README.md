# 智慧健康日誌與風險評估系統
# Smart Health Journal & Risk Assessment System

使用者每天記錄睡眠時數、步數、心情分數，系統透過決策樹自動分類健康風險等級（低／中／高），並提供 C4.5 資訊增益分析頁面。

---

## 資料流向

```
前端 fetch()
  → HealthLogController
  → HealthLogService
  → DecisionTreeService（三層決策樹計算 risk_level）
  → SQLite (data.db)
  → 回傳前端即時渲染

GET /health-logs/risk → 即時回傳最新風險等級
```

---

## 技術棧

| 層級 | 技術 |
|------|------|
| 後端框架 | Spring Boot 3.2.4 (Java 17) |
| 資料存取 | Spring Data JPA + Hibernate Community Dialects |
| 資料庫 | SQLite (sqlite-jdbc 3.45.1.0，檔案 data.db) |
| 前端 | 原生 HTML5 / CSS3 / JavaScript + Chart.js 4.4 |
| ML 模組 | 決策樹（Java 實作）+ 資訊增益分析（前端 JS 實作） |
| 部署 | Docker + Render |

---

## 功能頁面

- **儀表板** — 即時風險等級徽章（綠／黃／紅）與最新健康指標
- **紀錄管理** — 新增／編輯／刪除每日健康日誌，風險等級自動計算
- **趨勢圖表** — 睡眠、步數、心情的折線圖（近 30 筆）
- **決策樹** — 視覺化三層決策樹邏輯說明
- **🧮 資訊增益** — C4.5 演算法原理，從實際資料計算各特徵 Information Gain，找出最佳切分特徵與門檻值

---

## 決策樹邏輯

```
ROOT: sleep_hours
├── < 6 小時
│   ├── steps < 3000          → HIGH
│   └── steps >= 3000
│       ├── mood_score <= 4   → HIGH
│       └── mood_score > 4    → MEDIUM
└── >= 6 小時
    ├── steps < 5000          → MEDIUM
    └── steps >= 5000
        ├── mood_score >= 6   → LOW
        └── mood_score < 6    → MEDIUM
```

---

## 資訊增益（C4.5）

系統針對資料庫中的種子資料，對 `sleep_hours`、`steps`、`mood_score` 三個特徵：

1. 計算整體 Entropy H(S)
2. 枚舉所有候選門檻值
3. 計算每個門檻的加權 Entropy 與 Information Gain
4. 找出 IG 最大的特徵與最佳門檻，作為 C4.5 建議的根節點

---

## 本地啟動

```bash
cd health-journal-ai
export GEMINI_API_KEY=test
mvn spring-boot:run
```

開啟瀏覽器：http://localhost:8080

---

## Render 雲端部署

1. 將專案推送至 GitHub
2. Render → New Web Service → 連結 repo
3. 環境變數：`GEMINI_API_KEY=test`
4. Render 自動偵測 Dockerfile 部署
5. 每次 `git push` 自動觸發重新部署
