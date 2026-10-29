## 🗂️ Estructura del Proyecto

```
MascotaIoT/
├── app/
│   ├── build.gradle                          ← Dependencias nativas (sin Firebase)
│   └── src/main/
│       ├── AndroidManifest.xml               ← Declaración de las 7 Activities
│       ├── java/com/example/mascotaiot/
│       │   ├── LoginActivity.kt              ← Pantalla 1: Login
│       │   ├── CrearCuentaActivity.kt        ← Pantalla 2: Registro
│       │   ├── MenuActivity.kt               ← Pantalla 3: Menú Principal
│       │   ├── ManipularCircuitoActivity.kt  ← Pantalla 4: Control Compuerta + LED
│       │   ├── VerAccionesActivity.kt        ← Pantalla 5: Historial
│       │   ├── EstadisticasActivity.kt       ← Pantalla 6: Dashboard Estadísticas
│       │   ├── VerPerfilActivity.kt          ← Pantalla 7: Perfil de Usuario
│       │   ├── LocalRepository.kt            ← Repositorio de datos en memoria para testeo
│       │   ├── UsuarioModel.kt               ← Modelo de Usuario
│       │   ├── AccionModel.kt                ← Modelo de Acción
│       │   └── AccionesAdapter.kt            ← Adaptador para RecyclerView
│       └── res/
│           ├── drawable/                     ← Iconos vectoriales nativos (sin emojis)
│           ├── layout/                       ← Layouts XML con contraste y accesibilidad
│           └── values/
│               ├── colors.xml
│               ├── strings.xml
│               └── themes.xml
├── build.gradle
└── settings.gradle
```

---

## 🚀 Cómo ejecutar en Android Studio

1. Abre Android Studio.
2. Selecciona **File → Open** y elige la carpeta `MascotaIoT`.
3. Haz clic en **Sync Project with Gradle Files**.
4. Presiona **Run 'app'** en tu emulador o dispositivo físico.
