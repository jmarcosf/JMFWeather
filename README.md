<html>

<head>
<meta http-equiv=Content-Type content="text/html; charset=windows-1252">
<meta name=Generator content="Microsoft Word 14 (filtered)">
<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:Wingdings;
	panose-1:5 0 0 0 0 0 0 0 0 0;}
@font-face
	{font-family:Wingdings;
	panose-1:5 0 0 0 0 0 0 0 0 0;}
@font-face
	{font-family:Calibri;
	panose-1:2 15 5 2 2 2 4 3 2 4;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{margin-top:0cm;
	margin-right:0cm;
	margin-bottom:10.0pt;
	margin-left:0cm;
	line-height:115%;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p
	{margin-right:0cm;
	margin-left:0cm;
	font-size:12.0pt;
	font-family:"Times New Roman","serif";}
p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph
	{margin-top:0cm;
	margin-right:0cm;
	margin-bottom:10.0pt;
	margin-left:36.0pt;
	line-height:115%;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoListParagraphCxSpFirst, li.MsoListParagraphCxSpFirst, div.MsoListParagraphCxSpFirst
	{margin-top:0cm;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:36.0pt;
	margin-bottom:.0001pt;
	line-height:115%;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoListParagraphCxSpMiddle, li.MsoListParagraphCxSpMiddle, div.MsoListParagraphCxSpMiddle
	{margin-top:0cm;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:36.0pt;
	margin-bottom:.0001pt;
	line-height:115%;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoListParagraphCxSpLast, li.MsoListParagraphCxSpLast, div.MsoListParagraphCxSpLast
	{margin-top:0cm;
	margin-right:0cm;
	margin-bottom:10.0pt;
	margin-left:36.0pt;
	line-height:115%;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
.MsoChpDefault
	{font-size:10.0pt;
	font-family:"Calibri","sans-serif";}
@page WordSection1
	{size:595.3pt 841.9pt;
	margin:36.0pt 36.0pt 36.0pt 36.0pt;}
div.WordSection1
	{page:WordSection1;}
 /* List Definitions */
 ol
	{margin-bottom:0cm;}
ul
	{margin-bottom:0cm;}
-->
</style>

</head>

<body lang=ES>

<div class=WordSection1>

<p class=MsoNormal style='line-height:normal'><b><span style='font-size:12.0pt;
font-family:"Times New Roman","serif"'>JmfWeather</span></b></p>

<p class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Práctica
asignatura Android Fundamental, Master Programación de Apps, U-Tad.</span></p>

<p class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>La
práctica consiste en realizar una aplicación de meteorología con los siguientes
requisitos:</span></p>

<ol start=1 type=1>
 <li class=MsoNormal style='line-height:normal'><i><span style='font-family:
     "Times New Roman","serif"'>Diseño general de la interfaz de usuario con Actividades
     y Fragmentos, utilizando correctamente las vistas XML y los menús.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><u><span
style='font-family:"Times New Roman","serif"'>Actividades:</span></u></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CCityListActivity.java:          Lista
de Ciudades de la BB.DD</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CCityDetailsActivity.java:       Detalles
de la climatología de una ciudad</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CBaseCityActivity.java:          Clase
base para CCityListActivity y CCityDetailsActivity</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CSettingsActivity.java:          Preferencias
de usuario</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CCitySearchActivity.java:        Búsqueda
de ciudades</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CMessageBoxActivity.java:        Cuadros
de diálogo para mensajes al usuario</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CSplashActivity.java:            Pantalla
inicial con el logo</span></p>

<ol start=2 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Diferenciación
     de tablets / móviles.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>En modo Tablet existe una
preferencia de usuario que indica el modo de funcionamiento en tablets.</span></p>

<ol start=3 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Uso de
     estilos y temas.</span></i></li>
</ol>

<p class=MsoNormal style='margin-left:36.0pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>La aplicación utiliza diferentes
estilos y temas para móviles y tablets.</span></p>

<ol style='margin-top:0cm' start=4 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Uso de
     navegación efectiva (lateral y ancestral especialmente)</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>Es posible navegar de manera
efectiva por toda la aplicación.</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>La interacción del usuario se
mantiene durante toda la aplicación.</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>La actividad
CCityListActivity.java contiene un drawer con una pequeña ayuda.</span></p>

<ol start=5 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Carga y
     parseo de datos mediante llamadas HTTP, en background.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>El servicio
CWeatherRetrieverService se encarga de actualizar periódicamente la información
de las ciudades en la BB.DD.</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>La frecuencia de actualización se
puede ajustar en una preferencia de usuario.</span></p>

<ol start=6 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Carga de
     datos sin interacción del usuario necesaria.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>El servicio se encarga de
actualizar la información de forma independiente de las ciudades de la BB.DD.:</span></p>

<p class=MsoListParagraphCxSpFirst style='margin-left:90.0pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>·<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Estado de la
climatología actual.</span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:90.0pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>·<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>La predicción
para los próximos 5 días</span></p>

<p class=MsoListParagraphCxSpLast style='margin-left:90.0pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>·<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Las imágenes
de los iconos.</span></p>

<ol style='margin-top:0cm' start=7 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Almacenamiento
     persistente de la información, a modo de caché, en base de datos SQLite.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>La Aplicación utiliza SQlite para
almacenar la información de las ciudades en la BB.DD. jmfweather.db.</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>Consta de tres tablas:</span></p>

<p class=MsoListParagraphCxSpFirst style='margin-top:0cm;margin-right:0cm;
margin-bottom:0cm;margin-left:88.8pt;margin-bottom:.0001pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>·<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>City:          Relación
de ciudades que ha añadido el usuario</span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:88.8pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>·<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Condition: Estado
actual de la climatología</span></p>

<p class=MsoListParagraphCxSpLast style='margin-top:0cm;margin-right:0cm;
margin-bottom:0cm;margin-left:88.8pt;margin-bottom:.0001pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>·<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Forecast:    Predicción
de los 5 próximos días.</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>Además se guardan en caché los
ficheros con las imágenes de los iconos correspondiente a cada condición
meteorológica</span></p>

<ol start=8 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Gestión
     correcta de preferencias del usuario.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>Existen 3 preferencias para
móviles y una más para tablets.</span></p>

<p class=MsoListParagraphCxSpFirst style='margin-left:90.0pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>·<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Posibilidad
de añadir la localización actual de forma automática al arrancar la aplicación.</span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:90.0pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>·<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Frecuencia de
actualización de las condiciones meteorológicas de cada ciudad de la BB.DD.</span></p>

<p class=MsoListParagraphCxSpLast style='margin-left:90.0pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>·<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Unidad de los
grados: Celsius o Fahrenheit.</span></p>

<ol style='margin-top:0cm' start=9 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Perfecto
     funcionamiento de la app.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>Salvo inesperados, la aplicación
funciona correctamente.</span></p>

<p class=MsoNormal style='line-height:normal'><b><span style='font-family:"Times New Roman","serif"'>Extras:</span></b></p>

<ol style='margin-top:0cm' start=1 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Aspecto
     estético de la aplicación.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>La aplicación contiene los
siguientes asptectos estéticos:</span></p>

<ol style='margin-top:0cm' start=1 type=1>
 <ul style='margin-top:0cm' type=circle>
  <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;
      line-height:normal'><span style='font-family:"Times New Roman","serif"'>Diferentes
      layouts para móviles y tablets.</span></li>
  <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Diferentes
      layouts para portrait y landscape</span></li>
  <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Pantalla
      de Logo inciial.</span></li>
 </ul>
 <li class=MsoNormal style='margin-top:12.0pt;margin-bottom:0cm;margin-bottom:
     .0001pt;line-height:normal'><i><span style='font-family:"Times New Roman","serif"'>Utilidad
     real de la aplicación.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>Más allá del aspecto estético y
de la información presentada la aplicación tiene una utilidad real.</span></p>

<ol start=3 type=1>
 <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Cualquier
     otra funcionalidad adicional que se le ocurra al alumno, siempre que la
     parte principal esté cubierta.</span></li>
 <ul type=circle>
  <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Scroll
      horizontal de la predicción en la vista de detalles en modo portrait.</span></li>
  <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Sincronización
      de la última ciudad visitada y de la interacción del usuario entre todas
      las actividades.</span></li>
  <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Sincronización
      en cambios de preferencias de usuario.</span></li>
  <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Leguajes
      español (excepto nombre del país) e inglés.</span></li>
 </ul>
</ol>

<p class=MsoNormal><span style='font-size:10.0pt;line-height:115%'>&nbsp;</span></p>

</div>

</body>

</html>
