insert into cliente(codigo, cedula, correo, estado, imagen_perfil, primer_nombre, primer_apellido, password) values (1, "1852151","juan@email.com",0,"img","juan","manuel","51321354");
insert into cliente(codigo, cedula, correo, estado, imagen_perfil, primer_nombre, primer_apellido, password)values (2, "1051211","manuel@email.com",1,"img","manuel","pedro","65151321");
insert into cliente(codigo, cedula, correo, estado, imagen_perfil, primer_nombre, primer_apellido, password) values (3, "2151541","pedro@email.com",0,"img","pedro","pablo","78978988");
insert into cliente(codigo, cedula, correo, estado, imagen_perfil, primer_nombre, primer_apellido, password) values (4, "1224544","pablo@email.com",1,"img","pablo","garcia","5321222");
insert into cliente(codigo, cedula, correo, estado, imagen_perfil, primer_nombre, primer_apellido, password) values (5, "9522222","emilio@email.com",0,"img","emilio","jimenez","1111000");

insert into cliente_telefonos values (1,"651651111");
insert into cliente_telefonos values (2,"7874152");
insert into cliente_telefonos values (3,"6532181");
insert into cliente_telefonos values (4,"321645444");
insert into cliente_telefonos values (5,"321645444");

insert into administrador(codigo, correo, password, primer_nombre, primer_apellido) values (1, "admin1@email.com", "125ads","Manuel","Ramirez");
insert into administrador(codigo, correo, password, primer_nombre, primer_apellido) values (2, "admin2@email.com","25ads","David","Fernando");
insert into administrador(codigo, correo, password, primer_nombre, primer_apellido) values (3, "admin3@email.com","2ads","Sebastian","juan");
insert into administrador(codigo, correo, password, primer_nombre, primer_apellido) values (4, "admin4@email.com","5ads","Laura","Restrepo");
insert into administrador(codigo, correo, password, primer_nombre, primer_apellido) values (5, "admin5@email.com","1ads","Alison","Restrepo");

insert into administrador_teatro(codigo, correo, password, primer_nombre, primer_apellido) values (1, "admin6@email.com", "125ads","Manuela","Ramirez");
insert into administrador_teatro(codigo, correo, password, primer_nombre, primer_apellido) values (2, "admin7@email.com","25ads","Kevin","Fernando");
insert into administrador_teatro(codigo, correo, password, primer_nombre, primer_apellido) values (3, "admin8@email.com","2ads","Laura","juan");
insert into administrador_teatro(codigo, correo, password, primer_nombre, primer_apellido) values (4, "admin9@email.com","5ads","Hernan","Restrepo");
insert into administrador_teatro(codigo, correo, password, primer_nombre, primer_apellido) values (5, "admin10@email.com","1ads","Pablo","Restrepo");

insert into pelicula(codigo, estado, genero, nombre, reparto, sinopsis, url_img, url_trailer) values(1, 1,"TERROR","El Conjuro","actores","sinopsis","img","trailer");
insert into pelicula(codigo, estado, genero, nombre, reparto, sinopsis, url_img, url_trailer) values(2, 1,"ACCION", "El resplandor","actores","sinopsis","img","trailer");
insert into pelicula(codigo, estado, genero, nombre, reparto, sinopsis, url_img, url_trailer) values(3, 1,"CIENCIA_FICCION","Tenet","actores","sinopsis","img","trailer");
insert into pelicula(codigo, estado, genero, nombre, reparto, sinopsis, url_img, url_trailer) values(4, 1,"CIENCIA_FICCION","Spiderman 3","actores","sinopsis","img","trailer");
insert into pelicula(codigo, estado, genero, nombre, reparto, sinopsis, url_img, url_trailer) values(5, 1,"CIENCIA_FICCION","Batman: El caballero de la noche","actores","sinopsis","img","trailer");

insert into ciudad(codigo, nombre) values (1, "Armenia");
insert into ciudad(codigo, nombre) values (2, "Cali");
insert into ciudad(codigo, nombre) values (3, "Bogota");
insert into ciudad(codigo, nombre) values (4, "Manizalez");
insert into ciudad(codigo, nombre) values (5, "Pereira");


insert into cupon(codigo, descripcion, descuento, criterio, vencimiento) values(1, "Viernes festivo", 10, "Solo para compras de mas de 1 entradas", "2022/11/10");
insert into cupon(codigo, descripcion, descuento, criterio, vencimiento) values(2, "Miercoles de cine", 8, "Solo para compras de mas de 1 entradas", "2022/11/11");
insert into cupon(codigo, descripcion, descuento, criterio, vencimiento) values(3, "Combo de crispetas y pepsi 1L", 5, "Solo para compras de mas de 1 entradas", "2022/11/12");
insert into cupon(codigo, descripcion, descuento, criterio, vencimiento) values(4, "Combo de crispetas y cocacola 1L", 4, "Solo para compras de mas de 1 entradas", "2022/11/13");
insert into cupon(codigo, descripcion, descuento, criterio, vencimiento) values(5, "Combo de crispetas", 10, "Solo para compras de mas de 2 entradas", "2022/11/14");


insert into teatro(codigo, direccion, telefono, administrador_teatro_codigo, ciudad_codigo) values(1, "CL 30#15-20", "7525964", 1, 1);
insert into teatro(codigo, direccion, telefono, administrador_teatro_codigo, ciudad_codigo) values(2, "CI 20#10-15", "7125813", 2, 2);
insert into teatro(codigo, direccion, telefono, administrador_teatro_codigo, ciudad_codigo) values(3, "CL 25#11-15", "7657244", 3, 3);
insert into teatro(codigo, direccion, telefono, administrador_teatro_codigo, ciudad_codigo) values(4, "CL 10#5-8", "7116723", 4, 4);
insert into teatro(codigo, direccion, telefono, administrador_teatro_codigo, ciudad_codigo) values(5, "CI 11#5-10", "7428934", 5, 5);

insert into distribucion_sillas(codigo, esquema, total_sillas, filas, columnas) values(1, "V1", 140, 12, 12);
insert into distribucion_sillas(codigo, esquema, total_sillas, filas, columnas) values(2, "V2", 115, 11, 11);
insert into distribucion_sillas(codigo, esquema, total_sillas, filas, columnas) values(3, "V1", 81, 9, 9);
insert into distribucion_sillas(codigo, esquema, total_sillas, filas, columnas) values(4, "V2", 108, 9, 12);
insert into distribucion_sillas(codigo, esquema, total_sillas, filas, columnas) values(5, "V1", 130, 9, 15);

insert into sala(codigo, nombre, distribucion_sillas_codigo, teatro_codigo) values(1, "SALA 1", 1, 1);
insert into sala(codigo, nombre, distribucion_sillas_codigo, teatro_codigo) values(2, "SALA 2", 2, 1);
insert into sala(codigo, nombre, distribucion_sillas_codigo, teatro_codigo) values(3, "SALA 1", 3, 2);
insert into sala(codigo, nombre, distribucion_sillas_codigo, teatro_codigo) values(4, "SALA 2", 4, 2);
insert into sala(codigo, nombre, distribucion_sillas_codigo, teatro_codigo) values(5, "SALA 1", 5, 3);

insert into horario(codigo, dia, hora, fecha_inicio, fecha_fin) values(1, "L M MI J", "13:00", "2022-10-22", "2022-10-24");
insert into horario(codigo, dia, hora, fecha_inicio, fecha_fin) values(2, "S D", "14:00", "2022-11-10", "2022-12-2");
insert into horario(codigo, dia, hora, fecha_inicio, fecha_fin) values(3, "L M MI J V", "15:00", "2022-10-15", "2022-10-25");
insert into horario(codigo, dia, hora, fecha_inicio, fecha_fin) values(4, "L M MI J V", "16:00", "2022-12-8", "2022-12-13");
insert into horario(codigo, dia, hora, fecha_inicio, fecha_fin) values(5, "L M MI J V", "9:00", "2022-12-15", "2022-12-30");

insert into funcion(codigo, precio, horario_codigo, pelicula_codigo, sala_codigo) values(1, 15500, 1, 1, 1);
insert into funcion(codigo, precio, horario_codigo, pelicula_codigo, sala_codigo) values(2, 20000, 2, 2, 2);
insert into funcion(codigo, precio, horario_codigo, pelicula_codigo, sala_codigo) values(3, 15000, 3, 3, 3);
insert into funcion(codigo, precio, horario_codigo, pelicula_codigo, sala_codigo) values(4, 10000, 4, 4, 4);
insert into funcion(codigo, precio, horario_codigo, pelicula_codigo, sala_codigo) values(5, 8000, 5, 5, 5);

insert into confiteria(codigo, nombre, descripcion, precio, imagen) values(1, "Crispetas caramelo", "Tamaño normal", 12500, "assf");
insert into confiteria(codigo, nombre, descripcion, precio, imagen) values(2, "Crispetas", "Tamaño normal", 9000, "assf");
insert into confiteria(codigo, nombre, descripcion, precio, imagen) values(3, "Perro caliente", "Tamaño normal", 15000, "assf");
insert into confiteria(codigo, nombre, descripcion, precio, imagen) values(4, "Coca cola", "1L", 5000, "assf");
insert into confiteria(codigo, nombre, descripcion, precio, imagen) values(5, "Pepsi", "1L", 5000, "assf");


insert into cupon_cliente(codigo, estado, cliente_codigo, cupon_codigo) values(1, 1, 1, 1);
insert into cupon_cliente(codigo, estado, cliente_codigo, cupon_codigo) values(2, 1, 2, 2);
insert into cupon_cliente(codigo, estado, cliente_codigo, cupon_codigo) values(3, 1, 3, 1);
insert into cupon_cliente(codigo, estado, cliente_codigo, cupon_codigo) values(4, 1, 4, 2);
insert into cupon_cliente(codigo, estado, cliente_codigo, cupon_codigo) values(5, 1, 5, 1);

insert into compra(codigo, medio_pago, fecha_compra, valor_total, cliente_codigo, cupon_cliente_codigo, funcion_codigo) values(1, "EFECTIVO", "2022-10-20", 68400, 1, 1, 1);
insert into compra(codigo, medio_pago, fecha_compra, valor_total, cliente_codigo, cupon_cliente_codigo, funcion_codigo) values(2, "MASTERCARD", "2022-10-21", 54080, 2, 2, 2);
insert into compra(codigo, medio_pago, fecha_compra, valor_total, cliente_codigo, cupon_cliente_codigo, funcion_codigo) values(3, "DAVIPLATA", "2022-10-22", 94000, 3, 1, 3);
insert into compra(codigo, medio_pago, fecha_compra, valor_total, cliente_codigo, cupon_cliente_codigo, funcion_codigo) values(4, "NEQUI", "2022-10-23", 33400, 4, 2, 4);
insert into compra(codigo, medio_pago, fecha_compra, valor_total, cliente_codigo, cupon_cliente_codigo, funcion_codigo) values(5, "NEQUI", "2022-10-24", 36700, 5, 1, 5);

insert into entrada(codigo, columna, fila, precio, compra_codigo) values(1, "3", "3", 15500, 1);
insert into entrada(codigo, columna, fila, precio, compra_codigo) values(2, "3", "4", 15500, 1);
insert into entrada(codigo, columna, fila, precio, compra_codigo) values(3, "3", "5", 20000, 2);
insert into entrada(codigo, columna, fila, precio, compra_codigo) values(4, "3", "6", 20000, 2);
insert into entrada(codigo, columna, fila, precio, compra_codigo) values(5, "3", "7", 15000, 3);
insert into entrada(codigo, columna, fila, precio, compra_codigo) values(6, "3", "8", 10000, 4);
insert into entrada(codigo, columna, fila, precio, compra_codigo) values(7, "3", "9", 8000, 5);

insert into compra_confiteria(codigo, precio, cantidad, compra_codigo, confiteria_codigo) values(1, 25000, 2, 1, 1);
insert into compra_confiteria(codigo, precio, cantidad, compra_codigo, confiteria_codigo) values(2, 9000, 1, 2, 2);
insert into compra_confiteria(codigo, precio, cantidad, compra_codigo, confiteria_codigo) values(3, 45000, 3, 3, 3);
insert into compra_confiteria(codigo, precio, cantidad, compra_codigo, confiteria_codigo) values(4, 10000, 2, 4, 4);
insert into compra_confiteria(codigo, precio, cantidad, compra_codigo, confiteria_codigo) values(5, 5000, 1, 5, 5);

insert into combo(codigo, nombre, precio, descripcion, imagen) values(1, "Combo papas francesas y hamburguesa doble queso", 9000, "Desc", "/imagen");
insert into combo(codigo, nombre, precio, descripcion, imagen) values(2, "Combo papas francesas y perro", 9000, "Desc", "/imagen");
insert into combo(codigo, nombre, precio, descripcion, imagen) values(3, "Combo palomitas y pepsi", 20000, "Palomitas XL y pepsi 2L", "/imagen");
insert into combo(codigo, nombre, precio, descripcion, imagen) values(4, "Combo palomitas y coca cola", 15000, "Palomitas X y pepsi 2L", "/imagen");
insert into combo(codigo, nombre, precio, descripcion, imagen) values(5, "Combo palomitas dulces y pepsi", 25000, "Palomitas XL y pepsi 2L", "/imagen");

insert into compra_combo(codigo, cantidad, precio, compra_codigo, combo_codigo) values(1, 2, 18000, 1, 1);
insert into compra_combo(codigo, cantidad, precio, compra_codigo, combo_codigo) values(2, 1, 9000, 2, 2);
insert into compra_combo(codigo, cantidad, precio, compra_codigo, combo_codigo) values(3, 2, 40000, 3, 3);
insert into compra_combo(codigo, cantidad, precio, compra_codigo, combo_codigo) values(4, 1, 15000, 4, 4);
insert into compra_combo(codigo, cantidad, precio, compra_codigo, combo_codigo) values(5, 1, 25000, 5, 5);