## Technology Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA (Hibernate)**
- **H2 In-Memory Database**
- **Spring Scheduler (`@Scheduled`)**
- **RESTful API (JSON)**
- **Swagger / OpenAPI**

---

## API Testing (Swagger)

All APIs are fully documented and can be tested via **Swagger UI**:

- 👉 **http://localhost:8080/swagger-ui/index.htm**

### Swagger Features

- Clear API descriptions
- Request / response models
- Interactive testing for:
  - Buy / Sell orders
  - Wallet balance
  - Latest aggregated price
  - Trading history

### Screenshot
### Price API
Retrieve the latest **best aggregated prices** collected from Binance and Huobi.

- **GET** `/api/prices`  
  Returns the latest best bid (SELL) and ask (BUY) prices for supported trading pairs.
<img width="1457" height="775" alt="image" src="https://github.com/user-attachments/assets/91034d4c-cd1c-444d-b119-e8d349741aae" />

---

### Trade API
Execute trades based on the **latest aggregated price**.

- **POST** `/api/trades`  
  Place a BUY or SELL order using the current best price.
<img width="1461" height="874" alt="image" src="https://github.com/user-attachments/assets/fadc6d4b-b87b-403a-b16b-f62e696ea517" />


- **GET** `/api/trades/history`  
  Retrieve the user’s trading history.
<img width="1461" height="874" alt="image" src="https://github.com/user-attachments/assets/8068ef13-4737-49e7-96f9-54b7ffd20126" />

---

### Wallet API
View the current wallet balance.

- **GET** `/api/wallet`  
  Returns the user’s crypto and USDT balances.

<img width="1461" height="874" alt="image" src="https://github.com/user-attachments/assets/682294f6-b28e-41ed-a1d7-ac97b2afc9fc" />

