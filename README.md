# JobLoggerTest

El proyecto fue desarrollado en Eclipse IDE for Enterprise Java Developers.

Version: 2018-12 (4.10.0)

Java versión 1.8

Para la prueba de inserción en base de datos se hizo la implementación de la Base de datos en memoria H2.

El jar de la base de datos(H2) se encuentra en la carpeta raíz del proyecto en caso de ser necesario que sea referenciada en el JAVA Build Path.

messages.properties

#JDBC Connection


JDBC_CONNECTION = jdbc:h2:~/test


JDBC_USER = user

JDBC_PASSWORD = password

JDBC_USER_NAME = sa

JDBC_PASSWORD_VALUE =


#File Configuration

FILE_FOLDER = log/

FILE_NAME = logFile

FILE_EXTENSION = .txt

#Enable/Disable logging (Enable = TRUE, Disable = FALSE)

E_D_MESSAGE = TRUE

E_D_WARNING = TRUE

E_D_ERROR = TRUE

#Message type

MESSAGE_TYPE_INFO = [MESSAGE]

MESSAGE_TYPE_WARNING = [WARNING]

MESSAGE_TYPE_ERROR = [ERROR]

