# App Java Spring Boot

## Idea: 
tienda online

## Tecnologias usadas:
- Java
- Spring boot
- Spring security
- Thymeleaf
- Jpa
- Hibernate validation
- Sql
- Lombok
- Jakarta Validation

## Que podemos hacer??
![Navegacion Basica](./NavegacionBasica.gif)

- Desplegar la lista completa de productos, o desplegarla por su categoria, teniendo los productos mas de una posible categoria
- Podemos a acceder a una pagina personal para cada producto, en ella podremos agregar la cantidad que queremos en el carrito.
- El carrito podra ser comprado estando logeado o no, en el caso de no estarlo se le pedira al usuario no logeado algunos datos para la entrega.
- Buscar un producto por el nombre en el buscador. Luego podemos filtrar esa busqueda por categoria


### USUARIOS
![Users](./Usuarios.gif)

- El usuario podra crear una nueva cuenta para si mismo , o ingresar con una existente
- La constraseña de cada usuario estara encriptada en la BDD
- Hay dos tipos de usuarios, con el rol de admin o rol de user.

### Rol User

![Rol User](./User.gif)

- El rol de user podra: 
    - comprar productos
    - guardar productos en favoritos
    - editar sus propios datos de usuario


### Rol Admin
![Rol User](./Admin.gif)

- El rol de admin podra: 
    - agregar nuevos productos o categorias
    - editar nuevos productos o categorias
    - comprar productos 
    - borrar productos o categorias
    - habilitar o desabilitar categorias
    - guardar productos en favoritos 
    - podra agregar nuevos usuarios
    - podra editar el rol que tiene un usuario o desabilitarlo al usuario
    - editar sus propios datos de usuario 

![Rol User](./Autenti.gif)

- Si un usuario no autenticado intenta entrar o realizar acciones que exijen un rol o el authenticarse , se le redireccionara al login page
- Si un usuario no tiene la autorizacion necesaria, se le mostrara una pagina de error con un mensaje y un link a la pagina de inicio



## Como arrancarlo??
- Primero importar los archivos SQL para tener la BDD completa, eso incluye 2 usuarios de prueba, uno con rol admin y user(Mati , 123), y otro con solo el rol de user (Eze, 123)
- Se debera completar los dataSource en el application.properties
    -![properties](properties.png)
- Descargar todas las dependencias del pom.xml
- Por ultimo ejecutarlo con su entorno de desarrollo(netbeans, eclipse, etc...)en el puerto que se desee
