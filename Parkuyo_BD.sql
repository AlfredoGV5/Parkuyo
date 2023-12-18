create database Parkuyo 
use Parkuyo

create table usuario(
	usuario nvarchar(10),
	contrase�a nvarchar(10)
)

insert into usuario values('User1','12345')
 
create table cliente_Due�oCuyo(
	id_cliente int IDENTITY(1,1),
	id_cuyo int,
	nombre_cliente nvarchar(30),
	celular_cliente nvarchar(12),
	correo_cliente nvarchar(50),
	direccion_cliente nvarchar(80),
	PRIMARY KEY (id_cliente),
	FOREIGN KEY (id_cuyo) REFERENCES cuyo(id_cuyo)
)

create table cuyo(
	id_cuyo int IDENTITY(1,1),
	nombre nvarchar(40),
	edad int,
	especificaciones nvarchar(200),
	PRIMARY KEY (id_cuyo)
)

INSERT INTO cuyo VALUES('Felicio',2,'No le gusta mojarse')
INSERT INTO cliente_Due�oCuyo values (1,'Alfredo','55345345','jal@gmail.com','Paris 124')


create table empleados(
	id_empleado int IDENTITY(1,1),
	nombre nvarchar(50),
	telefono nvarchar(12),
	direccion nvarchar(50),
	sueldo float,
	fecha_nacimiento date,
	sexo nvarchar(2),
	PRIMARY KEY (id_empleado)
)



/*PROCEDURES*/

CREATE PROCEDURE InsertarEmpleado
    @nombre NVARCHAR(255),
    @telefono NVARCHAR(20),
    @direccion NVARCHAR(255),
    @sueldo DECIMAL(10, 2),
    @fecha_nacimiento DATE,
    @sexo CHAR(1)
AS
BEGIN
    INSERT INTO dbo.empleados (nombre, telefono, direccion, sueldo, fecha_nacimiento, sexo)
    VALUES (@nombre, @telefono, @direccion, @sueldo, @fecha_nacimiento, @sexo);
END;

-------------------------------------------

CREATE PROCEDURE InsertarCuyo
    @nombre NVARCHAR(25),
    @edad int,
    @especificaciones NVARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.cuyo(nombre, edad, especificaciones)
    VALUES (@nombre, @edad, @especificaciones);
    SELECT SCOPE_IDENTITY() AS IdGenerado;
END;

-------------------------


CREATE PROCEDURE actualizarEmpleado
    @nombreActual NVARCHAR(255),
    @fechaNacimientoActual DATE,
    @nombreNuevo NVARCHAR(255),
    @telefono NVARCHAR(20),
    @direccion NVARCHAR(255),
    @sueldo DECIMAL(10, 2),
    @sexo CHAR(1),
	@fechaNacimientoNueva DATE
AS
BEGIN
    -- Actualizando la informaci�n del empleado
    UPDATE empleados
    SET 
        nombre = @nombreNuevo,
        telefono = @telefono,
        direccion = @direccion,
        sueldo = @sueldo,
        sexo = @sexo,
		fecha_nacimiento=@fechaNacimientoNueva
    WHERE 
        nombre = @nombreActual AND 
        fecha_nacimiento = @fechaNacimientoActual;
END;
GO

CREATE PROCEDURE borrarEmpleado
    @id_empleado int
AS
BEGIN
    -- Borrando la informaci�n del empleado
    DELETE from empleados where id_empleado=@id_empleado
END;
GO


CREATE PROCEDURE ObtenerCuyosYClientes
AS
BEGIN
    SELECT c.id_cuyo, c.nombre, c.edad, c.especificaciones,
           cd.id_cliente, cd.nombre_cliente, cd.celular_cliente, cd.correo_cliente, cd.direccion_cliente
    FROM cuyo c
    LEFT JOIN cliente_Due�oCuyo cd ON c.id_cuyo = cd.id_cuyo;
END;

CALL  ObtenerCuyosYClientes

CREATE PROCEDURE InsertarDue�oCuyo
	@id_cuyo int,
	@nombre_cliente nvarchar(30),
	@celular_cliente nvarchar(12),
	@correo_cliente nvarchar(50),
	@direccion_cliente nvarchar(80)
AS
BEGIN
INSERT INTO cliente_Due�oCuyo (id_cuyo, nombre_cliente, celular_cliente, correo_cliente, direccion_cliente)
    VALUES (@id_cuyo, @nombre_cliente, @celular_cliente, @correo_cliente, @direccion_cliente);
END
GO


SELECT * FROM cuyo
DELETE FROM cuyo where id_cuyo between 3 and 50



CREATE PROCEDURE ContarEmpleados
AS
BEGIN
    SELECT COUNT(*) AS TotalEmpleados FROM empleados;
END;


CREATE PROCEDURE ContarCuyos
AS
BEGIN
    SELECT COUNT(*) AS TotalCuyos FROM cuyo;
END;










