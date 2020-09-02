
# Web service para obtener una lista de productos del mimo tipo, ordenada por precio y que son vendidos en supermercados online.

Una aplicación web desarrollada en java EE, Spring Framework e Hibernate. El servicio web recibe una solicitud y la aplicación devolverá una estructura en formato JSON con una lista ordenada por precio con productos vendidos en supermercados online. La característica principal del servicio es la utilizaciión de internet como fuente de datos, dichos datos son obtenidos en tiempo real, lo que permite tener siempre la información atualizada. El servicio es una API RESTFul, con lo que para obtener la informcación habrá que solicitarla mediante una petición sobre el protocolo HTTP.

## Herramientas

- [Ubuntu Server 18.04](https://ubuntu.com/download/server)
- [Eclipse IDE for J2EE](https://www.eclipse.org/ide/)
- [PostgresSQL](https://www.postgresql.org/)
- [OpenJDK 14](https://openjdk.java.net/projects/jdk/)
- [Spring Framework](https://spring.io/)
- [Apache Tomcat 9](http://tomcat.apache.org/)
- [SmartBear SoapUI](https://www.soapui.org/)
- [Oracle VirtualBox](https://www.virtualbox.org/)

## Lenguajes

- JAVA – J2EE
- HTML
- CSS
- JAVASCRIPT
- SQL

## Arquitectura

- RestFul

## Frameworks

- Spring web MVC, DATA
- Hibernate

## Técnica de extracción de datos

- [Web Scraping](https://es.wikipedia.org/wiki/Web_scraping)

# Preparación del Entorno

## Instalación del SGBD  

Se procede a [instalar PosgreSQL](https://www.digitalocean.com/community/tutorials/como-instalar-y-utilizar-postgresql-en-ubuntu-18-04-es) en el sistema operativo. Una vez instalado y configurado el SGBD, se ejecuta el script de la base de datos que se encuentra en **./BBDD/backup_sia_bbdd.sql**. Finalmente, Crear los siguientes **'Login/Grup Roles'** usados por la aplicación: 

| Usuario | Permisos |
| --- | --- |
| pgadmin | administrador |
| sia_select | Lectura |

## Instalación del entorno 
El primer paso consiste en descargar el proyecto de GitHub.  

```bash
~$ sudo apt install git 
~$ git clone https://github.com:/FelixMarin/searchitemsapp.git 
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

Se crea un el directorio **'/resources/'** en la raiz del sistema. 

Este directorio contiene los ficheros **'*.properties'**:

```console
   /resources/
   |----/resources/db.properties
   |----/resources/flow.properties
   |----/resources/log4j.properties
```

| Properties | Descripción |
| --- | --- |
| **db.properties** | contiene los literales y datos de conexión a la base de datos. |
| **flow.properties** | contiene todos los literales de la aplicación. |
| **log4j.properties** | contiene la configuración de la libreria de registros log4j. |

El fichero **flow.properties** contiene tres rutas a tener encueta. Son las rutas al ejecutabme del navegador firefox y los drivers de chrome y gecko. Hay que colocar los drivers en la ruta indicada.

```console
folw.value.firefox.ejecutable.path=/usr/bin/firefox
flow.value.google.driver.path=/usr/local/bin/drivers/chrome/chromedriver 
flow.value.firefox.driver.path=/usr/local/bin/drivers/firefox/geckodriver
```

A continuación, descargar los drivers de Firefox y Chrome y situarlos en la ruta que aparece a continuación. 

```console
/usr/local/bin/drivers/chrome/chromedriver 
/usr/local/bin/drivers/firefox/geckodriver 
```

El siguiente paso es añadir al fichero **/etc/environmet** las siguientes variables de entorno.  

| Variable de Entorno | Valor |
| --- | --- |
| **PROPERTIES_SIA** | "/resources" | 
| **CATALINA_HOME** | "/[path_to]/apache-tomcat-9" | 
| **JAVA_HOME** | "/[path_to]/java-14-openjdk-amd64" | 
| **JRE_HOME** | "/[path_to]/java-14-openjdk-amd64" |

Se crea un directorio llamado logs en la raíz de sistema para recoger los logs que va escribiendo la aplicación.  

```console
/log4j/
|-----/log4j/daily.log
|-----/log4j/error.log
```

[Instalar el servidor de aplicaciones Apache Tomcat 9.](https://tecnstuff.net/how-to-install-tomcat-9-on-ubuntu-18-04/) y [Vicular Tomcat a Eclipse IDE](https://www.codejava.net/servers/tomcat/how-to-add-tomcat-server-in-eclipse-ide). 
 
Finalmente, una vez preparado el entorno habrá que compilar el proyecto y desplegar la aplicación en el servidor Tomcat.

![Instalación Apache Tomcat](https://github.com/FelixMarin/searchitemsapp/blob/v0.7.0/docimg/tomcat.png)


## Uso de la aplicación

- **Para acceder a la interfaz gráfica de la aplicación se hará mediante el fichero index.jsp:**

```console
http://[url]:[port]/searchitemsapp/index.jsp
```

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

#### Ejemplo en [vídeo](https://youtu.be/K_4Wp0Poh2Q)
