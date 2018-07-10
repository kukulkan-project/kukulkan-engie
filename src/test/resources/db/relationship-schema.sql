CREATE SEQUENCE IF NOT EXISTS bitacora_id_seq START WITH 1 INCREMENT BY 1;
/*==============================================================*/
/*Table: tipo_bitacora                                          */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS tipo_bitacora (
  id                 	INT,
  codigo 				VARCHAR(30) NOT NULL,
  description  			VARCHAR(25) NULL DEFAULT NULL,
  constraint pk_tipo_bitacora primary key (id)
);
  
/*==============================================================*/
/*Table: bitacora                                         		*/
/*==============================================================*/
CREATE TABLE IF NOT EXISTS bitacora (
  id                 	SERIAL DEFAULT nextval('bitacora_id_seq'),
  tipo_bitacora_id  	INT NOT NULL,
  description  			VARCHAR(25) NULL DEFAULT NULL,
  constraint pk_bitacora primary key (id),
  constraint fk_tipo_bitacora_id foreign key (tipo_bitacora_id) references tipo_bitacora (id)
);