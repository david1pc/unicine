insert into cliente(cedula, correo, estado, imagen_perfil, primer_nombre, primer_apellido, password) values ("1852151","juan@email.com",0,"img","juan","manuel","51321354");
insert into cliente(cedula, correo, estado, imagen_perfil, primer_nombre, primer_apellido, password)values ("1051211","manuel@email.com",1,"img","manuel","pedro","65151321");
insert into cliente(cedula, correo, estado, imagen_perfil, primer_nombre, primer_apellido, password) values ("2151541","pedro@email.com",0,"img","pedro","pablo","78978988");
insert into cliente(cedula, correo, estado, imagen_perfil, primer_nombre, primer_apellido, password) values ("1224544","pablo@email.com",1,"img","pablo","garcia","5321222");
insert into cliente(cedula, correo, estado, imagen_perfil, primer_nombre, primer_apellido, password) values ("9522222","emilio@email.com",0,"img","emilio","jimenez","1111000");

insert into cliente_telefonos values (1,"651651111");
insert into cliente_telefonos values (2,"7874152");
insert into cliente_telefonos values (3,"6532181");
insert into cliente_telefonos values (4,"321645444");
insert into cliente_telefonos values (5,"321645444");

insert into administrador(correo, password, primer_nombre, primer_apellido) values ("admin1@email.com", "125ads","Manuel","Ramirez");
insert into administrador(correo, password, primer_nombre, primer_apellido) values ("admin2@email.com","25ads","David","Fernando");
insert into administrador(correo, password, primer_nombre, primer_apellido) values ("admin3@email.com","2ads","Sebastian","juan");
insert into administrador(correo, password, primer_nombre, primer_apellido) values ("admin4@email.com","5ads","Laura","Restrepo");
insert into administrador(correo, password, primer_nombre, primer_apellido) values ("admin5@email.com","1ads","Alison","Restrepo");

insert into administrador_teatro(correo, password, primer_nombre, primer_apellido) values ("admin6@email.com", "125ads","Manuela","Ramirez");
insert into administrador_teatro(correo, password, primer_nombre, primer_apellido) values ("admin7@email.com","25ads","Kevin","Fernando");
insert into administrador_teatro(correo, password, primer_nombre, primer_apellido) values ("admin8@email.com","2ads","Laura","juan");
insert into administrador_teatro(correo, password, primer_nombre, primer_apellido) values ("admin9@email.com","5ads","Hernan","Restrepo");
insert into administrador_teatro(correo, password, primer_nombre, primer_apellido) values ("admin10@email.com","1ads","Pablo","Restrepo");

insert into pelicula(estado, genero, nombre, reparto, sinopsis, url_img, url_trailer) values(1,"TERROR","El Conjuro","actores","sinopsis","img","trailer");
insert into pelicula(estado, genero, nombre, reparto, sinopsis, url_img, url_trailer) values(1,"ACCION", "El resplandor","actores","sinopsis","img","trailer");
insert into pelicula(estado, genero, nombre, reparto, sinopsis, url_img, url_trailer) values(1,"CIENCIA_FICCION","Tenet","actores","sinopsis","img","trailer");
insert into pelicula(estado, genero, nombre, reparto, sinopsis, url_img, url_trailer) values(1,"CIENCIA_FICCION","Spiderman 3","actores","sinopsis","img","trailer");
insert into pelicula(estado, genero, nombre, reparto, sinopsis, url_img, url_trailer) values(1,"CIENCIA_FICCION","Batman: El caballero de la noche","actores","sinopsis","img","trailer");

insert into ciudad(nombre) values ("Armenia");
insert into ciudad(nombre) values ("Cali");
insert into ciudad(nombre) values ("Bogota");
insert into ciudad(nombre) values ("Manizalez");
insert into ciudad(nombre) values ("Pereira");


insert into cupon(descripcion, descuento, criterio, vencimiento) values("Viernes festivo", 10, "Solo para compras de mas de 1 entradas", "2022/11/10");
insert into cupon(descripcion, descuento, criterio, vencimiento) values("Miercoles de cine", 8, "Solo para compras de mas de 1 entradas", "2022/11/11");
insert into cupon(descripcion, descuento, criterio, vencimiento) values("Combo de crispetas y pepsi 1L", 5, "Solo para compras de mas de 1 entradas", "2022/11/12");
insert into cupon(descripcion, descuento, criterio, vencimiento) values("Combo de crispetas y cocacola 1L", 4, "Solo para compras de mas de 1 entradas", "2022/11/13");
insert into cupon(descripcion, descuento, criterio, vencimiento) values("Combo de crispetas", 10, "Solo para compras de mas de 2 entradas", "2022/11/14");


insert into teatro(direccion, telefono, administrador_teatro_codigo, ciudad_codigo) values("CL 30#15-20", "7525964", 1, 1);
insert into teatro(direccion, telefono, administrador_teatro_codigo, ciudad_codigo) values("CI 20#10-15", "7125813", 2, 2);
insert into teatro(direccion, telefono, administrador_teatro_codigo, ciudad_codigo) values("CL 25#11-15", "7657244", 3, 3);
insert into teatro(direccion, telefono, administrador_teatro_codigo, ciudad_codigo) values("CL 10#5-8", "7116723", 4, 4);
insert into teatro(direccion, telefono, administrador_teatro_codigo, ciudad_codigo) values("CI 11#5-10", "7428934", 5, 5);

insert into distribucion_sillas(esquema, total_sillas, filas, columnas) values("V1", 140, 12, 12);
insert into distribucion_sillas(esquema, total_sillas, filas, columnas) values("V2", 115, 11, 11);
insert into distribucion_sillas(esquema, total_sillas, filas, columnas) values("V1", 81, 9, 9);
insert into distribucion_sillas(esquema, total_sillas, filas, columnas) values("V2", 108, 9, 12);
insert into distribucion_sillas(esquema, total_sillas, filas, columnas) values("V1", 130, 9, 15);

insert into sala(nombre, distribucion_sillas_codigo, teatro_codigo) values("SALA 1", 1, 1);
insert into sala(nombre, distribucion_sillas_codigo, teatro_codigo) values("SALA 2", 2, 1);
insert into sala(nombre, distribucion_sillas_codigo, teatro_codigo) values("SALA 1", 3, 2);
insert into sala(nombre, distribucion_sillas_codigo, teatro_codigo) values("SALA 2", 4, 2);
insert into sala(nombre, distribucion_sillas_codigo, teatro_codigo) values("SALA 1", 5, 3);

insert into horario(dia, hora, fecha_inicio, fecha_fin) values("L M MI J", "13:00", "2022-10-22", "2022-10-24");
insert into horario(dia, hora, fecha_inicio, fecha_fin) values("S D", "14:00", "2022-11-10", "2022-12-2");
insert into horario(dia, hora, fecha_inicio, fecha_fin) values("L M MI J V", "15:00", "2022-10-15", "2022-10-25");
insert into horario(dia, hora, fecha_inicio, fecha_fin) values("L M MI J V", "16:00", "2022-12-8", "2022-12-13");
insert into horario(dia, hora, fecha_inicio, fecha_fin) values("L M MI J V", "9:00", "2022-12-15", "2022-12-30");

insert into funcion(precio, horario_codigo, pelicula_codigo, sala_codigo) values(15500, 1, 1, 1);
insert into funcion(precio, horario_codigo, pelicula_codigo, sala_codigo) values(20000, 2, 2, 2);
insert into funcion(precio, horario_codigo, pelicula_codigo, sala_codigo) values(15000, 3, 3, 3);
insert into funcion(precio, horario_codigo, pelicula_codigo, sala_codigo) values(10000, 4, 4, 4);
insert into funcion(precio, horario_codigo, pelicula_codigo, sala_codigo) values(8000, 5, 5, 5);

insert into confiteria(nombre, descripcion, precio, imagen) values("Crispetas caramelo", "Tamaño normal", 12500, "assf");
insert into confiteria(nombre, descripcion, precio, imagen) values("Crispetas", "Tamaño normal", 9000, "assf");
insert into confiteria(nombre, descripcion, precio, imagen) values("Perro caliente", "Tamaño normal", 15000, "assf");
insert into confiteria(nombre, descripcion, precio, imagen) values("Coca cola", "1L", 5000, "assf");
insert into confiteria(nombre, descripcion, precio, imagen) values("Pepsi", "1L", 5000, "assf");


insert into cupon_cliente(estado, cliente_codigo, cupon_codigo) values(1, 1, 1);
insert into cupon_cliente(estado, cliente_codigo, cupon_codigo) values(1, 2, 2);
insert into cupon_cliente(estado, cliente_codigo, cupon_codigo) values(1, 3, 1);
insert into cupon_cliente(estado, cliente_codigo, cupon_codigo) values(1, 4, 2);
insert into cupon_cliente(estado, cliente_codigo, cupon_codigo) values(1, 5, 1);

insert into compra(medio_pago, fecha_compra, valor_total, cliente_codigo, cupon_cliente_codigo, funcion_codigo) values("EFECTIVO", "2022-10-20", 50400, 1, 1, 1);
insert into compra(medio_pago, fecha_compra, valor_total, cliente_codigo, cupon_cliente_codigo, funcion_codigo) values("MASTERCARD", "2022-10-21", 45080, 2, 2, 2);
insert into compra(medio_pago, fecha_compra, valor_total, cliente_codigo, cupon_cliente_codigo, funcion_codigo) values("DAVIPLATA", "2022-10-22", 54000, 3, 1, 3);
insert into compra(medio_pago, fecha_compra, valor_total, cliente_codigo, cupon_cliente_codigo, funcion_codigo) values("NEQUI", "2022-10-23", 18400, 4, 2, 4);
insert into compra(medio_pago, fecha_compra, valor_total, cliente_codigo, cupon_cliente_codigo, funcion_codigo) values("NEQUI", "2022-10-24", 11700, 5, 1, 5);

insert into entrada(columna, fila, precio, compra_codigo) values("3", "3", 15500, 1);
insert into entrada(columna, fila, precio, compra_codigo) values("3", "4", 15500, 1);
insert into entrada(columna, fila, precio, compra_codigo) values("3", "5", 20000, 2);
insert into entrada(columna, fila, precio, compra_codigo) values("3", "6", 20000, 2);
insert into entrada(columna, fila, precio, compra_codigo) values("3", "7", 15000, 3);
insert into entrada(columna, fila, precio, compra_codigo) values("3", "8", 10000, 4);
insert into entrada(columna, fila, precio, compra_codigo) values("3", "9", 8000, 5);

insert into compra_confiteria(precio, cantidad, compra_codigo, confiteria_codigo) values(25000, 2, 1, 1);
insert into compra_confiteria(precio, cantidad, compra_codigo, confiteria_codigo) values(9000, 1, 2, 2);
insert into compra_confiteria(precio, cantidad, compra_codigo, confiteria_codigo) values(45000, 3, 3, 3);
insert into compra_confiteria(precio, cantidad, compra_codigo, confiteria_codigo) values(10000, 2, 4, 4);
insert into compra_confiteria(precio, cantidad, compra_codigo, confiteria_codigo) values(5000, 1, 5, 5);