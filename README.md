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

<p class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Pr�ctica
asignatura Android Fundamental, Master Programaci�n de Apps, U-Tad.</span></p>

<p class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>La
pr�ctica consiste en realizar una aplicaci�n de meteorolog�a con los siguientes
requisitos:</span></p>

<ol start=1 type=1>
 <li class=MsoNormal style='line-height:normal'><i><span style='font-family:
     "Times New Roman","serif"'>Dise�o general de la interfaz de usuario con Actividades
     y Fragmentos, utilizando correctamente las vistas XML y los men�s.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><u><span
style='font-family:"Times New Roman","serif"'>Actividades:</span></u></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CCityListActivity.java:��������� Lista
de Ciudades de la BB.DD</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CCityDetailsActivity.java:������ Detalles
de la climatolog�a de una ciudad</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CBaseCityActivity.java:��������� Clase
base para CCityListActivity y CCityDetailsActivity</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CSettingsActivity.java:��������� Preferencias
de usuario</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CCitySearchActivity.java:������� B�squeda
de ciudades</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CMessageBoxActivity.java:������� Cuadros
de di�logo para mensajes al usuario</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-size:9.0pt;font-family:"Courier New"'>CSplashActivity.java:����������� Pantalla
inicial con el logo</span></p>

<ol start=2 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Diferenciaci�n
     de tablets / m�viles.</span></i></li>
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
style='font-family:"Times New Roman","serif"'>La aplicaci�n utiliza diferentes
estilos y temas para m�viles y tablets.</span></p>

<ol style='margin-top:0cm' start=4 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Uso de
     navegaci�n efectiva (lateral y ancestral especialmente)</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>Es posible navegar de manera
efectiva por toda la aplicaci�n.</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>La interacci�n del usuario se
mantiene durante toda la aplicaci�n.</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>La actividad
CCityListActivity.java contiene un drawer con una peque�a ayuda.</span></p>

<ol start=5 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Carga y
     parseo de datos mediante llamadas HTTP, en background.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>El servicio
CWeatherRetrieverService se encarga de actualizar peri�dicamente la informaci�n
de las ciudades en la BB.DD.</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>La frecuencia de actualizaci�n se
puede ajustar en una preferencia de usuario.</span></p>

<ol start=6 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Carga de
     datos sin interacci�n del usuario necesaria.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>El servicio se encarga de
actualizar la informaci�n de forma independiente de las ciudades de la BB.DD.:</span></p>

<p class=MsoListParagraphCxSpFirst style='margin-left:90.0pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>�<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Estado de la
climatolog�a actual.</span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:90.0pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>�<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>La predicci�n
para los pr�ximos 5 d�as</span></p>

<p class=MsoListParagraphCxSpLast style='margin-left:90.0pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>�<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Las im�genes
de los iconos.</span></p>

<ol style='margin-top:0cm' start=7 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Almacenamiento
     persistente de la informaci�n, a modo de cach�, en base de datos SQLite.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>La Aplicaci�n utiliza SQlite para
almacenar la informaci�n de las ciudades en la BB.DD. jmfweather.db.</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>Consta de tres tablas:</span></p>

<p class=MsoListParagraphCxSpFirst style='margin-top:0cm;margin-right:0cm;
margin-bottom:0cm;margin-left:88.8pt;margin-bottom:.0001pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>�<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>City:��������� Relaci�n
de ciudades que ha a�adido el usuario</span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:88.8pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>�<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Condition: Estado
actual de la climatolog�a</span></p>

<p class=MsoListParagraphCxSpLast style='margin-top:0cm;margin-right:0cm;
margin-bottom:0cm;margin-left:88.8pt;margin-bottom:.0001pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>�<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Forecast:��� Predicci�n
de los 5 pr�ximos d�as.</span></p>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>Adem�s se guardan en cach� los
ficheros con las im�genes de los iconos correspondiente a cada condici�n
meteorol�gica</span></p>

<ol start=8 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Gesti�n
     correcta de preferencias del usuario.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>Existen 3 preferencias para
m�viles y una m�s para tablets.</span></p>

<p class=MsoListParagraphCxSpFirst style='margin-left:90.0pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>�<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Posibilidad
de a�adir la localizaci�n actual de forma autom�tica al arrancar la aplicaci�n.</span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:90.0pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>�<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Frecuencia de
actualizaci�n de las condiciones meteorol�gicas de cada ciudad de la BB.DD.</span></p>

<p class=MsoListParagraphCxSpLast style='margin-left:90.0pt;text-indent:-18.0pt;
line-height:normal'><span style='font-family:Symbol'>�<span style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></span><span style='font-family:"Times New Roman","serif"'>Unidad de los
grados: Celsius o Fahrenheit.</span></p>

<ol style='margin-top:0cm' start=9 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Perfecto
     funcionamiento de la app.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>Salvo inesperados, la aplicaci�n
funciona correctamente.</span></p>

<p class=MsoNormal style='line-height:normal'><b><span style='font-family:"Times New Roman","serif"'>Extras:</span></b></p>

<ol style='margin-top:0cm' start=1 type=1>
 <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;line-height:
     normal'><i><span style='font-family:"Times New Roman","serif"'>Aspecto
     est�tico de la aplicaci�n.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>La aplicaci�n contiene los
siguientes asptectos est�ticos:</span></p>

<ol style='margin-top:0cm' start=1 type=1>
 <ul style='margin-top:0cm' type=circle>
  <li class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt;
      line-height:normal'><span style='font-family:"Times New Roman","serif"'>Diferentes
      layouts para m�viles y tablets.</span></li>
  <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Diferentes
      layouts para portrait y landscape</span></li>
  <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Pantalla
      de Logo inciial.</span></li>
 </ul>
 <li class=MsoNormal style='margin-top:12.0pt;margin-bottom:0cm;margin-bottom:
     .0001pt;line-height:normal'><i><span style='font-family:"Times New Roman","serif"'>Utilidad
     real de la aplicaci�n.</span></i></li>
</ol>

<p class=MsoNormal style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;
margin-left:36.0pt;margin-bottom:.0001pt;line-height:normal'><span
style='font-family:"Times New Roman","serif"'>M�s all� del aspecto est�tico y
de la informaci�n presentada la aplicaci�n tiene una utilidad real.</span></p>

<ol start=3 type=1>
 <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Cualquier
     otra funcionalidad adicional que se le ocurra al alumno, siempre que la
     parte principal est� cubierta.</span></li>
 <ul type=circle>
  <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Scroll
      horizontal de la predicci�n en la vista de detalles en modo portrait.</span></li>
  <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Sincronizaci�n
      de la �ltima ciudad visitada y de la interacci�n del usuario entre todas
      las actividades.</span></li>
  <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Sincronizaci�n
      en cambios de preferencias de usuario.</span></li>
  <li class=MsoNormal style='line-height:normal'><span style='font-family:"Times New Roman","serif"'>Leguajes
      espa�ol (excepto nombre del pa�s) e ingl�s.</span></li>
 </ul>
</ol>

<p class=MsoNormal><span style='font-size:10.0pt;line-height:115%'>&nbsp;</span></p>

</div>

</body>

</html>
