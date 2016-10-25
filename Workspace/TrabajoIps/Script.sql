DROP TABLE IF EXISTS socio
DROP TABLE IF EXISTS reserva
DROP TABLE IF EXISTS instalacion
DROP TABLE IF EXISTS actividad
DROP TABLE IF EXISTS cuota
DROP TABLE IF EXISTS entrada_cuota

CREATE TABLE socio (
	socioID varchar(32) NOT NULL,
	PRIMARY KEY socioID
);

CREATE TABLE instalacion (
	instalacionID integer NOT NULL,
	PRIMARY KEY instalacionID
);

CREATE TABLE reserva (
	socioID varchar(32) NOT NULL,
	instalacionID integer NOT NULL,
	horaComienzo  TIMESTAMP NOT NULL,
	horaFinal TIMESTAMP NOT NULL,
	horaEntrada TIMESTAMP,
	horaSalida TIMESTAMP,
	modoPago varchar(32),
	pagado BOOLEAN,
	precio integer,
	FOREIGN KEY (socioID) REFERENCES SOCIO(socioID),
	FOREIGN KEY (instalacionID) REFERENCES INSTALACION(instalacionID),
	PRIMARY KEY (socioID, instalacionID, horaComienzo)
	CONSTRAINT chk_modoPago (modoPago IN ("efectivo", "cuota"))
);

CREATE TABLE cuota (
    cuotaID INTEGER NOT NULL,
    socioID VARCHAR(32) NOT NULL,
    mes DATE NOT NULL,

    PRIMARY KEY (cuotaID)
)

CREATE TABLE entrada_cuota(
    cuotaID INTEGER NOT NULL,
    importe integer,
    concepto VARCHAR(64)
)

/*
CREATE TABLE RECIBO (
	reciboID integer NOT NULL,
	socioID varchar(32) NOT NULL,
	reservaID integer NOT NULL,
	instalacionID integer NOT NULL,
	importe integer,
	PRIMARY KEY (reciboID, socioID, reservaID, instalacionID)
);
*/