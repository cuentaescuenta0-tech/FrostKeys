# Aviso de privacidad — FrostKeys Lineage

**Última actualización:** 2 de noviembre de 2026

Este aviso se refiere únicamente a las compilaciones experimentales de este fork. No es el aviso de privacidad de la aplicación original FrostKeys distribuida por su mantenedor.

## Datos y procesamiento local

Esta rama elimina Gemini, Klipy, la búsqueda remota de GIFs/stickers y los permisos `INTERNET` y `ACCESS_NETWORK_STATE`. La aplicación no incorpora en esta rama un servicio de IA o contenido remoto ni envía las teclas o consultas a esos servicios.

Las palabras aprendidas, el historial local del portapapeles y los ajustes se procesan y guardan en el dispositivo. El usuario puede exportar o restaurar los respaldos desde la aplicación. El permiso opcional de contactos se usa localmente para sugerencias de nombres si el usuario habilita esa función. Esta rama no incorpora analítica ni rastreadores.

## Permisos

El manifiesto de esta rama solicita permisos necesarios para el funcionamiento local del teclado, vibración, diccionarios y, si se habilita, sugerencias basadas en contactos. El workflow de GitHub Actions comprueba que el APK de depuración conserve el identificador de paquete paralelo y debe utilizarse también para verificar el manifiesto final antes de distribuirlo.

## Alcance

Este es un proyecto experimental en desarrollo. El aviso solo describe el código de este repositorio y puede dejar de ser válido si alguien añade cambios o dependencias. Revisa el manifiesto y las dependencias de la versión concreta antes de instalarla. Para preguntas sobre este fork, utiliza los Issues de este repositorio.
