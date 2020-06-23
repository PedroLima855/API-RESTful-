create table ordem_servico(
	
	id SERIAL PRIMARY KEY,
	cliente_id int NOT NULL,
	descricao varchar(60) NOT NULL,
	preco decimal (12,2) NOT NULL,
	status varchar(20) NOT NULL,
	data_abertura timestamp NOT NULL,
	data_finalizacao timestamp,
	
	foreign key (cliente_id) references cliente (id)
);