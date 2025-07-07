package com.xabier.desafio.controller;

import com.xabier.desafio.repository.UserRepository;
import com.xabier.desafio.security.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 * 4. Pruebas de Integración (REST, contexto completo)
 * 
 * 
 * ¡Muy buena pregunta!  
Ambas clases de test levantan el **contexto completo de Spring Boot**, pero la diferencia clave está en **cómo se hacen las peticiones HTTP**:

---

### 1. `@SpringBootTest` + `@AutoConfigureMockMvc` (**MockMvc**)

- **Levanta el contexto completo** de Spring Boot (beans, repositorios, servicios, etc.).
- **NO levanta un servidor web real** (no hay Tomcat/Jetty escuchando en un puerto).
- Usa `MockMvc` para simular peticiones HTTP **dentro del proceso Java**, sin salir a la red.
- Es muy rápido y no depende de puertos ni de la red.
- Ejemplo:  
  ```java
  mockMvc.perform(post("/users/api/v1")...)
  ```
  ejemplo 
  mockMvc.perform(post("/users/api/v1")...)

---

### 2. `@SpringBootTest(webEnvironment = RANDOM_PORT)` + `TestRestTemplate` (**RestTemplate**)

- **Levanta el contexto completo** de Spring Boot **y un servidor web real** en un puerto aleatorio.
- Puedes hacer peticiones HTTP reales a `http://localhost:{port}/...`.
- Usa `TestRestTemplate` (o `RestTemplate`) para enviar peticiones HTTP **como lo haría un cliente externo**.
- Es más realista para probar la integración completa, incluyendo filtros, encoding, etc.
- Ejemplo:  
  ```java
  restTemplate.postForEntity("http://localhost:" + port + "/users/api/v1", ...)
  ```

---

### **Resumen de diferencias**

| MockMvc (tu clase actual)           | RestTemplate/TestRestTemplate           |
|-------------------------------------|-----------------------------------------|
| No levanta servidor real            | Levanta servidor real (puerto random)   |
| Peticiones simuladas en memoria     | Peticiones HTTP reales (localhost)      |
| Más rápido, sin red                 | Más realista, incluye stack HTTP real   |
| No prueba configuración de red real | Prueba stack HTTP completo              |

---

**Ambos levantan el contexto completo, pero RestTemplate prueba el endpoint como lo haría un cliente externo real.**  
Usa MockMvc para rapidez y control, y RestTemplate para integración end-to-end real.

 */


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    void cleanDb() {
        userRepository.deleteAll();
    }

    @Test
    void addUser_returnsCreatedUserView() throws Exception {
        String json = "{"
                + "\"name\": \"Juan Rodriguez\","
                + "\"email\": \"juan@rodriguez.org\","
                + "\"password\": \"Hun77er2\","
                + "\"phones\": [{\"number\": \"1234567\", \"citycode\": \"1\", \"contrycode\": \"57\"}]"
                + "}";

        // Generar un token JWT para la autenticación 
        String token = jwtUtil.generateToken("juan@rodriguez.org");

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Juan Rodriguez"))
                .andExpect(jsonPath("$.email").value("juan@rodriguez.org"));
    }
}