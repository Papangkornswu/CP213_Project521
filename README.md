# TalkDeck 🎴

## 📌 Overview

**TalkDeck** คือแอปพลิเคชันแนว Card Game สำหรับใช้สร้างบทสนทนาและคำถามเพื่อกระตุ้นการพูดคุยในกลุ่ม ไม่ว่าจะเป็นเพื่อน ครอบครัว หรือกิจกรรม ice-breaking ต่าง ๆ โดยผู้ใช้สามารถเลือกหมวดคำถาม หรือสร้างคำถามของตัวเองได้

ตัวแอปถูกออกแบบให้ใช้งานง่าย มี flow ชัดเจน ตั้งแต่การเลือกประเภทคำถาม → เริ่มเกม → แสดงการ์ดคำถาม → จบเกม พร้อม UI ที่เน้นความเรียบง่ายและทันสมัย

---

## 🛠 Tech Stack

* **📱 Mobile Development**
Language: Kotlin
  * Framework: Android Native (Jetpack Compose)
  * Architecture: MVVM (Model - View - ViewModel)
* **🧩 Android Components**
  * Jetpack Compose – ใช้สร้าง UI แบบ Declarative
  * ViewModel – จัดการ state และ logic ของแอป (เช่น GameViewModel.kt)
  * Navigation Component (Compose Navigation) – จัดการการเปลี่ยนหน้าจอ (AppNavigation.kt)

* **Tools & Others**

  * Git & GitHub (Version Control)
  * Figma (UI/UX Design)

---

## ✨ Features

ฟีเจอร์หลักของ TalkDeck:

### 🎮 Core Features

* **เริ่มเกม (Start Game)**

  * เริ่มเล่นเกมด้วยการสุ่มการ์ดคำถาม

* **เลือกหมวดคำถาม**

  * Fun / Casual
  * Deep Talk
  * หรือหมวดอื่น ๆ ในอนาคต

* **ระบบการ์ด (Card System)**

  * แสดงคำถามแบบสุ่ม
  * ปัด/เปลี่ยนการ์ดได้

* **สร้างคำถามเอง (Custom Question)**

  * ผู้ใช้สามารถเพิ่มคำถามของตัวเองได้
  * รองรับการจัดหมวดหมู่
