# Projeto Controle Financeiro de contas a pagar e receber

Projeto desenvolvido na linguagem de programação JAVA com o framework Spring-MVC com o css do bootstrap. Nesse projeto, tanto o backend quanto o front está em apenas um pacote. O usuário após efetuar o login na aplicação, o mesmo terá acesso ao sistema, que permitirá o mesmo realziar o cadastro: Tipos de Despesas, Tipos de Entradas, Usuários, Lançamento de Despesas e Receitas e obter um relatório do que ele tem naquele mês para receber ou pagar. 

1) Tela inical do Sistema, o mesmo terá o menu de acesso para as funções desejadas. 
![image](https://github.com/user-attachments/assets/a8b9355e-ac78-4fe8-ab70-5075d723a6f3)

2) Cadastro de Tipos de Despesas (ex: Luz, água, etc)
![image](https://github.com/user-attachments/assets/2f37b6d4-7d76-40c5-b56a-f89fd485c462)

3) Cadastro de tipos de Receitas
![image](https://github.com/user-attachments/assets/c8752f6c-0418-4a85-9803-5d27509eecb9)

4) Cadastro de Usuários
![image](https://github.com/user-attachments/assets/962e5495-1926-492a-bbf6-1648fd54f3e2)

5) Lançamento de Despesas, Receita (Telas semelhantes)
![image](https://github.com/user-attachments/assets/72d93d25-fd5e-4c44-87fc-a3d18db1d647)

6) Relatório de Despesas e Receitas (Telas semelhantes)
![image](https://github.com/user-attachments/assets/fe5e80e2-0844-4ad8-9fdc-e21977fe2ae0)

## 🚀 Montagem de Ambiente

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento e teste.

Consulte **[Implantação](#-implanta%C3%A7%C3%A3o)** para saber como implantar o projeto.

### 📋 Pré-requisitos

Para que a aplicação rode localmente, devemos ter instalado na máquina os itens listados abaixo: <br>

Banco de Dados Postgress 16.1<br>
Java versão 11<br>
maven 3.8.7

### 🔧 Instalação passo a passo

1) Realizar o clone do projeto - git clone [https://github.com/bgrbarbosa/eventos.git]<br>
![image](https://github.com/user-attachments/assets/5f96fcaf-33a8-45ca-9740-42ff4bee558c)


2) Para a build do front-end

   2.1) Na pasta raiz do projeto rodar o comando: npm install<br>
   2.2) ng serve

3) Para buildar o back-end

   3.1) Na pasta raiz da api (eventos-api) rodar o comando: mvn clean install (Para instalar as bibliotecas do projeto) <br>
   3.2) Rodar o comando : spring-boot:run (Para startar o projeto) 
 
## ⚙️ Executando os testes

Após buildar o front-end e o back-end da aplicação, abrir o navegaor na url http://localhost:4200/home e a tela contendo os eventos cadastrados deverá ser exibida, caso exista evento cadastrado. Caso exista evento e o mesmo não apareca na tela, verifique se a api está de pé.


## 📦 Implantação

1) Criando um build de deploy do backend: mvn clean install -DskipTests
2) Criando um build de deploy do frontend: ng build

## 📌 Versão

V1.0.0 - Versão Beta. 

## ✒️ Autores

Projetado e Desenvolvido por: Bruno Gaspar Romeiro Barbosa.<br>
Contato: bgrbarbosa@hotmail.com - Cel: (24)98854-9631

## 📄 Licença

Este projeto foi criado para fins educativo, sendo livre para ser clonado e alterado de acordo com a necessidade dos usuários.








