# proyectoTercerTrimestr
Este repositorio contiene la capa de dominio y la lógica central del sistema HelpDesk U. Aquí se encuentran las clases que modelan las entidades principales del proyecto, así como los componentes encargados de gestionar su ciclo de vida y reglas de negocio.
Contenido del Proyecto
Dominio
Incluye las clases fundamentales del modelo:
Usuario
Departamento
Ticket
Diccionario
Palabra
Estas clases representan las entidades principales del sistema y definen su comportamiento.
Lógica y Gestión
Data: Responsable del almacenamiento en memoria, manejando las colecciones internas de objetos.
Gestor: Implementa las reglas de negocio del sistema y coordina la creación, actualización y gestión de todas las entidades.
Pruebas Unitarias
El repositorio incluye un conjunto de test cases que validan el funcionamiento de la capa de dominio y aseguran la integridad del modelo.
Documentación (JavaDoc)
Se genera JavaDoc para facilitar la comprensión del código, su estructura y las responsabilidades de cada clase.
Integración
Este módulo es independiente de cualquier interfaz gráfica o API.
Expone un archivo .jar que puede ser utilizado por cualquier aplicación que necesite las funcionalidades del dominio y la lógica del HelpDesk U.
