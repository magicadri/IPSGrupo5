DROP TABLE IF EXISTS SOCIO;
DROP TABLE IF EXISTS RESERVA;
DROP TABLE IF EXISTS INSTALACION;
DROP TABLE IF EXISTS RECIBO;

CREATE TABLE socio (
	socioID varchar(32) NOT NULL,
	PRIMARY KEY (socioID)
);

CREATE TABLE instalacion (
	instalacionID integer NOT NULL,
	instalacion_nombre varchar(32) NOT NULL,
	PRIMARY KEY (instalacionID)
);

CREATE TABLE reserva (
	socioID varchar(32) NOT NULL,
	instalacionID integer NOT NULL,
	reservaID integer NOT NULL,
	horaComienzo  TIMESTAMP NOT NULL,
	horaFinal TIMESTAMP NOT NULL,
	horaEntrada TIMESTAMP,
	horaSalida TIMESTAMP,
	modoPago varchar(32),
	pagado BOOLEAN,
	precio integer,
	CONSTRAINT FK_SOCIO FOREIGN KEY (socioID) REFERENCES SOCIO(socioID),
	CONSTRAINT FK_INSTALACION FOREIGN KEY (instalacionID) REFERENCES INSTALACION(instalacionID),
        CONSTRAINT chk_pago CHECK (modoPago IN ('Efectivo', 'Cuota')),
	PRIMARY KEY (socioID, instalacionID, horaComienzo)
);

CREATE TABLE recibo (
    reciboID INTEGER NOT NULL,
    socioID VARCHAR(32) NOT NULL,
    importe INTEGER NOT NULL,
    fecha TIMESTAMP NOT NULL,
    PRIMARY KEY (reciboID)
);


