JmfWeather
--------------
**Práctica asignatura Android Fundamental, Master Programación de Apps, U-Tad.**

La práctica consiste en realizar una aplicación de meteorología con los siguientes requisitos:

1) *Diseño general de la interfaz de usuario con Actividades y Fragmentos, utilizando correctamente las vistas XML y los menús*.
>Actividades:

>>CCityListActivity.java:          Lista de Ciudades de la BB.DD.

>>CCityDetailsActivity.java:       Detalles de la climatología de una ciudad

>>CBaseCityActivity.java:          Clase base para CCityListActivity y CCityDetailsActivity

>>CSettingsActivity.java:          Preferencias de usuario

>>CCitySearchActivity.java:        Búsqueda de ciudades

>>CMessageBoxActivity.java:        Cuadros de diálogo para mensajes al usuario

>>CSplashActivity.java:            Pantalla inicial con el logo

2) *Diferenciación de tablets / móviles*.
>En modo Tablet existe una preferencia de usuario que indica el modo de funcionamiento en tablets.

3) *Uso de estilos y temas*.
>La aplicación utiliza diferentes estilos y temas para móviles y tablets.

4) *Uso de navegación efectiva (lateral y ancestral especialmente)*.
>Es posible navegar de manera efectiva por toda la aplicación.

>La interacción del usuario se mantiene durante toda la aplicación.

>La actividad CCityListActivity.java contiene un drawer con una pequeña ayuda.

5) *Carga y parseo de datos mediante llamadas HTTP, en background*.
>El servicio CWeatherRetrieverService se encarga de actualizar periódicamente la información de las ciudades en la BB.DD.

>La frecuencia de actualización se puede ajustar en una preferencia de usuario.

6) *Carga de datos sin interacción del usuario necesaria*.
>El servicio se encarga de actualizar la información de forma independiente de las ciudades de la BB.DD.:

>- Estado de la climatología actual.
>- La predicción para los próximos 5 días
>- Las imágenes de los iconos.


7) *Almacenamiento persistente de la información, a modo de caché, en base de datos SQLite*.

>La Aplicación utiliza SQlite para almacenar la información de las ciudades en la BB.DD. jmfweather.db.Consta de tres tablas:

>- City:          Relación de ciudades que ha añadido el usuario
>- Condition: Estado actual de la climatología
>- Forecast:    Predicción de los 5 próximos días.

>Además se guardan en caché los ficheros con las imágenes de los iconos correspondiente a cada condición meteorológica

8) *Gestión correcta de preferencias del usuario*.
>Existen 3 preferencias para móviles y una más para tablets.

>- Posibilidad de añadir la localización actual de forma automática al arrancar la aplicación.
>- Frecuencia de actualización de las condiciones meteorológicas de cada ciudad de la BB.DD.
>- Unidad de los grados: Celsius o Fahrenheit.

9) *Perfecto funcionamiento de la app*.
>Salvo inesperados, la aplicación funciona correctamente.

**Extras**:

1) *Aspecto estético de la aplicación*.
>La aplicación contiene los siguientes aspectos estéticos:

>- Diferentes layouts para móviles y tablets.
>- Diferentes layouts para portrait y landscape.
>- Pantalla de Logo inciial.

2) *Utilidad real de la aplicación*.
>Más allá del aspecto estético y de la información presentada la aplicación tiene una utilidad real.

3) *Cualquier otra funcionalidad adicional que se le ocurra al alumno, siempre que la parte principal esté cubierta*.
>- Scroll horizontal de la predicción en la vista de detalles en modo portrait.
>- Sincronización de la última ciudad visitada y de la interacción del usuario entre todas las actividades.
>- Sincronización en cambios de preferencias de usuario.
>- Lenguajes inglés y español (excepto nombre del país limitado por el Api).
