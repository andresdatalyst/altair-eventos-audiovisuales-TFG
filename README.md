# Altair Eventos (Proyecto Final)

**Proyecto web para la gestión y organización de eventos audiovisuales**

---

## 📌 Resumen
**Altair Eventos** es una aplicación web desarrollada con Spring Boot para gestionar productores, empresas trabajadoras, trabajadores, localizaciones, material audiovisual y eventos. Permite crear eventos, asignar recursos, controlar accesos por roles y desplegar en un servidor Tomcat.

> **Nota importante:** Este proyecto fue **desarrollado sin IA**. ✅

---

## 🧭 Tabla de contenidos
- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Requisitos](#-requisitos)
- [Instalación y ejecución](#-instalación-y-ejecución)
- [Configuración de la base de datos](#-configuración-de-la-base-de-datos)
- [Estructura del proyecto](#-estructura-del-proyecto)
- [Seguridad y buenas prácticas](#-seguridad-y-buenas-prácticas)
- [Documentación](#-documentación)
- [Licencia](#-licencia)

---

## ✅ Características principales
- Gestión de usuarios y roles (Admin, Productor, Jefe de empresa, Trabajador).
- CRUD para: Productores, Trabajadores, Empresas trabajadoras, Localizaciones, Material Audiovisual, Eventos.
- Reglas de negocio implementadas (restricciones de fechas, unicidad de nombres y comprobaciones por empresa/localización).
- Paginación, búsqueda y filtros en listados.
- Subida de imágenes y archivos (directorio `uploads/`).
- Seguridad con Spring Security y encriptación de contraseñas (BCrypt).

---

## 🛠️ Tecnologías
- Java 17
- Spring Boot 2.6.x (Web, Data JPA, Security, Thymeleaf, Validation)
- Maven
- Thymeleaf (vistas)
- MySQL (producción)
- Bootstrap (frontend)

---

## ⚙️ Requisitos
- JDK 17
- Maven
- MySQL (o un contenedor MySQL)
- (Opcional) Tomcat para desplegar WAR

---

## 🚀 Instalación y ejecución (local)
1. Clona el repositorio:

```bash
git clone <tu-repo-url>
cd proyecto-final-altair-audiovisual
```

2. Configura la base de datos (ver **Configuración de la base de datos**).

3. Ejecuta con Maven (empieza la app embebida):

```bash
./mvnw spring-boot:run
# o
mvn spring-boot:run
```

4. Accede en: http://localhost:8080/altairAudiovisuales

---


- El proyecto incluye `src/main/resources/import.sql` para importar datos iniciales.

---

## 🧩 Estructura del proyecto (resumen)
- `src/main/java/com/andrespr/springboot/app` - código fuente principal
  - `controllers/` - controladores MVC
  - `models/` - entidades JPA
  - `models/repository/` - interfaces de acceso a datos
  - `services/` - lógica de negocio
  - `handler/`, `util/` - utilidades y manejadores
- `src/main/resources/templates` - vistas Thymeleaf
- `src/main/resources/static` - CSS, JS, imágenes
- `docs/` - documentación extra (manual, memoria y presentación en texto)

---

## 🔐 Seguridad y buenas prácticas
- No incluir credenciales en el repositorio; usar variables de entorno o servicios de secret management.
- Cambiar la contraseña por defecto y validar roles antes de operaciones sensibles.
- Revisar `application.properties` y remover datos reales del control de versiones.
- Considerar HTTPS para producción y revisar configuración de CORS si se expone a terceros.

---

## 📚 Documentación y recursos
- Manual de usuario, memoria y presentación se encuentran en los correspondientes pdf`.
- Para crear usuarios y datos de prueba, revisa `src/main/resources/import.sql`.

---

## 🤝 Contribuciones
- Si deseas añadir mejoras o reportar bugs, crea un _issue_ o un _pull request_.

---

**Contacto:** Andrés Pérez 




