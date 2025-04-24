Projeto Banco Digital JPA
Este projeto tem como objetivo desenvolver a estrutura básica de um sistema bancário digital. 
A aplicação foi construída utilizando o framework Spring Boot e a tecnologia JPA (Java Persistence API) para o gerenciamento de dados.

* Tecnologias Utilizadas
  **Spring Boot:** Framework Java para a criação de aplicações standalone e prontas para produção.
  
  **JPA (Java Persistence API):** API Java para persistência de dados em bancos de dados relacionais.
  
  **Maven:** Ferramenta de gerenciamento de dependências e build do projeto Java.
  
  **H2 Database:** Banco de dados relacional em memória, utilizado para o desenvolvimento e testes desta aplicação.
  
  **Postman:** Utilizado para testar as APIs e funcionalidades implementadas.

* Implementações Concluídas

Atualmente, as seguintes entidades foram implementadas e testadas:

Atualmente, as seguintes entidades foram implementadas e testadas:

* **Cliente:** Permite o cadastro de informações básicas do cliente, como CPF, nome, data de nascimento e endereço. A estrutura para a classificação em categorias (Comum, Super e Premium) também foi definida na entidade.
* **Conta:** Foi criada a estrutura básica para a entidade de Conta, contemplando atributos como número da conta, tipo (corrente ou poupança) e saldo.

  As funcionalidades básicas dessas entidades foram testadas utilizando o Postman e estão funcionando conforme o esperado, com os dados sendo persistidos utilizando o banco de dados em memória H2.

* Implementações Pendentes

A entidade de **Cartão** ainda não foi implementada nesta versão inicial. 
Portanto, as funcionalidades relacionadas à emissão de cartões de crédito e débito, operações de pagamento, alteração de senha e status, ajuste de limites e seguros não estão presentes.

* Próximos Passos

Os próximos passos para o desenvolvimento deste projeto incluem a implementação completa da entidade de Cartão, seguindo as regras de negócio definidas. 
Implementar as operações bancárias básicas (exibição de saldo, transferências via Pix, depósitos, saques) e as funcionalidades específicas para contas correntes (taxa de manutenção) e contas poupança (rendimentos).
