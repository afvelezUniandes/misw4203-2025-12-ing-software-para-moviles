# 🎵 VinylWave App

## 📱 Descripción
VinylWave es una aplicación móvil para Android que permite explorar y gestionar una colección de álbumes musicales. Navega por la lista de álbumes disponibles y consulta información detallada de cada uno.

## 👥 Equipo de desarrollo

- 👩‍💻 Paula Alejandra Sandoval Goyeneche - pa.sandoval@uniandes.edu.co
- 👨‍💻 Jose Daniel Garcia Arias - jd.garciaa1@uniandes.edu.co
- 👨‍💻 Andres Felipe Velez Velez - af.velezv1@uniandes.edu.co
- 👨‍💻 Juan Jose Chica Osorio - j.chicao@uniandes.edu.co


## ✨ Características
- 📋 Visualización de lista de álbumes
- 📊 Detalles de cada álbum
- 📋 Visualización de lista de artistas
- 📊 Detalles de cada artista 
- 📋 Visualización de lista de coleccionistas

## 🛠️ Requisitos del sistema

- Android Studio con soporte para Compose
- JDK 11
- Android SDK:
  - Nivel API mínimo: 21 (Android 5.0 Lollipop)
  - Nivel API objetivo: 35
- Emulador Android o dispositivo físico con Android 5.0+

## 🚀 Instalación

### Clonar el repositorio

```bash
git clone https://github.com/usuario/misw4203-2025-12-ing-software-para-moviles.git
cd misw4203-2025-12-ing-software-para-moviles
```
## 📥 Descarga del APK

Puedes descargar la última versión del APK directamente desde el repositorio:

- El archivo APK de la aplicación se encuentra en la carpeta release del repositorio. Para instalar la aplicación en tu dispositivo Android, navega a esta carpeta y transfiere el archivo release1.apk a tu dispositivo.

## ⚙️ Configuración del servidor backend

La aplicación consume una API REST que debe estar en ejecución en http://10.0.2.2:3000/ (localhost para el emulador Android).

Para configurar el backend:

1. Clona el repositorio del backend: 

```bash
git clone https://github.com/MISW-4203-2023/BackVynils.git
```

2. Instala las dependencias:

```bash
npm install
```

3. Inicia el servidor:
```bash
npm run start
```

El backend utiliza PostgreSQL como base de datos. Asegúrate de tener configurado PostgreSQL con:

- Usuario: postgres
- Contraseña: 123
- Base de datos: vinyls

## 📲 Compilar y ejecutar la aplicación

1. Abre el proyecto en Android Studio
2. Sincroniza el proyecto con los archivos Gradle
3. Ejecuta la aplicación en un emulador o dispositivo físico

## 🧪 Ejecución de pruebas

Para el caso de las pruebas realizadas con Espresso, se puede ejecutar haciendo uso de la interfaz de android studio haciendo clic en las botones de ejecición ▶️ mostrados en la siguiente imagen:
![image](https://github.com/user-attachments/assets/44411700-5083-4001-9604-c80871aaf9b5)

## 💻 Tecnologías utilizadas

- Jetpack Compose: Framework moderno para UI declarativa
- Retrofit: Cliente HTTP para consumo de APIs
- Coil: Carga de imágenes asíncrona
- Kotlin: Lenguaje de programación
  
## 🏗️ Arquitectura

La aplicación sigue una arquitectura MVVM (Model-View-ViewModel) e implementa los siguientes patrones:

- MVVM: Separación de la lógica de presentación y la UI
- Repository: Centralización del acceso a datos
- Service Adapter: Desacoplamiento de la comunicación con servicios externos

## 📁 Estructura del proyecto

```plaintext
com.example.vinilos/
├── models/             # Modelos de datos
├── network/            # Configuración de red y API
│   └── adapter/        # Patrón Service Adapter
├── repositories/       # Repositorios para acceso a datos
├── ui/                 # Componentes de UI (Jetpack Compose)
├── utils/              # Utilidades
└── viewmodels/         # ViewModels (MVVM)
```
