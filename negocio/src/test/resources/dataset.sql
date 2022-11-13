insert into rol(codigo, nombre) values(1, "ROLE_CLIENTE");
insert into rol(codigo, nombre) values(2, "ROLE_ADMIN");
insert into rol(codigo, nombre) values(3, "ROLE_ADMIN_TEATRO");

insert into cliente(codigo, cedula, correo, estado, primer_nombre, primer_apellido, password, rol_codigo, username) values (1, "1852151","juan@email.com",0,"juan","manuel","$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku", 1, "juan");
insert into cliente(codigo, cedula, correo, estado, primer_nombre, primer_apellido, password, rol_codigo, username)values (2, "1051211","manuel@email.com",1,"manuel","pedro","$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku", 1, "manuel");
insert into cliente(codigo, cedula, correo, estado, primer_nombre, primer_apellido, password, rol_codigo, username) values (3, "2151541","pedro@email.com",0,"pedro","pablo","$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku", 1 , "pedro");
insert into cliente(codigo, cedula, correo, estado, primer_nombre, primer_apellido, password, rol_codigo, username) values (4, "1224544","pablo@email.com",1,"pablo","garcia","$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku", 1, "pablo");
insert into cliente(codigo, cedula, correo, estado, primer_nombre, primer_apellido, password, rol_codigo, username) values (5, "9522222","emilio@email.com",0,"emilio","jimenez","$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku", 1, "emilio");

insert into cliente_telefonos values (1,"651651111");
insert into cliente_telefonos values (2,"7874152");
insert into cliente_telefonos values (3,"6532181");
insert into cliente_telefonos values (4,"321645444");
insert into cliente_telefonos values (5,"321645444");

insert into administrador(codigo, correo, password, primer_nombre, primer_apellido, rol_codigo, username) values (1, "admin1@email.com", "$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku","Manuel","Ramirez", 2, "admin1");
insert into administrador(codigo, correo, password, primer_nombre, primer_apellido, rol_codigo, username) values (2, "admin2@email.com","$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku","David","Fernando", 2, "admin2");
insert into administrador(codigo, correo, password, primer_nombre, primer_apellido, rol_codigo, username) values (3, "admin3@email.com","$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku","Sebastian","juan", 2, "admin3");
insert into administrador(codigo, correo, password, primer_nombre, primer_apellido, rol_codigo, username) values (4, "admin4@email.com","$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku","Laura","Restrepo", 2, "admin4");
insert into administrador(codigo, correo, password, primer_nombre, primer_apellido, rol_codigo, username) values (5, "admin5@email.com","$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku","Alison","Restrepo", 2, "admin5");

insert into administrador_teatro(codigo, correo, password, primer_nombre, primer_apellido, rol_codigo, username) values (1, "admin6@email.com", "$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku","Manuela","Ramirez", 3, "admin6");
insert into administrador_teatro(codigo, correo, password, primer_nombre, primer_apellido, rol_codigo, username) values (2, "admin7@email.com","$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku","Kevin","Fernando", 3, "admin7");
insert into administrador_teatro(codigo, correo, password, primer_nombre, primer_apellido, rol_codigo, username) values (3, "admin8@email.com","$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku","Laura","juan", 3, "admin8");
insert into administrador_teatro(codigo, correo, password, primer_nombre, primer_apellido, rol_codigo, username) values (4, "admin9@email.com","$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku","Hernan","Restrepo", 3, "admin9");
insert into administrador_teatro(codigo, correo, password, primer_nombre, primer_apellido, rol_codigo, username) values (5, "admin10@email.com","$2a$10$6CDWjRFJbLSPHqh2twL6UO/R8EoyddMTfHqsKweAXVHPm5TGZm2Ku","Pablo","Restrepo", 3, "admin10");

insert into imagen(codigo, nombre, imagen_url, imagen_id) values(1, "hola","hola.jpg","41122533894");
insert into imagen(codigo, nombre, imagen_url, imagen_id) values(2, "juan","juan.jpg","5125894");
insert into imagen(codigo, nombre, imagen_url, imagen_id) values(3, "roger","roger.jpg","5182725894");
insert into imagen(codigo, nombre, imagen_url, imagen_id) values(4, "onepiece","onepiece.jpg","671894");
insert into imagen(codigo, nombre, imagen_url, imagen_id) values(5, "jdw","jdw.jpg","253452");


insert into pelicula(codigo, estado, genero, nombre, reparto, sinopsis, imagen_codigo, url_trailer) values(1, 1,"TERROR","El Conjuro","actores","sinopsis",1,"trailer");
insert into pelicula(codigo, estado, genero, nombre, reparto, sinopsis, imagen_codigo, url_trailer) values(2, 1,"ACCION", "El resplandor","actores","sinopsis",2,"trailer");
insert into pelicula(codigo, estado, genero, nombre, reparto, sinopsis, imagen_codigo, url_trailer) values(3, 1,"CIENCIA_FICCION","Tenet","actores","sinopsis",3,"trailer");
insert into pelicula(codigo, estado, genero, nombre, reparto, sinopsis, imagen_codigo, url_trailer) values(4, 1,"CIENCIA_FICCION","Spiderman 3","actores","sinopsis",4,"trailer");
insert into pelicula(codigo, estado, genero, nombre, reparto, sinopsis, imagen_codigo, url_trailer) values(5, 1,"CIENCIA_FICCION","Batman: El caballero de la noche","actores","sinopsis",5,"trailer");

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

insert into confiteria(codigo, nombre, descripcion, precio, imagen_codigo) values(1, "Crispetas caramelo", "Tamaño normal", 12500, 1);
insert into confiteria(codigo, nombre, descripcion, precio, imagen_codigo) values(2, "Crispetas", "Tamaño normal", 9000, 2);
insert into confiteria(codigo, nombre, descripcion, precio, imagen_codigo) values(3, "Perro caliente", "Tamaño normal", 15000, 3);
insert into confiteria(codigo, nombre, descripcion, precio, imagen_codigo) values(4, "Coca cola", "1L", 5000, 4);
insert into confiteria(codigo, nombre, descripcion, precio, imagen_codigo) values(5, "Pepsi", "1L", 5000, 5);


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

insert into combo(codigo, nombre, precio, descripcion, imagen_codigo) values(1, "Combo papas francesas y hamburguesa doble queso", 9000, "Desc", 1);
insert into combo(codigo, nombre, precio, descripcion, imagen_codigo) values(2, "Combo papas francesas y perro", 9000, "Desc", 2);
insert into combo(codigo, nombre, precio, descripcion, imagen_codigo) values(3, "Combo palomitas y pepsi", 20000, "Palomitas XL y pepsi 2L", 3);
insert into combo(codigo, nombre, precio, descripcion, imagen_codigo) values(4, "Combo palomitas y coca cola", 15000, "Palomitas X y pepsi 2L", 4);
insert into combo(codigo, nombre, precio, descripcion, imagen_codigo) values(5, "Combo palomitas dulces y pepsi", 25000, "Palomitas XL y pepsi 2L", 5);

insert into compra_combo(codigo, cantidad, precio, compra_codigo, combo_codigo) values(1, 2, 18000, 1, 1);
insert into compra_combo(codigo, cantidad, precio, compra_codigo, combo_codigo) values(2, 1, 9000, 2, 2);
insert into compra_combo(codigo, cantidad, precio, compra_codigo, combo_codigo) values(3, 2, 40000, 3, 3);
insert into compra_combo(codigo, cantidad, precio, compra_codigo, combo_codigo) values(4, 1, 15000, 4, 4);
insert into compra_combo(codigo, cantidad, precio, compra_codigo, combo_codigo) values(5, 1, 25000, 5, 5);