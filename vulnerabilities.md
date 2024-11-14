
   CERTIFICATE ANALYSIS
* Aplicación vulnerable a la vulnerabilidad Janus 
  warning
  La aplicación está firmada con el esquema de firma v1, lo que la hace vulnerable a la vulnerabilidad Janus en Android 5.0-8.0, 
  si sólo está firmada con el esquema de firma v1. Las aplicaciones que se ejecutan en Android 5.0-7.0 firmadas con el esquema v1 y v2/v3 también son vulnerables.

* Aplicación firmada con certificado de depuración
  high
  Aplicación firmada con un certificado de depuración. La aplicación de producción no debe enviarse con un certificado de depuración.
 
  MANIFEST ANALYSIS
* La aplicación se puede instalar en una versión de Android vulnerable upatched Android 5.0-5.0.2, [minSdk=21]
  high
  Esta aplicación se puede instalar en una versión antigua de android que tiene múltiples vulnerabilidades sin corregir.
  Estos dispositivos no recibirán actualizaciones de seguridad razonables de Google. Soporta una versión de Android => 10, API 29 para recibir actualizaciones de seguridad razonables.
* Depuración activada para la aplicación 
  hihg
  Se ha habilitado la depuración en la aplicación, lo que facilita a los ingenieros inversos la conexión a un depurador. Esto permite volcar un seguimiento de pila y acceder a clases de ayuda de depuración.
* Se puede hacer una copia de seguridad de los datos de la aplicación [android:allowBackup=true]
  waringh
  Esta bandera permite a cualquier persona para copia de seguridad de sus datos de aplicación a través de adb. Permite a los usuarios que han habilitado la depuración USB copiar los datos de la aplicación fuera del dispositivo.
* Broadcast Receiver (androidx.profileinstaller.ProfileInstallReceiver) está protegido por un permiso, pero debe comprobarse el nivel de protección del permiso. Permiso: android.permission.DUMP [android:exported=true]
  waringh
  Se ha descubierto que un receptor de difusión se comparte con otras aplicaciones del dispositivo, por lo que es accesible para cualquier otra aplicación del dispositivo. Está protegido por un permiso que no está definido en la aplicación analizada.
  Por lo tanto, se debe comprobar el nivel de protección del permiso donde está definido. Si está definido como normal o peligroso,
  una aplicación maliciosa puede solicitar y obtener el permiso e interactuar con el componente. Si está definido como firma, sólo las aplicaciones firmadas con el mismo certificado pueden obtener el permiso.
