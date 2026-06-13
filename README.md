# Takeout Ordering

一个前后端分离的外卖点单示例项目。

- 前端：Vue 3 + Vite
- 后端：Java 21 + Spring Boot
- 容器化：Dockerfile + Docker Compose

## 本地开发

前端：

```bash
cd frontend
npm install
npm run dev
```

后端：

```bash
cd backend
mvn spring-boot:run
```

## Docker 运行

```bash
docker compose up --build
```

打开 http://localhost:3000

## API

- `GET /api/menu` 获取菜单
- `POST /api/orders` 创建订单

