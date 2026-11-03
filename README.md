# FrostKeys Lineage (migración experimental)

Este repositorio es un fork de desarrollo no oficial de [FrostKeys](https://github.com/AshwinSoni-01/FrostKeys). El objetivo es migrar el núcleo del teclado a [AOSP LatinIME mantenido por LineageOS](https://github.com/LineageOS/android_packages_inputmethods_LatinIME), conservando las funciones locales de FrostKeys que puedan portarse.

> **Estado:** trabajo en curso. Se aprobó Android 11+ (minSdk 30) para seguir la base LineageOS LatinIME; el núcleo aún requiere integración y validación. Gemini y Klipy permanecen retirados. No debe considerarse una versión final ni sustituye la aplicación original.

## Funciones que se busca conservar

- Interfaz Frosted Glass y personalización visual.
- Menú de acceso, teclado emoji y búsqueda local de emoji.
- Portapapeles local, diccionarios personalizados y varios idiomas.
- Escritura por deslizamiento y respaldo/restauración de ajustes.

Gemini y Klipy se eliminaron: esta compilación no incluye el asistente de IA, búsqueda remota de GIFs ni envío de stickers desde esos servicios.

## Instalar junto a FrostKeys original

La aplicación de este fork usa el identificador `com.orion.frostkeys.lineage`, distinto de `com.orion.frostkeys`. Por eso Android puede instalar ambas aplicaciones en el mismo dispositivo con datos y ajustes separados. Las compilaciones de depuración de CI usan además el sufijo `.debug`.

Los APK de GitHub Actions son artefactos de desarrollo, no versiones firmadas por el mantenedor original ni distribuciones oficiales. Revisa el código y el origen del artefacto antes de instalar un teclado, ya que un teclado puede procesar texto sensible.

## Compilar

El workflow **Build FrostKeys Lineage APK** compila el APK de depuración en GitHub Actions para los cambios de la rama `lineageos-migration` y las pull requests. El artefacto se publica como `FrostKeys-Lineage-debug`. El workflow también verifica que el APK tenga el identificador de paquete paralelo esperado.

Para compilar localmente con Android SDK y NDK instalados:

```bash
./gradlew assembleDebug
```

## Licencias y atribución

Este trabajo conserva los avisos de licencia de los proyectos de los que deriva. FrostKeys y las modificaciones bajo GPL-3.0 deben mantenerse conforme a sus términos; los componentes AOSP conservan sus avisos Apache-2.0. La identidad visual y marcas de los proyectos originales pertenecen a sus respectivos titulares. Este fork no está afiliado ni respaldado por LineageOS, HeliBoard ni el mantenedor original de FrostKeys.
