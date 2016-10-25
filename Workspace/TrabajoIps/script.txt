DROP TABLE IF EXISTS SOCIO
DROP TABLE IF EXISTS RESERVA
DROP TABLE IF EXISTS INSTALACION
DROP TABLE IF EXISTS ACTIVIDAD
DROP TABLE IF EXISTS RECIBO

CREATE TABLE SOCIO (
	socioID varchar(32) NOT NULL,
	PRIMARY KEY socioID
);

CREATE TABLE INSTALACION (
	instalacionID integer NOT NULL,
	PRIMARY KEY instalacionID
);

CREATE TABLE RESERVA (
	reservaID integer NOT NULL,
	socioID varchar(32) NOT NULL,
	instalacionID integer NOT NULL,
	horaComienzo  TIMESTAMP NOT NULL,
	horaFinal TIMESTAMP NOT NULL,
	horaEntrada TIMESTAMP,
	horaSalida TIMESTAMP,
	modoPago varchar(32),
	precio integer,
	FOREIGN KEY (socioID) REFERENCES SOCIO(socioID),
	FOREIGN KEY (instalacionID) REFERENCES INSTALACION(instalacionID),
	PRIMARY KEY (reservaID, socioID, instalacionID)
);

CREATE TABLE RECIBO (
	reciboID integer NOT NULL,
	socioID varchar(32) NOT NULL,
	reservaID integer NOT NULL,
	instalacionID integer NOT NULL,
	importe integer,
	PRIMARY KEY (reciboID, socioID, reservaID, instalacionID)
);