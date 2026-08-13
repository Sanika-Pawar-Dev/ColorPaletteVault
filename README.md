# Colorex: Color Palette Vault Studio
A localized GUI-based Design Token Management System built with **Java Swing (AWT/Swing GUI Engine)** and **MongoDB** as a core persistent NoSQL data repository.

Developed by **Sanika Pawar** (TYBSc IT, Roll No. 485) at **Guru Nanak Khalsa College, Mumbai**.

---

## 🚀 Environment & Deployment Requirements
To execute this standalone Java application locally with full database functionality during evaluation, ensure your workstation meets the following prerequisites:

### 1. MongoDB Local Daemon Engine
* **Service Status:** Ensure the MongoDB Server background daemon is actively running locally on your machine before booting the app.
* **Connection Address:** `mongodb://localhost:27017`
* **Target Database Name:** `ColorVault` (Automatically provisioned by the application on initialization)
* **Target Collections:**
    * `users` — Stores system authentication credentials and Role-Based Access Control (RBAC) levels.
    * `palettes` — Stores the saved hex design tokens and color management properties.

### 2. Java Runtime Environment (JRE/JDK)
* **Compatibility:** Fully compliant with **JDK 8 (Java 1.8) and above**.
* The system features cross-version backward compatibility, verified to compile and run seamlessly on modern environments up to JDK 25.

### 3. Cross-IDE Portability Setup (Offline Fallback)
* **Automated Framework:** The project features a structured **Maven Build Configuration (`pom.xml`)**.
* **Zero-Internet Dependency:** To eliminate network failure points during grading, the external runtime libraries—the official **MongoDB Java Driver (3.12.14)** and the **FlatLaf Look-and-Feel Engine (3.6.2)**—are physically embedded directly inside the project tree at `/src/lib/`.
* When loaded into any modern development environment, the IDE automatically maps the dependencies locally using the absolute system scope path property `${project.basedir}` with **zero manual configuration or internet access required**.

---

## 🛠️ Step-by-Step Execution Guide

### Standard Automated Launch
1. Download or clone this project repository directory structure onto your local workstation.
2. Open your preferred Java development tool (**IntelliJ IDEA, VS Code, Eclipse, or NetBeans**).
3. Select **File ➡️ Open** and click directly on the root project folder (`ColorPaletteVault`).
4. Wait a brief moment for the background engine to parse the `pom.xml` configuration and automatically link the local library path arrays.
5. Open `src/ColorVaultApp.java`, right-click inside the file, and select **Run 'ColorVaultApp.main()'** to launch the dark-mode graphical user interface.

### Manual Build Path Backup (If IDE Automation is Disabled)
If your host IDE platform configuration does not natively parse the Maven build automation tree framework, the application libraries can be mapped manually in 3 clicks:
1. Open your IDE's **Build Path** / **Project Structure** configuration settings panel.
2. Select **Add External JARs** or **Dependencies Path**.
3. Navigate directly into the `/src/lib/` directory inside this project folder.
4. Select both `mongo-java-driver-3.12.14.jar` and `flatlaf-3.6.2.jar` to instantly restore local runtime compilation.

---

## 📁 Core Architectural Layout
```text
ColorPaletteVault/       <-- Main top-level root folder
├── pom.xml              <-- Automated cross-IDE build configuration script
├── README.md            <-- Deployment guide and system documentation
├── .gitignore           <-- Version control exclusion rules
├── ColorPaletteVault.iml <-- Legacy IntelliJ configuration module
└── src/                 <-- Main application source root folder
    ├── lib/             <-- Embedded physical driver binaries (Offline Safety Net)
    │   ├── flatlaf-3.6.2.jar
    │   └── mongo-java-driver-3.12.14.jar
    ├── ColorVaultApp.java    <-- Core System Entry Point (Main Class)
    ├── DashboardView.java    <-- Primary Application Workspace UI
    ├── DatabaseConfig.java   <-- MongoDB Connection Client Driver Hooks
    ├── DigitalModelerRole.java <-- Role Module Asset
    ├── LoginView.java        <-- Authentication Panel UI
    ├── RegisterView.java     <-- User Enrollment Interface UI
    ├── SecurityEngine.java   <-- Hashing and Input Validation Mechanics
    ├── UxArchitectRole.java  <-- Role Module Asset
    └── WebMasterRole.java    <-- Role Module Asset
