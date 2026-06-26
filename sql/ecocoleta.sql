-- ============================================================
--  EcoColeta - Script de Criação do Banco de Dados
--  Engenharia de Software II - 2026
--  Grupo: Julio Cezar Melo, Jean José, Gabriel Vieira
-- ============================================================

CREATE DATABASE IF NOT EXISTS ecocoleta
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE ecocoleta;

-- --------------------------------------------------------
-- Tabela: usuarios
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuarios (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nome        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    senha       VARCHAR(255) NOT NULL,          -- armazenar hash (ex: SHA-256)
    tipo        ENUM('CIDADAO','EMPRESA','ADMIN') NOT NULL DEFAULT 'CIDADAO',
    ativo       BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- --------------------------------------------------------
-- Tabela: pontos_coleta
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS pontos_coleta (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(150) NOT NULL,
    endereco        VARCHAR(255) NOT NULL,
    latitude        DECIMAL(10, 8),
    longitude       DECIMAL(11, 8),
    descricao       TEXT,
    ativo           BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_responsavel  INT NOT NULL,
    FOREIGN KEY (id_responsavel) REFERENCES usuarios(id)
);

-- --------------------------------------------------------
-- Tabela: tipos_material
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS tipos_material (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    nome    VARCHAR(80) NOT NULL UNIQUE   -- ex: Plástico, Vidro, Eletrônico
);

-- --------------------------------------------------------
-- Tabela: ponto_material  (N:N entre pontos e materiais)
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS ponto_material (
    id_ponto    INT NOT NULL,
    id_material INT NOT NULL,
    PRIMARY KEY (id_ponto, id_material),
    FOREIGN KEY (id_ponto)    REFERENCES pontos_coleta(id) ON DELETE CASCADE,
    FOREIGN KEY (id_material) REFERENCES tipos_material(id)
);

-- --------------------------------------------------------
-- Tabela: avaliacoes
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS avaliacoes (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nota        TINYINT NOT NULL CHECK (nota BETWEEN 1 AND 5),
    comentario  TEXT,
    criado_em   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_usuario  INT NOT NULL,
    id_ponto    INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_ponto)   REFERENCES pontos_coleta(id) ON DELETE CASCADE
);

-- --------------------------------------------------------
-- Tabela: guias_informativos  (RF-06)
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS guias_informativos (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    titulo      VARCHAR(200) NOT NULL,
    conteudo    TEXT NOT NULL,
    criado_em   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_autor    INT NOT NULL,
    FOREIGN KEY (id_autor) REFERENCES usuarios(id)
);

-- --------------------------------------------------------
-- Dados iniciais
-- --------------------------------------------------------
-- Admin padrão (senha: admin123 — em produção use hash)
INSERT INTO usuarios (nome, email, senha, tipo) VALUES
('Administrador', 'admin@ecocoleta.com', 'admin123', 'ADMIN');

-- Tipos de material padrão
INSERT INTO tipos_material (nome) VALUES
('Plástico'),
('Vidro'),
('Papel/Papelão'),
('Metal'),
('Eletrônico'),
('Medicamento'),
('Agrotóxico'),
('Óleo de Cozinha');

-- Ponto de coleta de exemplo
INSERT INTO pontos_coleta (nome, endereco, latitude, longitude, descricao, id_responsavel) VALUES
('Ecoponto Central', 'Rua das Flores, 100 - Centro', -27.4005, -51.4578,
 'Aceita plástico, vidro e papel', 1);

INSERT INTO ponto_material (id_ponto, id_material) VALUES (1, 1), (1, 2), (1, 3);

-- Guia de exemplo
INSERT INTO guias_informativos (titulo, conteudo, id_autor) VALUES
('Como descartar eletrônicos corretamente',
 'Eletrônicos contêm metais pesados. Leve a pontos de coleta credenciados. Nunca descarte no lixo comum.',
 1);
