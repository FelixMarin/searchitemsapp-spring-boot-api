[https://travis-ci.org/FelixMarin/searchitemsapp-spring-boot-api.svg?branch=master](https://travis-ci.org/FelixMarin/searchitemsapp-spring-boot-api.svg?branch=master)

# Comparador de precios de supermercados online.

La aplicación consiste en un servicio web con arquitectura Restful, desarrollado en java Empresarial. Lo que hace la aplicación es retorna una lista de productos de supermercado del mismo tipo. Esta lista viene ordenada por precio o por precio kilo según se indique en los parámetros de entrada indicados en la solicitud. La solicitud se realiza mediante el método GET a través del protocolo http lo que significa que los parámetros de la petición van en la propia URL. En la URL se indican parámetros como el nombre del producto y los identificadores de los supermercados con los que se va a realizar la comparativa.

## Herramientas

- [Spring Tools Suite](https://spring.io/tools)
- [OpenJDK 14](https://openjdk.java.net/projects/jdk/)
- [Spring Boot](https://start.spring.io/)
- [H2](http://h2database.com/html/main.htmls)
- [Postman](https://www.postman.com/)
- [Git](https://git-scm.com/downloads)

## Lenguajes

**Back-end**
- JAVA
- SQL

**Front-end**
- HTML5
- CSS3
- Bootstrap
- Javascript/Jquery

## Arquitectura

- Distribución por capas. 

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
~$ git clone https://github.com/FelixMarin/searchitemsapp-spring-boot-api.git 
```
A continuacion, importar el proyecto al IDE [Spring Tools Suite](https://spring.io/tools):

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

## Ejecutable
[Descargar](https://github.com/FelixMarin/searchitemsapp-spring-boot-api/releases/download/rls1.9/rls1.9.zip)

## Uso de la aplicación

- **Formato de la URL con la que se realizará la solicitud al servicio:**

```console
http://[url]:[port]/searchitemsapp/search?country=[id_país]&category=[id_categoría]&sort=[ordenar]&product=[producto]&pipedcompanies=[id_empresa]
```

- **Lista de parámetros de la solicitud:**

| Parámetro | Valor |
| --- | --- |
| __country__ | 101 (España). |
| __category__ | 101 (Supermercados) |
| __sort__ | PVP: 1 / PVP/KILO: 2 |
| __product__ | (Arroz, Aceite, sal, ...) |
| __pipedcompanies__ | [101] , [101,103,104] , [ALL] |
 

- **Ejemplos de solicitud:**

Esta URL devolverá un listado de objetos json con los productos de todos los supermercados ordenados por precio. 

```console
http://[url]:[port]/search?country=101&category=101&sort=1&product=arroz&pipedcompanies=ALL
```

Esta URL devolverá un listado de objetos json con los productos de un supermercado ordenados por volumen.

```console
http://[url]:[port]/search?country=101&category=101&sort=1&product=sal&pipedcompanies=103
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

#### Ejemplo backend en [vídeo](https://youtu.be/smuZhHQhij4)
#### Ejemplo frontend en [vídeo](https://www.youtube.com/watch?v=K_4Wp0Poh2Q&t=7s)
#### [Descargar](https://github.com/FelixMarin/searchitemsapp-spring-boot-api/releases/download/rls1.9/rls1.9.zip)
