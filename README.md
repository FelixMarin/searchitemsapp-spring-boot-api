
# API REST para obtener una lista de productos del mismo tipo, vendidos en supermercados online, y ordenados por precio.

Una aplicación web desarrollada en java EE, Spring Framework e Hibernate. La API rest recibe una solicitud y esta devuelve una estructura en formato JSON con una lista ordenada por precio con productos vendidos en supermercados online. La característica principal del servicio es la utilizaciión de internet como fuente de datos, dichos datos son obtenidos en tiempo real, lo que permite tener siempre la información atualizada. El servicio es una API RESTFul, con lo que para obtener la informcación habrá que solicitarla mediante una petición sobre el protocolo HTTP.

## Herramientas

- [Spring Tools Suite](https://spring.io/tools)
- [OpenJDK 14](https://openjdk.java.net/projects/jdk/)
- [Spring Boot](https://start.spring.io/)
- [H2](http://h2database.com/html/main.htmls)
- [Postman](https://www.postman.com/)
- [Git](https://git-scm.com/downloads)

## Lenguajes

- JAVA – J2EE
- SQL

## Arquitectura

- RestFul

## Frameworks

- Spring Boot
- Maven
- Hibernate

## Técnica de extracción de datos

- [Web Scraping](https://es.wikipedia.org/wiki/Web_scraping)

# Preparación del Entorno


## Instalación del entorno 
El primer paso consiste en descargar el proyecto de GitHub.  

```bash
~$ sudo apt install git 
~$ git clone https://github.com/FelixMarin/searchitemsapp-spring-boot-version.git 
```
A continuacion, importar el proyecto en Eclipse IDE:

```bash
1. 'File > Import'.

En el Import Wizard:

2. Expandir 'Git' => 'Projects from Git' => 'Next'.
3. 'Existing local repository' => 'bash Next'.
4. 'Add' (para navegar a cualquier repositorio local).
5. En 'Wizard for project import' => 'Next'. 
6. 'Import  existing Eclipse project' => 'Next'.
7. 'Select nested projects'.
8. 'Import Projects'.
9. 'Finish'.
```

Una vez importado el proyecto, actualizar las dependencias Maven: 

```bash
1. 'Project Properties' => 'Maven' => 'Maven Update'.
2. 'Project Properties' => 'Run' => 'Maven Clean'.
```

## Ejecutar la apliación

Se debe ejecutar el comando "mvn clean package" para generar el jar. La aplicacion compilada se genera el directorio target dentro del directorio del código fuente de la aplicación. 

Para ejecutar la apliación se utilizará el siguiente comando:

```console
java -jar sia-0.0.1-SNAPSHOT.jar
```

## Uso de la aplicación

- **Formato de la URL con la que se realizará la solicitud al servicio:**

```console
http://[url]:[port]/searchitemsapp/search?pais=[id_país]&categoria=[id_categoría]&ordenacion=[ordenar]&producto=[producto]&empresas=[id_empresa]
```

- **Lista de parámetros de la solicitud:**

| Parámetro | Valor |
| --- | --- |
| __país__ | 101 (España). |
| __categoría__ | 101 (Supermercados) |
| __ordenar__ | PVP: 1 / PVP/KILO: 2 |
| __producto__ | (Arroz, Aceite, sal, ...) |
| __super__ | [101] , [101,103,104] , [ALL] |
 

- **Ejemplos de solicitud:**

Esta URL devolverá un listado de objetos json con los productos de todos los supermercados ordenados por precio. 

```console
http://[url]:[port]/searchitemsapp/search?pais=101&categoria=101&ordenacion=1&producto=arroz/empresas=ALL
```

Esta URL devolverá un listado de objetos json con los productos de un supermercado ordenados por volumen.

```console
http://[url]:[port]/searchitemsapp/search?pais=101&categoria=101&ordenacion=2&producto=sal&empresas=103
```

- **Ejemplo de respuesta:**

```console
{
    "resultado": [
        {
            "identificador": "1",
            "nomProducto": "Arroz categoría extra Carrefour 1 kg.",
            "didEmpresa": "104",
            "nomEmpresa": "CARREFOUR",
            "imagen": "https://static.carrefour.es/hd_280x_/img_pim_food/101424_00_1.jpg",
            "nomUrl": "https://www.carrefour.es/supermercado/arroz-categoria-extra-carrefour-1/
            "precio": "0,78 eur",
            "precioKilo": "0,78 eur/kg"
        },
        {
            "identificador": "2",
            "nomProducto": "Arroz redondo Hacendado",
            "didEmpresa": "101",
            "nomEmpresa": "MERCADONA",
            "imagen": "https://prod-mercadona.imgix.net/images/930c97c9.jpg?fit=crop&h=300&w=300",
            "nomUrl": "null",
            "precio": "0,79",
            "precioKilo": "0,79"
        }
]}
```

#### Ejemplo en [vídeo](https://youtu.be/smuZhHQhij4)
