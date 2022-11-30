# API-Clothing-Store
Repositorio para entrega final da matéria Desenvolvimento de Software Corporativo do curso Sistemas de Informação

Essa API RESTful vai permitir para um ecommerce ter um controle de suas vendas e estoque de suas roupas.

Dentro das funções temos:

[**Venda**](#reference/recursos/venda)

[**Carrinho**](#reference/recursos/carrinho)

[**Clientes**](#reference/recursos/clientes)

[**Roupas**](#reference/recursos/roupas)

[**Marca**](#reference/recursos/marca)

[**Sexo**](#reference/recursos/sexo)

[**Tamanho**](#reference/recursos/tamanho)

[**Foto**](#reference/recursos/foto)

# Diagrama de Classes 
![image](https://user-images.githubusercontent.com/89109699/204794302-68c35471-da9e-4a7e-9ef2-8ca019e3bf61.png)

## Métodos
Requisições para a API devem seguir os padrões:
| Método | Descrição |
|---|---|
| `GET` | Retorna informações de um ou mais registros. |
| `POST` | Utilizado para criar um novo registro. |
| `PUT` | Atualiza dados de um registro ou altera sua situação. |
| `DELETE` | Remove um registro do sistema. |

## Respostas

| Código | Descrição |
|---|---|
| `200` | Requisição executada com sucesso (success).|
| `400` | Erros de validação ou os campos informados não existem no sistema.|
| `401` | Dados de acesso inválidos.|
| `404` | Registro pesquisado não encontrado (Not found).|


# Venda [/venda]

Vendas realizadas, novas vendas e cancelar.

## Listar

## Novo

## Editar

## Deletar


# Carrinho [/carrinho]

Carrinho onde o cliente vai adicionandos os produtos que irá comprar.

## Listar

## Novo

## Editar

## Deletar

# Clientes [/clientes]

Dados de todos os clientes registrados no site

## Listar

## Novo

## Editar

## Deletar

# Roupas [/roupas]

Estoque de todas as roupas no site

## Listar

## Novo

## Editar

## Deletar

# Marca [/marca]

Categoria dentro de roupas.

## Listar

## Novo

## Editar

## Deletar

