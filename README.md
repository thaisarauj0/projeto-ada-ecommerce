# Projeto ADA & Meli | E-Commerce

## Visão Geral

**Objetivo:**  
Simular um fluxo básico de E-Commerce:
- Cadastro de clientes e produtos
- Criação de pedidos com itens, pagamento e entrega
- Regras de negócio implementadas
- Notificações simuladas por e-mail

**Abordagem:**
- Arquitetura em camadas (MVC)
- Orientação a objetos e princípios SOLID
- Uso de Generics e interfaces
- Base de dados em memória

**Tecnologias:**
- Java puro (sem frameworks)
- Repositórios genéricos, serviços de domínio, controllers e views simuladas (CLI)

---

## Arquitetura

**Camadas:**

- **Model (Domínio)**  
  Entidades: `Clients`, `Products`, `Orders`, `OrderItem`, enums `OrderStatus`, `PaymentStatus`  
  Regras de estado encapsuladas (ex.: itens só podem ser modificados enquanto pedido estiver `Aberto`)

- **Repository (Persistência)**  
  Interface genérica `IRepository<T, ID>`: `add`, `findById`, `listAll`, `update`  
  Implementação em memória: `MemoryRepository<T, ID>` (uso de Map)  
  Reutilizável para Cliente, Produto e Pedido

- **Service (Lógica de Negócio)**  
  `OrdersService`: regras de pedidos (criar, adicionar/remover itens, iniciar/confirmar pagamento, entrega)  
  `INotifier / SimpleNotifier`: envio de notificações simuladas

- **Controller (MVC)**  
  `ClientsController`, `ProductsController`, `OrdersController`  
  Dependem de interfaces (DIP) para repositórios e notificadores

- **Main (Entrada)**  
  `Main` ou `AppMain`: inicializa repositórios, controllers, serviços e menu CLI

---

## Fluxo Principal

- Cadastrar Cliente/Produto (ID gerado via UUID)
- Cadastrar Pedido (associado a cliente existente)
- Consultar Cliente, Produto ou Pedido por ID
- **List All Clients**: exibir todos os clientes
- Detalhes exibidos: ID, nome, status, total, etc.

---

## Componentes Principais

| Camada      | Componentes                          | Papel |
|------------|-------------------------------------|------|
| Model       | `Client`, `Product`, `Order`, `OrderItem`, `OrderStatus`, `PaymentStatus` | Entidades e regras de estado |
| Repository  | `IRepository<T, ID>`, `MemoryRepository<T, ID>` | Acesso genérico a dados |
| Service     | `OrdersService`                     | Lógica de negócios de pedidos |
| Notifier    | `INotifier`, `SimpleNotifier`       | Envio de notificações simuladas |
| Controller  | `ClientsController`, `ProductsController`, `OrdersController` | Interação UI ↔ Lógica |
| View        | `ClientView`, `ProductView`, `OrdersView` | UI simulada (CLI) |
| Main        | `AppMenuCadastroEnglish` ou `Main`  | Inicialização e menu CLI |

---

## SOLID Aplicado

- **SRP:** entidades, serviços e controllers têm responsabilidades únicas
- **OCP:** serviços e repositórios extensíveis via interfaces
- **LSP:** entidades e serviços respeitam contratos
- **ISP:** interfaces pequenas e focadas (`IRepository`, `INotifier`)
- **DIP:** camadas superiores dependem de abstrações, não implementações concretas

---

## Generics e Interfaces

- **Generics:** `IRepository<T, ID>` e `MemoryRepository<T, ID>` reutilizáveis para Cliente, Produto e Pedido
- **Interfaces:** `INotifier` define contrato de notificações; controllers e serviços dependem de interfaces para facilitar testes e manutenção

---

## Estrutura
```
src

├── controller
│ ├── ClientsController
│    ├── OrdersController
│    └── ProductsController
│
├── model
│    ├── Clients
│    ├── OrderItem
│    ├── Orders
│    ├── OrderStatus
│    ├── Payment
│    ├── PaymentStatus
│    └── Products
│
├── repository
│    ├── IRepository
│    └── MemoryRepository
│
├── services
│    ├── INotificador
│    ├── NotificadorSimples
│    └── OrdersService
│
├── view
│    ├── ClientView
│    ├── OrdersView
│    └── ProductView
│
└── Main
```