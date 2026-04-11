create table if not exists categorias (
	idCategoria serial not null primary key,
	cdCategoria varchar(50) not null unique,
	nmCategoria varchar(100) not null
);

create table if not exists moedas (
	idMoeda serial not null primary key,
	nmMoeda varchar(100) not null,
	dsCifrao varchar(50) not null,
	dsSigla varchar(50) not null
);

create table if not exists produtos (
	idProduto serial not null primary key,
	cdProduto varchar(50) not null unique,
	nmProduto varchar(100) not null,
	dsProduto varchar(200),
	vlCusto decimal(15,2),
	vlProduto decimal(15,2),
	fk_idCategoria int not null,
	foreign key (fk_idCategoria) references categorias(idCategoria),
	fk_idMoeda int not null,
	foreign key (fk_idMoeda) references moedas(idMoeda)
);


create table if not exists estoques (
	idEstoque serial not null primary key,
	cdEstoque varchar(50) not null unique,
	nmEstoque varchar(100) not null,
	dsEstoque varchar(200),
	fk_idCategoria int not null,
	foreign key (fk_idCategoria) references categorias(idCategoria)

);

create table if not exists produtosEstoques (
	idProdutoEstoque serial not null primary key,
	nrQuantidade bigint not null,
	dsObservacao varchar(200),
	fk_idProduto int not null,
	foreign key (fk_idProduto) references produtos(idProduto),
	fk_idEstoque int not null,
	foreign key (fk_idEstoque) references estoques(idEstoque)

);

create table if not exists clientes (
	idCliente serial not null primary key,
	nmCliente varchar(100) not null,
	nrDocumento varchar(50)
);

create table if not exists acessos (
	idAcesso serial not null primary key,
	nmAcesso varchar(100) not null,
	cdAcesso varchar(50) not null unique,
	dsAcesso varchar(200)
);

create table if not exists funcionarios (
	idFuncionario serial not null primary key,
	nmFuncionario varchar(100) not null,
	cdFuncionario varchar(50) not null unique,
	fk_idAcesso int not null,
	foreign key (fk_idAcesso) references acessos(idAcesso)
);

create table if not exists vendas (
	idVenda serial not null primary key,
	dtVenda timestamptz not null,
	vlVenda decimal(15,2) not null,
	flPago bool not null,
	fk_idCliente int not null,
	foreign key (fk_idCliente) references clientes(idCliente),
	fk_idFuncionario int not null,
	foreign key (fk_idFuncionario) references funcionarios(idFuncionario)
);

create table if not exists produtosVendas (
	idProdutoVenda serial not null primary key,
	nrQuantidade bigint not null check(nrQuantidade > 0),
	vlSaldo decimal(15,2) not null,
	fk_idProdutoEstoque int not null,
	foreign key (fk_idProdutoEstoque) references produtosEstoques(idProdutoEstoque),
	fk_idVenda int not null,
	foreign key (fk_idVenda) references vendas(idVenda)
);
