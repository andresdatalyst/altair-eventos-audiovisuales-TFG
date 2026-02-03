
/*TABLA LOCATIONS*/
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Melilla","683-4221 Scelerisque, Avenue","West Region",35960, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Gijon","Ap #482-5972 Morbi Avenue","Utrecht",84792, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Melilla","P.O. Box 327, 177 Ullamcorper, Av.","Cordillera Administrative Region",97929, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Cordoba","980-2879 Proin St.","Vinnytsia oblast",87516, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Melilla","315-9256 At Av.","Sao Paulo",58266, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Leon","P.O. Box 196, 1868 Purus Road","Umbria",68659, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Gasteiz","1124 Nulla Rd.","North Island",83400, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Bilbo","Ap #151-7975 In Rd.","Munster",39303, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Pamplona","782-3573 Eu Avenue","Maluku",33038, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Alacant","761-8955 Erat Ave","Alberta",40474, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Algeciras","Ap #680-5766 Ante Avenue","Viken",12457, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Lleida","Ap #269-6850 Diam St.","Mpumalanga",19545, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Avila","945-4633 Sed Street","Limon",25269, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Ceuta","923-2182 Magna, Av.","Ceara",69217, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Torrevieja","641-8716 Donec Street","Alajuela",18669, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Burgos","P.O. Box 318, 5948 Nibh St.","Limburg",62349, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Teruel","689-2018 Eu Rd.","Coquimbo",94440, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Zaragoza","Ap #263-6453 Ligula St.","Gilgit Baltistan",26857, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Albacete","8898 Fusce Avenue","Katsina",95759, '');
INSERT INTO locations ( city, direction, location_name,postal_code,photo) VALUES( "Bilbo","341-2201 Amet, Av.","Mersin",20171, '');

/*TABLA MENU*/
INSERT INTO menus (action,label) VALUES("/menu/listAll","Menus");
INSERT INTO menus (action,label) VALUES("/role/listAll","Roles");
INSERT INTO menus (action,label) VALUES("/audiovisualMaterial/listAll","Materiales Audiovisuales");
INSERT INTO menus (action,label) VALUES("/location/listAll","Localizaciones");
INSERT INTO menus (action,label) VALUES("/producer/listAll","Productores");
INSERT INTO menus (action,label) VALUES("/hardworkingCompany/listAll","Empresas Trabajadoras");
INSERT INTO menus (action,label) VALUES("/worker/listAll","Trabajadores");
INSERT INTO menus (action,label) VALUES("/admin/listAll","Admins");
INSERT INTO menus (action,label) VALUES("/userAccount/listAll","UserAccounts");
INSERT INTO menus (action,label) VALUES("/roleHasMenu/listAll","RoleHasMenu");
INSERT INTO menus (action,label) VALUES("/event/listAll","Events");
INSERT INTO menus (action,label) VALUES("/eventHasMaterial/listAll","EventHasMaterials");


/*TABLA ROLES*/
INSERT INTO roles (role_description,role_name) VALUES ("Rol de Administrador","ROLE_ADMIN");
INSERT INTO roles (role_description,role_name) VALUES ("Rol de Productor","ROLE_PRODUCER");
INSERT INTO roles (role_description,role_name) VALUES ("Rol de Jefe de Empresa Trabajadora","ROLE_BOSS");
INSERT INTO roles (role_description,role_name) VALUES ("Rol de Trabajador","ROLE_WORKER");

/*TABLA MATERIALES AUDIOVISUALES*/
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("50","Cables XLR","")
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("50","Cables DMX","")
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("20","Cables RJ","")
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("30","Cables HDMI","")
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("10","Monitores Yamaha","")
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("2","Televisores 50 pulgadas","")
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("20","Estructura Truck","")
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("15","Pies de Microfrono","")
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("10","Micros Shure de mano","")
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("5","Micros Shure Inalambricos","")
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("15","Camaras Sony","")
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("100","Cintas aislantes","")
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("80","Cintas Americanas","")
INSERT INTO audiovisual_materials(amount,material_name,photo) VALUES("50","Paquete de bridas","")

/*TABLA COMPAÑIAS TRABAJADORAS*/

INSERT INTO hardworking_companys (cif,direccion,email,name,phone,photo,postal_code,province) VALUES("G05390042","621-4549 Sodales. Rd.","a.neque@icloud.couk","Ornare Ltd",776620707,"",48918,"Ceuta");
INSERT INTO hardworking_companys (cif,direccion,email,name,phone,photo,postal_code,province) VALUES("W5380655J","158-4870 Est. St.","phasellus.in@icloud.ca","Sed Libero LLP",768468106,"",95736,"Castilla y Leon");
INSERT INTO hardworking_companys (cif,direccion,email,name,phone,photo,postal_code,province) VALUES("B67242586","Ap #327-3627 At, Rd.","libero.nec@protonmail.org","Cras Sed LLP",937539613,"",32342,"Murcia");
INSERT INTO hardworking_companys (cif,direccion,email,name,phone,photo,postal_code,province) VALUES("P2213741H","Ap #165-6136 Vitae, Avenue","dui.quis@yahoo.couk","Posuere Vulputate Inc.",608839268,"",66576,"Catalunya");
INSERT INTO hardworking_companys (cif,direccion,email,name,phone,photo,postal_code,province) VALUES("G27286384","Ap #558-130 Mauris Road","aliquet.odio.etiam@yahoo.org","Eu Nibh Vulputate Corporation",756392602,"",53380,"Castilla - La Mancha");
INSERT INTO hardworking_companys (cif,direccion,email,name,phone,photo,postal_code,province) VALUES("V92520030","Ap #399-6258 Elementum Road","pharetra@hotmail.ca","Vitae Aliquet Nec Incorporated",794910456,"",17490,"Castilla y Leon");
INSERT INTO hardworking_companys (cif,direccion,email,name,phone,photo,postal_code,province) VALUES("C60030889","623-468 Non, Avenue","quis@yahoo.ca","Erat Eget Ipsum PC",806286461,"",50002,"Comunitat Valenciana");
INSERT INTO hardworking_companys (cif,direccion,email,name,phone,photo,postal_code,province) VALUES("H67460451","Ap #327-5523 Donec St.","et.commodo@icloud.net","Et Pede Institute",521354319,"",68453,"Aragon");
INSERT INTO hardworking_companys (cif,direccion,email,name,phone,photo,postal_code,province) VALUES("D31462815","Ap #847-4011 Donec Rd.","aenean@hotmail.ca","Commodo At PC",779360779,"",22215,"Melilla");
INSERT INTO hardworking_companys (cif,direccion,email,name,phone,photo,postal_code,province) VALUES("G01868850","Ap #344-2794 Quis Street","sed.dui@outlook.com","Scelerisque Neque LLP",981864592,"",60150,"La Rioja");











