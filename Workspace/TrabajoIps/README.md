[![Build Status](https://travis-ci.org/magicadri/TrabajoIps.svg?branch=master)](https://travis-ci.org/magicadri/TrabajoIps)
# Proyecto IPS. Grupo 5 Lab2

## Ciclo de desarrollo
Los pasos de un ciclo normal de desarrollo son:

1. Hacer pull del repositorio para descargar los cambio.
2. Trabajar en las historias.
3. Cada vez que hagas algo significativo y **el código compile** hacer commit.
4. Si ya has terminado, hacer push al servidor. Sino, volver al paso 2.

## Instrucciones de instalación
* Clonar el repositorio para tenerlo en local. Se puede usar Sourcetree o otra herramienta a tu elección.
* Dependiendo del IDE tienes que:
    * Eclipse: (probado con Eclipse Neon, no puedo asegurar que funcione en versiones más antiguas). File -> Import -> Gradle -> Gradle project. Seleccionas el path al proyecto y le das a finish.
* Importar proyecto con normalidad

## Como ejectuar
Se está utilizando un sistema de buildeo llamado Gradle, similar a Maven. Este se encarga de la compilación, ejecución de tests y descarga de dependencias (si las hubiera). Hay dos formas principales de utilizarlo:

* Desde terminal. Tendrías que ir a la carpeta raiz y ejecutar:
    * Windows 
  
    ```
    gradelw run
    ```

    * Linux 

    ```
    ./gradelw run
    ```
* Desde el IDE. Eclipse, IntelliJ o Netbeans tienen soporte para gradle. Deberíais poder encontrar una lista de tasks, estan run entre ellas.
    * Eclipse: Las distintas opciones de gradle están en el menú inferior, donde pone *Gradle Tasks*. Las principales son *build* y *run*.
