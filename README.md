# Controle de Despesas (Java + JDBC)

Um sistema simples de controle de despesas em Java, construído com JDBC e console, ideal para aprender boas práticas com banco de dados, tratamento de entrada e organização de código.

## Funcionalidades

- Cadastrar, listar, buscar, atualizar e remover categorias e despesas.
- Filtrar despesas por período.
- Calcular total de despesas por mês.
- Proteção contra entradas inválidas (`int`, `BigDecimal`, datas) com leitura segura.
- Verificação de integridade (não permite remover categoria com despesas associadas).
- Feedback claro ao usuário e tratamento de exceções.

## Tecnologias utilizadas

- Java 11+  
- JDBC + MySQL  
- Estrutura MVC simplificada (`app`, `dao`, `model`, `db`)  
- Arquivo de configuração `.properties` com credenciais (protegido no `.gitignore`)

## 🚀 Como executar o projeto

### 1. Crie o banco de dados no MySQL

Execute o script abaixo no seu MySQL Workbench ou terminal:

```sql
CREATE DATABASE IF NOT EXISTS controle_despesas;
USE controle_despesas;

CREATE TABLE IF NOT EXISTS categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS despesa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    data DATE NOT NULL,
    categoria_id INT,
    FOREIGN KEY(categoria_id) REFERENCES categoria(id) ON DELETE CASCADE ON UPDATE CASCADE
);
```

### 2. Crie o arquivo db.properties com suas credenciais de acesso

Na pasta database/, crie um arquivo chamado db.properties com este conteúdo:
```bash
# Exemplo de configuração local
db.url=jdbc:mysql://localhost:3306/controle_despesas
db.user=seu_usuario
db.password=sua_senha
```

### 3. Compile e execute o projeto
✅ Via terminal:
```bash
  javac -d bin src/app/*.java src/dao/*.java src/model/*.java src/db/*.java
  java -cp bin app.ProgramaPrincipal
```

✅ Ou via IntelliJ/VSCode:
- Crie um novo projeto Java
- Adicione a pasta src/ como raiz de código
- Configure o caminho do db.properties para apontar corretamente
- Rode a classe ProgramaPrincipal
