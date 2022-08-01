create database plataforma_restaurante;
use plataforma_restaurante;



/*CLIENTES*/
create table clientes(
   id_cliente int auto_increment,
   rfc_cl  varchar(100),
   nombres_cl  varchar(100),
   apellidop_cl  varchar(100),
   apellidom_cl  varchar(100),
   direccion_cl  varchar(100),
   cp_cl varchar(5),
   correo_cl  varchar(100),
   celular_cl  varchar(10),
   constraint pk_tcl primary key(id_cliente)
  
);

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("GOGA020520827","Alan Didier","Gonzales","Gonzales","Revolución #1500","44105","alangg205@gmail.com", "3316806170");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("LODA050848877","David Alejandro","Lomeli", "Apodaca", "Revolución #1235","47225","davidlomeli1527@gmail.com","3313345675");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("FLGL100420727","Luis Enrique","Flores","Gonzales","Revolución #1100","40100","luisflg100@gmail.com","3318744100");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("GACS180530287","Sergio Arturo","Garcia","Camarena","Revolución #2500","48707","sergiog1875@gmail.com","3319344022");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("GAGL010530105","Luis Fernando","Garcia","Garcia","Revolución #1430","45215","luisgarcia273025@gmail.com","3313406790");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("ROMJ160760247","Jesus Antonio","Roman","Mora","Revolución #7052","45790","jesusroman27@gmail.com","3329547022");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("SADJ502720027","Joshua Alexander","Salazar","Diaz","Revolución #2775","47797","joshuasalaz2507@gmail.com","3313275032");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("GADL170401287","Luis Alejandro","Garcia"," Diaz","Revolución #2570","45427","luisgdiaz1427@gmail.com","3316254032");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("DEJC178540027","Carlos Oscar","Derbez","Jacinto","Revolución #4805","48457","carlosos1274@gmail.com","3316347023");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("CALA1707338097","Alan Antonio","Camarena","Lomeli","Revolución #2850","43219","alanlomeli3030@gmail.com","3319748052");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("GAGM170231427","Maria Fernanda","Garcia","Gonzales","Revolución #2835","46206","marigonza02@gmail.com","3313267092");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("GAAV160332857","Vanessa Itzel","Garcia","Apodaca","Revolución #2855","47426","vane005@gmail.com","3318920682");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("CAGM160630217","Maria Julieta","Garcia","Camarena","Revolución #2095","48226","julieg143010@gmail.com","3313329002");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("GOGA041080627","Ana Daniela","Gonzales","Gonzales","Revolución #1510","44215","dania1132@gmail.com","3316906190");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("LODA040146897","Diana Alejandra","Lomeli","Apodaca","Revolución #1245","48245","dianalomeli1657@gmail.com","3315945655");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("FLGL100820529","Lorena Eva","Flores","Gonzales","Revolución #3100","90100","lorefg100@gmail.com","3328544100");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("GACS185230587","Susana Amelia","Garcia","Camarena","Revolución #2800","45407","susang1875@gmail.com","3329384012");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("GAGL014301055","Lilith Fernanda","Garcia","Garcia","Revolución #1427","48515","lilithg5025@gmail.com","3313406090");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("ROMJ163700250","Julieta Alejandra","Roman","Mora","Revolución #82802",46480,"juliemora29@gmail.com","3328946032");

INSERT INTO clientes(rfc_cl, nombres_cl, apellidop_cl, apellidom_cl, direccion_cl,cp_cl,correo_cl,celular_cl) VALUES ("SADJ5027039","Jazmin Adilene","Salazar","Diaz","Revolución #2777",47589,"jazmindiaz107@gmail.com","3326378062");




create table estados_mesa(
    id_estado_em int,
    estado_em varchar(64),
    constraint pk_tem primary key(id_estado_em) 
); 

insert into estados_mesa VALUES(1,"Disponible") ,(2,"Ocupada");

/*PUESTO*/


/*PUESTO*/
create table puestos(
  id_puesto int,
  nombre_pu varchar(64),
  constraint pk_tp primary key(id_puesto)
);
INSERT INTO puestos VALUES(1,'Gerente'),(2,'Cajero'),(3,'Camarero');
/*EMPLEADOS*/
create table empleados(
    codigo_empleado smallint(5) not null,
    nombres_e varchar(100),
	apellido_me varchar(100),
    apellido_pe  varchar(100),
	password varchar(64) not null,
    puesto_e int not null,
    constraint pk_te primary key(codigo_empleado),
    constraint fk_tp foreign key(puesto_e) references puestos(id_puesto)
); 

INSERT INTO empleados(codigo_empleado,nombres_e,apellido_me,apellido_pe,password,puesto_e) VALUES(1,'Alan Didier','Gonzalez','Gonzalez','1234',1);
INSERT INTO empleados(codigo_empleado,nombres_e,apellido_me,apellido_pe,password,puesto_e) VALUES(2,'Sergio','Gonzalez','Gonzalez','1234',2);
INSERT INTO empleados(codigo_empleado,nombres_e,apellido_me,apellido_pe,password,puesto_e) VALUES(3,'Sergio','Gonzalez','Gonzalez','1234',2);
INSERT INTO empleados(codigo_empleado,nombres_e,apellido_me,apellido_pe,password,puesto_e) VALUES(4,'Sergio','Gonzalez','Gonzalez','1234',2);
INSERT INTO empleados(codigo_empleado,nombres_e,apellido_me,apellido_pe,password,puesto_e) VALUES(5,'Sergio','Gonzalez','Gonzalez','1234',3);

INSERT INTO `empleados` (`codigo_empleado`, `nombres_e`, `apellido_me`, `apellido_pe`, `password`, `puesto_e`) VALUES
(11, 'armando', 'jimenez', 'hernanzdez', '12345', 1),
(10, 'luis', 'garcia', 'hernandez', '12345', 3),
(9, 'pedro', 'perez', 'lopez', '12345', 3),
(8, 'mario', 'perez', 'lopez', '12345', 3),
(7, 'armando', 'garcia', 'camarena', '12345', 3),
(12, 'alan', 'gonzalez', 'gonzalez', '12345', 3);

create table mesas(
  num_mesa int,
  ubicacion varchar(200),
  personas_m int,
  estado_m int,
  constraint pk_tm primary key(num_mesa),
  constraint fk_tem foreign key(estado_m) references estados_mesa(id_estado_em)

);

INSERT INTO `mesas` (`num_mesa`, `ubicacion`, `personas_m`, `estado_m`) VALUES
(1, 'lado derecho', 5, 1),
(2, 'lado izquierdo', 5, 1),
(3, 'lado derecho', 10, 1),
(4, 'lado izquierdo', 5, 1),
(5, 'lado derecho', 5, 1),
(6, 'lado derecho', 10, 1),
(7, 'planta alta', 5, 1),
(8, 'planta alta', 5, 1),
(9, 'planta alta', 5, 1),
(10, 'planta alta', 5, 1);



/*estados comandas*/
create table estados_comanda(
    id_estado_ec int,
    estado_ec varchar(64),
    constraint pk_tec primary key(id_estado_ec) 
); 

insert into estados_comanda VALUES(1,"Abierta") ,(2,"Cerrada");


/*COMANDAS*/
create table comandas(
  num_comanda int auto_increment,
  fecha_c DATE NOT NULL,
  hora_c  TIME NOT NULL,
  estado_c int NOT NULL,
  camarero_c smallint(5) NOT NULL,
  cliente int ,
  num_comensales int ,
  mesa_c int NOT NULL,
  constraint pk_tc primary key(num_comanda),
  constraint fk_tm1 foreign key(mesa_c) references mesas(num_mesa),
  constraint fk_te foreign key(camarero_c) references empleados(codigo_empleado),
  constraint fk_tec foreign key(estado_c) references estados_comanda(id_estado_ec),
  constraint fk_tcl foreign key(cliente) references clientes(id_cliente)
);

/*PLATILLOS*/

/*estados Platillos y Bebidas*/
create table estados_platillobebida(
    id_estado_epb int,
    estado_epb varchar(64),
    constraint pk_tepb primary key(id_estado_epb) 
); 

insert into estados_platillobebida VALUES(1,"Disponible") ,(2,"No Disponible");

create table categoriaPB(
    id_categoriapb int auto_increment,
    nombre_cpb varchar(64),
    constraint pk_tcpb primary key(id_categoriapb) 

);
insert into categoriaPB(nombre_cpb) VALUES("Platillo") ,("Bebida");

create table platillosBebidas(
  id_pb int,
  nombre_pb varchar(100),
  descripcion_pb varchar(255),
  precio_unitario_pb float,
  precio_pb float,
  categoria_pb int,
  estado_pb int,
  constraint pk_tpl primary key(id_pb),
  constraint fk_tepb foreign key(estado_pb) references estados_platillobebida(id_estado_epb),
  constraint fk_tcpb foreign key(categoria_pb) references categoriaPB(id_categoriapb)
); 

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("35","Filete T-bone","Corte de Res de 400 gramos",320,320,1,1);

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("36","Sashimi","Sashimi de pescado",80,80,1,1);

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("37","Crema de Tomate"," Crema de tomate con cebolla caramelizada",120,120,1,1) ;

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("38","Filete Rossini","Filete de Res Angus ",575,575,1,1) ;

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("39","Rollitos Primavera","Rollitos Primavera rellenos de pato y salsa dulce y picante ",105,105,1,1) ;

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("40","Rib Eye Angus","Filete de Res Reb-Eye Angus estilo grill ",400,400,1,1) ;

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("41","Ensalada 3 manzanas con queso ahumado","Ensalada de manzana con piñones y queso ahumado ",95,95,1,1) ;

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("42","Atún con caviar de cangrejo","Atún con aguacate y hueva de cangrejo",195,195,1,1) ;

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("43","Chuletón ", "Chuleton de 800 gramos añejado durante 30 días",960,960,1,1) ;

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("44","Pasta","Variedad de Pastas elaboradas por la casa",200,200,1,1) ;

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("45","Mollejas de ternera", "Carne de ternera con especias y menta piperita",218,218,1,1) ;

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("46","Ternera al horno", "Pecho de ternera braseado durante seis  horas acompañado de pico de gallo y salsa guacamoleada",360,360,1,1) ;

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("47","Pollo a la Cordon blue", "Pechuga de pollo rellena de queso y finas yerbas",150,150,1,1) ;

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("48","Pato a la naranja ", "Clasico de la cocina francesa con una salsa increíble ",250,250,1,1) ;

INSERT INTO platillosbebidas(id_pb, nombre_pb, descripcion_pb, precio_unitario_pb, precio_pb,categoria_pb,estado_pb ) VALUES ("49","Spaghetti  ala boloñesa", "Un clásico de la cocina  italiana",200,200,1,1) ;







/*PEDIDOS ESTADOS
create table estados_pedido(
    id_estado_ep int,
    estado_ep varchar(64),
    constraint pk_tep primary key(id_estado_ep) 
); 

insert into estados_pedido VALUES(1,"Pediente") ,(2,"En proceso"),(3,"Listo");

PEDIDOS
create table pedidos(
  num_orden int auto_increment,
  comanda_p int,
  estado_p int,
  constraint pk_tpe primary key(num_orden),
  constraint fk_tc foreign key(comanda_p) references comandas(num_comanda),
  constraint fk_tep foreign key(estado_p) references estados_pedido(id_estado_ep)
);

*/

/*PEDIDOS PLATILLOS DETALLES*/
create table pedido_pb_detalles(
  num_orden_ppb int,
  cantidad_ppb int,
  producto_ppb int,
  constraint fk_tppb foreign key(num_orden_ppb) references comandas(num_comanda),
  constraint fk_tpl foreign key(producto_ppb) references platillosBebidas(id_pb)
);
/*PEDIDOS DETALLES*/



/*PROVEEDORES*/
create table proveedores(
   id_proveedor int auto_increment,
   nombre_pr varchar(100),
   telefono_pr varchar(10),
   correo_pr varchar(100),
   constraint pk_tpr primary key(id_proveedor)
);

INSERT INTO proveedores( nombre_pr, telefono_pr,correo_pr) VALUES ("JT Carnes ","8110861557"," info@jtcarnes.com.mx");

INSERT INTO proveedores( nombre_pr, telefono_pr,correo_pr) VALUES ("Carnes Finas San Nicolás ","3325068244","carnesfinassn@gmail.com");

INSERT INTO proveedores( nombre_pr, telefono_pr,correo_pr) VALUES ("Carnes San Francisco ","5530851187"," info@carnessanfrancisco.com.mx");

INSERT INTO proveedores( nombre_pr, telefono_pr,correo_pr) VALUES ("Carnes América Luis","57031561"," ventas@americaluis.com.mx");

INSERT INTO proveedores( nombre_pr, telefono_pr,correo_pr) VALUES ("Comestibles Maldonado","6622898950","maldonadoco@cm.com.mx");

INSERT INTO proveedores( nombre_pr, telefono_pr,correo_pr) VALUES ("Frutas con sabor a México","7211430861"," contacto@frutosconsaboramexico.com");

INSERT INTO proveedores( nombre_pr, telefono_pr,correo_pr) VALUES ("El huerto","5527029915"," hola@huerto.com.mx");

INSERT INTO proveedores( nombre_pr, telefono_pr,correo_pr) VALUES ("Hudson traders","2019173044"," hudsontrd@gmail.com");

INSERT INTO proveedores( nombre_pr, telefono_pr,correo_pr) VALUES ("LaAnita","9999301812"," ventas@laanita.com");

INSERT INTO proveedores( nombre_pr, telefono_pr,correo_pr) VALUES ("FreshKampo","3545512416"," contacto@freshkampo.com");



/*INSUMOS
create table insumos(
id_insumo int auto_increment,
nombre_i varchar(200),
descripcion_i varchar(200),
cantidad int,
unidad int,
stock_min int,
stock_max int,
proveedor_i int,
constraint pk_ti primary key(id_insumo),
constraint fk_tpr foreign key(proveedor_i) references proveedores(id_proveedor)
);

BEBIDA_INSUMOS
create table bebida_insumos(
bebida_bi int,
insumo_bi int,
constraint fk_tb1 foreign key(bebida_bi) references  bebidas(id_bebida),
constraint fk_ti foreign key(insumo_bi) references    insumos(id_insumo)
);
*/

/*PLATILLOS_INSUMOS
create table platillo_insumos(
platillo_pi int,
insumo_pi int,
constraint fk_tpl1 foreign key(platillo_pi) references  platillos(id_platillo),
constraint fk_ti1 foreign key(insumo_pi) references  insumos(id_insumo)
);
*/

/*INSUMOS ENTRADA
create table insumos_entrada(
fecha_ie date,
hora_ie time,
insumo_ie int,
cantidad int,
almacen int,
constraint fk_ti2 foreign key(insumo_ie) references insumos(id_insumo)
);

*/
/*INSUMOS SALIDA
create table insumos_salida(
fecha_is date,
hora_is time,
insumo_is int,
cantidad int,
constraint fk_ti3 foreign key(insumo_is) references insumos(id_insumo)
);
*/
/*estados cajas
create table estados_caja(
    id_estado_ea int,
    estado_ea varchar(64),
    constraint pk_tea primary key(id_estado_ea) 
); 

insert into estados_caja VALUES(1,"Abierta") ,(2,"Cerrada");
*/
/*CAJAS
create table cajas(
num_caja int,
estado_ca int,
monto_inicio float,
constraint pk_tca primary key(num_caja),
constraint fk_tea foreign key(estado_ca) references estados_caja(id_estado_ea)
);*/
/*CAJA MOVIMIENTO
create table tipo_movimiento_caja(
    id_tmc int,
    movimiento_tmc varchar(64),
    constraint pk_ttmc primary key(id_tmc) 
); 

insert into tipo_movimiento_caja VALUES(1,"Deposito") ,(2,"Retiro");


create table caja_movimiento(
folio_cm int auto_increment,
tipo_cm int,
observacion_cm varchar(100),
fecha_cm date,
hora_cm time,
monto_cm float,
responsable_cm int,
constraint pk_tcm primary key(folio_cm),
constraint fk_ttcm foreign key(tipo_cm) references tipo_movimiento_caja(id_tmc)
);*/

/*VENTAS*/
create table ventas(
folio int auto_increment,
comanda_v int,
fecha_v date,
hora_v time,
num_caja_v varchar(100),
cajero_v smallint(5),
cambio float,
efectivo float,
importe float,
propina float,
descuento float,
constraint pk_tv  primary key(folio),
/*constraint fk_tca foreign key(num_caja_v) references cajas(num_caja)*/
constraint fk_tc2  foreign key(comanda_v) references comandas(num_comanda),
constraint fk_te1 foreign key(cajero_v) references empleados(codigo_empleado)
);