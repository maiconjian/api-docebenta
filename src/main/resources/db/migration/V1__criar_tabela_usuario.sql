CREATE TABLE usuario(
        id uuid NOT NULL DEFAULT gen_random_uuid(),
        nome varchar(100),
        cpf varchar(20),
        login varchar(50),
        senha varchar(255),
        email varchar(100),
        cadastro timestamp  DEFAULT CURRENT_DATE,
        atualizacao timestamp ,
        ativo boolean,
        CONSTRAINT usuario_pkey PRIMARY KEY(id)
);