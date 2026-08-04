# 🚀 Enterprise AI Knowledge Hub - Backend

An enterprise-grade **AI-powered Knowledge Management System** built with **Java 21**, **Spring Boot**, **Spring AI**, **MongoDB**, **Redis**, and **Qdrant**.

The application enables organizations to upload enterprise documents, automatically process them into embeddings, perform semantic search, and chat with an AI assistant using Retrieval-Augmented Generation (RAG).

---

# ✨ Features

## 🔐 Authentication & Security

- JWT Authentication
- Spring Security 6
- Role-Based Access Control (RBAC)
- Password Encryption (BCrypt)
- Stateless Authentication
- Protected REST APIs

---

## 📚 Knowledge Base Management

- Create Knowledge Bases
- Update Knowledge Bases
- Delete Knowledge Bases
- User-specific Knowledge Bases
- Ownership Validation

---

## 📄 Document Management

- Upload PDF, DOCX and TXT documents
- Document Validation
- File Storage
- Download Documents
- Delete Documents
- Re-index Documents

---

## 🧠 Document Intelligence (RAG)

- Automatic Document Processing
- PDF Parsing
- Text Extraction
- Smart Chunking
- Embedding Generation
- Vector Storage in Qdrant
- Semantic Search
- Context Retrieval

---

## 🤖 AI Chat Assistant

- AI-powered Question Answering
- Retrieval-Augmented Generation (RAG)
- Conversation Memory
- Context-aware Responses
- Source Citations
- Streaming Responses (Server Sent Events)

---

## 💬 Conversation Management

- Create Conversations
- Conversation History
- Conversation Memory
- AI Context Preservation
- Redis Cache Support

---

## ⚡ Performance

- Redis Caching
- MongoDB Indexing
- Vector Similarity Search
- Optimized Retrieval
- Streaming AI Responses

---

# 🏗 Architecture

```
                Angular Frontend
                        │
                        ▼
              Spring Boot REST APIs
                        │
 ┌──────────────────────┼──────────────────────┐
 │                      │                      │
 ▼                      ▼                      ▼
MongoDB             Redis Cache          Spring AI
 │                                          │
 ▼                                          ▼
Document Store                      OpenAI GPT Model
 │                                          │
 ▼                                          ▼
Chunking Service  ─────►  Qdrant Vector Database
                        │
                        ▼
                 Semantic Retrieval
                        │
                        ▼
                  AI Response Engine
```

---

# 🛠 Tech Stack

## Backend

- Java 21
- Spring Boot 3
- Spring Security
- Spring AI
- Spring Data MongoDB
- Spring Validation
- Spring Web
- Lombok

---

## AI

- OpenAI GPT
- Spring AI ChatClient
- Embedding Model
- Prompt Engineering
- Retrieval-Augmented Generation (RAG)

---

## Database

- MongoDB
- Redis
- Qdrant Vector Database

---

## Build Tools

- Maven
- Docker
- Docker Compose

---

# 📂 Project Structure

```
src/main/java/com/rishi/aihub

├── common
│
├── config
│
├── security
│
├── features
│   ├── auth
│   ├── knowledgebase
│   ├── document
│   ├── chunk
│   ├── embedding
│   ├── vector
│   ├── retrieval
│   ├── ai
│   ├── conversation
│   ├── storage
│   └── ingestion
│
└── exception
```

---

# 📌 Core Modules

## Authentication

- Login
- Register
- JWT Token Generation
- Token Validation

---

## Knowledge Base

- CRUD Operations
- Ownership Validation

---

## Document

- Upload
- Download
- Delete
- Re-index

---

## AI

- Prompt Builder
- Conversation Memory
- Retrieval Service
- AI Orchestrator
- Streaming API

---

## Vector Search

- Embedding Generation
- Similarity Search
- Metadata Filtering
- Source Mapping

---

# 🔄 RAG Pipeline

```
Upload Document
        │
        ▼
Text Extraction
        │
        ▼
Chunking
        │
        ▼
Embedding Generation
        │
        ▼
Qdrant Vector Storage
        │
        ▼
User Question
        │
        ▼
Semantic Search
        │
        ▼
Relevant Chunks
        │
        ▼
Prompt Builder
        │
        ▼
OpenAI GPT
        │
        ▼
AI Response + Sources
```

---

# 🔌 REST APIs

## Authentication

```
POST /api/v1/auth/register
POST /api/v1/auth/login
```

---

## Knowledge Base

```
GET    /api/v1/knowledge-bases
POST   /api/v1/knowledge-bases
PUT    /api/v1/knowledge-bases/{id}
DELETE /api/v1/knowledge-bases/{id}
```

---

## Documents

```
POST   /api/v1/knowledge-bases/{id}/documents
GET    /api/v1/knowledge-bases/{id}/documents
GET    /api/v1/documents/{id}/download
DELETE /api/v1/documents/{id}
POST   /api/v1/documents/{id}/reindex
```

---

## Conversation

```
POST /api/v1/conversations
POST /api/v1/conversations/{id}/chat
POST /api/v1/conversations/{id}/stream
GET  /api/v1/conversations/{id}/messages
```

---

# 🔐 Security

- JWT Authentication
- BCrypt Password Encryption
- User Ownership Validation
- Protected APIs
- Role-Based Authorization

---

# 🚀 Getting Started

## Clone Repository

```bash
git clone https://github.com/<your-username>/enterprise-ai-backend.git
```

## Build

```bash
mvn clean install
```

## Run

```bash
mvn spring-boot:run
```

---

# 🐳 Docker

```bash
docker-compose up -d
```

Services:

- MongoDB
- Redis
- Qdrant

---

# 📈 Future Enhancements

- Multi-Model AI Support (OpenAI, Gemini, Claude)
- Hybrid Search (Keyword + Vector)
- OCR Support
- Multi-Tenant Architecture
- Admin Dashboard
- Background Job Queue
- Kubernetes Deployment
- Observability (Micrometer + Prometheus + Grafana)

---

# 👨‍💻 Author

**Rishi Dubey**

Java | Spring Boot | Spring AI | Microservices | MongoDB | Redis | Qdrant | Angular | Docker

---

# ⭐ If you found this project useful, consider giving it a Star.