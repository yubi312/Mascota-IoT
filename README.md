# MascotaIoT — Aplicación Android para IoT
## TI3042 - Aplicaciones Móviles para IoT | Unidad 2

---

## 📱 Modo Testeo y Prototipado 100% Autónomo (Sin Firebase)

El proyecto está configurado para **compilar, ejecutar y probarse directamente en Android Studio o emulador sin necesidad de Firebase ni servicios externos**:
- **Zero Configuration:** No requiere archivo `google-services.json`.
- **Datos locales precargados:** Incluye usuarios y acciones del sistema IoT para testeo instantáneo.
- **Creación dinámica:** Permite registrar nuevos usuarios, manipular la compuerta, generar nuevas acciones y calcular estadísticas en tiempo real.

---

## 🔑 Credenciales de Prueba Disponibles

| Usuario | RUT | Contraseña | Cargo |
| :--- | :--- | :--- | :--- |
| **Carlos Riveros** | `11.111.111-1` | `123456` | Administrador |
| **Agustín Vidal** | `22.222.222-2` | `123456` | Técnico |

*(También puedes crear cualquier cuenta nueva desde el botón **Crear Cuenta**).*

---

## 📱 Flujo de las 7 Pantallas en Modo Testeo

1. **Login:** Inicia sesión con las credenciales de prueba prellenadas o con una cuenta recién creada.
2. **Crear Cuenta:** Guarda el nuevo usuario en el repositorio local y valida duplicados.
3. **Menú:** Muestra el nombre y apellido del usuario activo y da acceso a las 4 áreas.
4. **Manipular Circuito:**
   - **Activar:** Abre la compuerta, cambia el LED a verde y agrega un registro de activación al historial.
   - **Desactivar:** Cierra la compuerta, cambia el LED a rojo y agrega un registro de desactivación.
5. **Ver Acciones:** Muestra el historial completo en un `RecyclerView` (con distancia, hora, fecha, RUT y acción).
6. **Estadísticas:** Calcula y suma en tiempo real:
   - Total de Activaciones
   - Total de Desactivaciones
   - Obstáculos Detectados
   - Distancia Total Acumulada (cm)
7. **Ver Perfil:** Muestra todos los campos del usuario en sesión con contraseña oculta.

---

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
