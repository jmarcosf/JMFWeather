JmfWeather
--------------
**Pr�ctica asignatura Android Fundamental, Master Programaci�n de Apps, U-Tad.**

La pr�ctica consiste en realizar una aplicaci�n de meteorolog�a con los siguientes requisitos:

1) *Dise�o general de la interfaz de usuario con Actividades y Fragmentos, utilizando correctamente las vistas XML y los men�s*.
>Actividades:

>>CCityListActivity.java:          Lista de Ciudades de la BB.DD.

>>CCityDetailsActivity.java:       Detalles de la climatolog�a de una ciudad

>>CBaseCityActivity.java:          Clase base para CCityListActivity y CCityDetailsActivity

>>CSettingsActivity.java:          Preferencias de usuario

>>CCitySearchActivity.java:        B�squeda de ciudades

>>CMessageBoxActivity.java:        Cuadros de di�logo para mensajes al usuario

>>CSplashActivity.java:            Pantalla inicial con el logo

2) *Diferenciaci�n de tablets / m�viles*.
>En modo Tablet existe una preferencia de usuario que indica el modo de funcionamiento en tablets.

3) *Uso de estilos y temas*.
>La aplicaci�n utiliza diferentes estilos y temas para m�viles y tablets.

4) *Uso de navegaci�n efectiva (lateral y ancestral especialmente)*.
>Es posible navegar de manera efectiva por toda la aplicaci�n.

>La interacci�n del usuario se mantiene durante toda la aplicaci�n.

>La actividad CCityListActivity.java contiene un drawer con una peque�a ayuda.

5) *Carga y parseo de datos mediante llamadas HTTP, en background*.
>El servicio CWeatherRetrieverService se encarga de actualizar peri�dicamente la informaci�n de las ciudades en la BB.DD.

>La frecuencia de actualizaci�n se puede ajustar en una preferencia de usuario.

6) *Carga de datos sin interacci�n del usuario necesaria*.
>El servicio se encarga de actualizar la informaci�n de forma independiente de las ciudades de la BB.DD.:

>- Estado de la climatolog�a actual.
>- La predicci�n para los pr�ximos 5 d�as
>- Las im�genes de los iconos.


7) *Almacenamiento persistente de la informaci�n, a modo de cach�, en base de datos SQLite*.

>La Aplicaci�n utiliza SQlite para almacenar la informaci�n de las ciudades en la BB.DD. jmfweather.db.Consta de tres tablas:

>- City:          Relaci�n de ciudades que ha a�adido el usuario
>- Condition: Estado actual de la climatolog�a
>- Forecast:    Predicci�n de los 5 pr�ximos d�as.

>Adem�s se guardan en cach� los ficheros con las im�genes de los iconos correspondiente a cada condici�n meteorol�gica

8) *Gesti�n correcta de preferencias del usuario*.
>Existen 3 preferencias para m�viles y una m�s para tablets.

>- Posibilidad de a�adir la localizaci�n actual de forma autom�tica al arrancar la aplicaci�n.
>- Frecuencia de actualizaci�n de las condiciones meteorol�gicas de cada ciudad de la BB.DD.
>- Unidad de los grados: Celsius o Fahrenheit.

9) *Perfecto funcionamiento de la app*.
>Salvo inesperados, la aplicaci�n funciona correctamente.

**Extras**:

1) *Aspecto est�tico de la aplicaci�n*.
>La aplicaci�n contiene los siguientes aspectos est�ticos:

>- Diferentes layouts para m�viles y tablets.
>- Diferentes layouts para portrait y landscape.
>- Pantalla de Logo inciial.

2) *Utilidad real de la aplicaci�n*.
>M�s all� del aspecto est�tico y de la informaci�n presentada la aplicaci�n tiene una utilidad real.

3) *Cualquier otra funcionalidad adicional que se le ocurra al alumno, siempre que la parte principal est� cubierta*.
>- Scroll horizontal de la predicci�n en la vista de detalles en modo portrait.
>- Sincronizaci�n de la �ltima ciudad visitada y de la interacci�n del usuario entre todas las actividades.
>- Sincronizaci�n en cambios de preferencias de usuario.
>- Lenguajes ingl�s y espa�ol (excepto nombre del pa�s limitado por el Api).
