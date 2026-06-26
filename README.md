# 🌱 EcoColeta — MVP

**Trabalho de Engenharia de Software II — 2026**  
Grupo: Julio Cezar Melo · Jean José · Gabriel Vieira

Sistema que conecta cidadãos a pontos de descarte correto de resíduos especiais.

---

## 📋 Requisitos Funcionais implementados

| RF | Descrição | Onde está |
|----|-----------|-----------|
| RF-01 | Cadastro de usuários (cidadão, empresa, admin) | `UsuarioService.cadastrar()` |
| RF-02 | Login e autenticação | `UsuarioService.login()` |
| RF-03 | Visualização de pontos de coleta | `PontoColetaService.listarTodos()` |
| RF-04 | Cadastro de pontos por admin/empresa | `PontoColetaService.cadastrar()` |
| RF-05 | Filtro por tipo de material | `PontoColetaService.filtrarPorMaterial()` |
| RF-06 | Guias informativos sobre descarte | `GuiaInformativoService` |
| RF-07 | Avaliação e comentários nos pontos | `AvaliacaoService` |

---

## 🗂️ Estrutura do Projeto

```
EcoColeta/
├── src/
│   └── ecocoleta/
│       ├── controller/   → Main.java  (menu de texto / entrada do programa)
│       ├── service/      → Regras de negócio e validações
│       ├── dao/          → Acesso ao banco de dados (JDBC)
│       ├── model/        → Classes de dados (Usuario, PontoColeta, ...)
│       └── util/         → ConexaoDB.java
├── sql/
│   └── ecocoleta.sql     → Script completo de criação do banco
├── lib/
│   └── mysql-connector-j-*.jar   ← você coloca aqui
└── README.md
```

---

## 🚀 Como configurar e rodar (Apache NetBeans)

### 1. Instalar o MySQL
- Baixe o MySQL Community Server em https://dev.mysql.com/downloads/mysql/
- Instale e anote a **senha do root**

### 2. Criar o banco de dados
Abra o **MySQL Workbench** (ou o terminal MySQL) e execute:

```sql
-- Cole o conteúdo inteiro do arquivo sql/ecocoleta.sql
```

Ou pelo terminal:
```bash
mysql -u root -p < sql/ecocoleta.sql
```

### 3. Baixar o Driver JDBC do MySQL
1. Acesse: https://dev.mysql.com/downloads/connector/j/
2. Baixe o arquivo `.jar` (ex: `mysql-connector-j-8.x.x.jar`)
3. Coloque o `.jar` dentro da pasta `lib/` do projeto

### 4. Abrir o projeto no NetBeans
1. **File → Open Project** → selecione a pasta `EcoColeta`
2. Clique com botão direito no projeto → **Properties → Libraries**
3. Clique em **Add JAR/Folder** → selecione o `.jar` dentro de `lib/`
4. Confirme com **OK**

### 5. Configurar a conexão com o banco
Abra `src/ecocoleta/util/ConexaoDB.java` e edite:

```java
private static final String USUARIO = "root";
private static final String SENHA   = "SUA_SENHA_AQUI";
```

### 6. Executar
- Clique com botão direito em `Main.java` → **Run File** (ou `Shift + F6`)
- O menu do sistema será exibido no console

---

## 🖥️ Exemplo de uso no console

```
====================================
  Bem-vindo ao EcoColeta v1.0 MVP
====================================
=== Testando conexão com o banco de dados ===
Conexão estabelecida com sucesso!

--- MENU DE ACESSO ---
1. Login
2. Cadastrar novo usuário
0. Sair
Opção: 1

-- Login --
E-mail: admin@ecocoleta.com
Senha: admin123
Login bem-sucedido! Bem-vindo(a), Administrador.

--- MENU PRINCIPAL --- [Administrador | ADMIN]
1. Listar pontos de coleta
...
```

---

## 🐙 Integrando com o GitHub

### Primeira vez (criar repositório)
```bash
# Dentro da pasta EcoColeta:
git init
git add .
git commit -m "feat: estrutura inicial do projeto EcoColeta"
git branch -M main
git remote add origin https://github.com/SEU_USUARIO/ecocoleta.git
git push -u origin main
```

### Fluxo de trabalho em grupo

```bash
# Atualizar antes de trabalhar
git pull origin main

# Criar uma branch para sua feature
git checkout -b feature/cadastro-usuario

# Após terminar, subir
git add .
git commit -m "feat: implementa cadastro de usuario RF-01"
git push origin feature/cadastro-usuario

# No GitHub, abrir um Pull Request para a branch main
```

### Boas mensagens de commit (Conventional Commits)
```
feat:     nova funcionalidade
fix:      correção de bug
docs:     atualização de documentação
refactor: melhoria de código sem mudar comportamento
test:     adição ou correção de testes
```

---

## 🗃️ Modelo do Banco de Dados

```
usuarios
  id | nome | email | senha | tipo | ativo | criado_em

pontos_coleta
  id | nome | endereco | latitude | longitude | descricao | ativo | id_responsavel

tipos_material
  id | nome

ponto_material  (N:N)
  id_ponto | id_material

avaliacoes
  id | nota | comentario | id_usuario | id_ponto | criado_em

guias_informativos
  id | titulo | conteudo | id_autor | criado_em
```

---

## ⚙️ Tecnologias utilizadas

- **Java** (JDK 11+) — Linguagem principal
- **MySQL** — Banco de dados relacional
- **JDBC** — Conexão Java ↔ MySQL (sem frameworks)
- **Apache NetBeans** — IDE recomendada
- **GitHub** — Controle de versão

---

## 👤 Usuário padrão para testes

| Campo | Valor |
|-------|-------|
| E-mail | admin@ecocoleta.com |
| Senha  | admin123 |
| Tipo   | ADMIN |

---

## 📌 Observações importantes

- **Não** suba o arquivo `ConexaoDB.java` com sua senha real. Adicione ao `.gitignore`:
  ```
  src/ecocoleta/util/ConexaoDB.java
  lib/
  ```
  E documente no README que cada desenvolvedor deve configurar localmente.
- Em produção, as senhas devem ser armazenadas com **hash** (ex: `BCrypt`). Para o MVP, o texto simples é aceitável para fins didáticos.
