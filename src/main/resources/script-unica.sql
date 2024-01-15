CREATE DATABASE  IF NOT EXISTS `unica`;
USE `unica`;

CREATE TABLE ubs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  cnpj VARCHAR(20) NOT NULL,
  nome VARCHAR(255) NOT NULL,
  estado VARCHAR(255) NOT NULL,
  municipio VARCHAR(255) NOT NULL,
  cep VARCHAR(10),
  bairro VARCHAR(255),
  logradouro VARCHAR(255),
  numero INTEGER,
  cnes VARCHAR(20) NOT NULL,
  ativo BOOLEAN,
  data_criacao DATE,
  hora_criacao TIME
);

CREATE TABLE usuario (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  tipo_usuario VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  cpf VARCHAR(15) UNIQUE,
  cnpj VARCHAR(20) UNIQUE,
  estado VARCHAR(255),
  municipio VARCHAR(255),
  regiao_geografica VARCHAR(255),
  cep VARCHAR(10),
  bairro VARCHAR(255),
  logradouro VARCHAR(255),
  numero INTEGER,
  senha VARCHAR(255) NOT NULL,
  cnes VARCHAR(20),
  ativo BOOLEAN,
  data_criacao DATE,
  hora_criacao TIME,
  ubs_id BIGINT,
  FOREIGN KEY (ubs_id) REFERENCES ubs(id)
);

INSERT INTO usuario (ativo, bairro, cep, cnpj, cpf, data_criacao, email, estado, hora_criacao, logradouro, municipio, nome, numero, regiao_geografica, senha, tipo_usuario, ubs_id)VALUES (true, 'bairro', '34001090', '81.558.931/0001-01', '191.950.450-81', '2023-10-27', 'admin@geral', 'MG', '01:49:00', 'logradouro', 'Nova Lima', 'Adm Geral', 56, 'ZONA_SUL', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_ADMINISTRADOR_GERAL', null);
INSERT INTO usuario (ativo, bairro, cep, cnpj, cpf, data_criacao, email, estado, hora_criacao, logradouro, municipio, nome, numero, regiao_geografica, senha, tipo_usuario, ubs_id)VALUES (true, 'bairro', '34001090', '81.558.932/0001-02', '191.950.450-82', '2023-10-27', 'agente@endemias', 'MG', '01:49:00', 'logradouro', 'Nova Lima', 'Agente Endemias', 56, 'ZONA_NORTE', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_AGENTE_ENDEMIAS', null);
INSERT INTO usuario (ativo, bairro, cep, cnpj, cpf, data_criacao, email, estado, hora_criacao, logradouro, municipio, nome, numero, regiao_geografica, senha, tipo_usuario, ubs_id)VALUES (true, 'bairro', '34001090', '81.558.932/0001-03', '191.950.450-83', '2023-10-27', 'admin@estadual', 'MG', '01:49:00', 'logradouro', 'Nova Lima', 'Adm Estadual', 56, 'ZONA_OESTE', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_ADMINISTRADOR_ESTADUAL', null);
INSERT INTO usuario (ativo, bairro, cep, cnpj, cpf, data_criacao, email, estado, hora_criacao, logradouro, municipio, nome, numero, regiao_geografica, senha, tipo_usuario, ubs_id)VALUES (true, 'bairro', '34001090', '81.558.932/0001-04', '191.950.450-84', '2023-10-27', 'admin@municipal', 'MG', '01:49:00', 'logradouro', 'Nova Lima', 'Adm Municipal', 56, 'ZONA_LESTE', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_ADMINISTRADOR_MUNICIPAL', null);
INSERT INTO usuario (ativo, bairro, cep, cnpj, cpf, data_criacao, email, estado, hora_criacao, logradouro, municipio, nome, numero, regiao_geografica, senha, tipo_usuario, ubs_id)VALUES (true, 'bairro', '34001090', '81.558.932/0001-05', '191.950.450-85', '2023-10-27', 'role@ubs', 'MG', '01:49:00', 'logradouro', 'Nova Lima', 'Ubs', 56, 'ZONA_NORTE', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_UBS', null);
INSERT INTO usuario (ativo, bairro, cep, cnpj, cpf, data_criacao, email, estado, hora_criacao, logradouro, municipio, nome, numero, regiao_geografica, senha, tipo_usuario, ubs_id)VALUES (true, 'bairro', '34001090', '81.558.932/0001-06', '191.950.450-86', '2023-10-27', 'admin@nacional', 'MG', '01:49:00', 'logradouro', 'Nova Lima', 'Adm Nacional', 56, 'ZONA_OESTE', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_ADMINISTRADOR_NACIONAL', null);
INSERT INTO usuario (ativo, bairro, cep, cnpj, cpf, data_criacao, email, estado, hora_criacao, logradouro, municipio, nome, numero, regiao_geografica, senha, tipo_usuario, ubs_id)VALUES (true, 'bairro', '34001090', '81.558.932/0001-07', '191.950.450-87', '2023-10-27', 'profissional@ubs', 'MG', '01:49:00', 'logradouro', 'Nova Lima', 'Profissional Ubs', 56, 'ZONA_SUL', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_PROFISSIONAL_UBS', null);

CREATE TABLE paciente (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255),
  data_nascimento DATE,
  cpf VARCHAR(15),
  cartao_sus VARCHAR(20) NOT NULL,
  logradouro VARCHAR(255),
  bairro VARCHAR(255),
  cidade VARCHAR(255),
  cep VARCHAR(10),
  estado VARCHAR(255),
  codigo INTEGER,
  numero INTEGER,
  tipo_arbovirose VARCHAR(255) NOT NULL,
  tipo_analise VARCHAR(255),
  analise_paciente VARCHAR(255),
  observacao VARCHAR(255),
  obito BOOLEAN,
  internado BOOLEAN,
  obito_suspeito BOOLEAN,
  data_criacao DATE,
  hora_criacao TIME,
  ubs_id BIGINT,
  FOREIGN KEY (ubs_id) REFERENCES ubs(id)
);

CREATE TABLE documento (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  usuario_id BIGINT,
  tipo_imovel VARCHAR(255) NOT NULL,
  acesso_imovel BOOLEAN,
  foto VARCHAR(255),
  observacao VARCHAR(255),
  logradouro VARCHAR(255),
  bairro VARCHAR(255),
  numero INTEGER,
  cidade VARCHAR(255),
  uf VARCHAR(255),
  cep VARCHAR(10),
  quadra VARCHAR(255),
  zona INTEGER,
  nome_proprietario VARCHAR(255) NOT NULL,
  data_nascimento_proprietario DATE,
  cpf_proprietario VARCHAR(15),
  presenca_criadouros BOOLEAN NOT NULL,
  tipo_deposito VARCHAR(255) NOT NULL,
  foto_criadouro VARCHAR(255),
  quantidade INTEGER,
  presenca_larva BOOLEAN NOT NULL,
  observacao_criadouro VARCHAR(255),
  data_criacao DATE,
  hora_criacao TIME,
  tag_code VARCHAR(255),
  tipo_analise VARCHAR(255),
  tipo_intervencao VARCHAR(255),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE tb_password_recover (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    expiration TIMESTAMP NOT NULL
);

CREATE TABLE relatorio (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  indice_infestacao_predial VARCHAR(255) NOT NULL,
  risco_transmissao VARCHAR(255) NOT NULL,
  indice_breteau DOUBLE NOT NULL,
  notificados INTEGER NOT NULL,
  casos_positivos INTEGER NOT NULL,
  casos_suspeitos INTEGER NOT NULL,
  internados INTEGER NOT NULL,
  obitos INTEGER NOT NULL,
  obitos_suspeitos INTEGER NOT NULL,
  cidade VARCHAR(255) NOT NULL,
  uf VARCHAR(255) NOT NULL,
  data_criacao DATE NULL,
  usuario_id BIGINT REFERENCES usuario(id)
);


