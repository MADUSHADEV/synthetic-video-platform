🎬 AI Video Library — YouTube‑style replica for AI‑generated videos ONLY 🤖🎥✨

Welcome to AI Video Library — a YouTube‑inspired platform where every single video is created by AI. No human‑recorded clips here — only AI videos, always and forever! 🔒🤖📹

🚀 Overview
Think YouTube, but exclusively for AI‑generated content. Discover, upload, and discuss videos produced by models and tools across the AI ecosystem.

🎯 Core Principles
- Only AI videos. Always. No exceptions. ✅
- YouTube‑style experience for discovery, playback, and engagement. ▶️
- Built for creators, researchers, and AI enthusiasts. 🧠💡

🛠️ Tech Stack
- Language: Kotlin ☕
- Framework: Ktor 🚀
- Database: PostgreSQL 🐘
- Cache/Queue: Redis ⚡
- Resilience: Resilience4j 🛡️
- Build Tool: Gradle 🏗️
- Infra: Docker & Docker Compose 🐳

📦 Project Structure
- core — shared configs, auth, resilience, and utilities 🧩
- identity-service — Ktor service for authentication and user management 🔐
- db-mg-service — database migration service (Liquibase) 🧱
- buildSrc — convention plugins and dependency catalog 📚
- docker-compose.yml — local infra (PostgreSQL, Redis) 🧰
- data/redis — local Redis data (ignored in VCS) 💾

🎥 Features
- Upload and manage AI‑generated videos only 🤖📤
- Secure authentication and identity service 👤🔐
- Search and discover AI content 🔍
- Engage with the community (likes, comments — module‑dependent) 💬⭐
- Robust, secure storage and configuration 🔒

🏁 Getting Started
Prerequisites
- Java 17+ ☕
- Docker Desktop 🐳
- Gradle Wrapper (included) 🏗️

1) Configure environment
- Edit the .env file at the project root to match your local setup (database, Redis, service ports). 🧪

2) Start local infrastructure
- docker-compose up -d  🐳

3) Build the project
- Linux/macOS: ./gradlew build
- Windows: .\gradlew.bat build

4) Run services (example)
- Linux/macOS: ./gradlew :identity-service:run
- Windows: .\gradlew.bat :identity-service:run

✅ Useful Gradle commands
- Build all modules: ./gradlew build
- Run checks/tests: ./gradlew check
- Clean build outputs: ./gradlew clean

💡 Notes
- We use the Gradle Wrapper for consistent builds across environments.
- Database migrations are handled by db-mg-service (Liquibase). Ensure your DB is up via Docker before running services. 🧱

🎨 Why AI Videos Only?
Because the future of content creation is synthetic, generative, and mind‑blowing — and we celebrate that! 🚀🤖
- Champion AI‑generated art and creativity 🎭
- Explore cutting‑edge AI video technology 🔬
- Foster a thriving community of AI enthusiasts 🌟

📚 Resources
- Gradle Docs — gradle.org/docs 📖
- Ktor Docs — ktor.io 🌐

🤝 Contributing
We welcome contributions!
- 🐛 Report bugs
- 💡 Suggest features
- 🔧 Submit pull requests

📄 License
This project is part of the AI Video Library ecosystem.

———
Built with ❤️ for the AI creative community by MADUSHADEV