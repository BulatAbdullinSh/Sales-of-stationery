package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Path;

@Testcontainers
@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class CRUDTest {
  @Container
  static DockerComposeContainer<?> compose = new DockerComposeContainer<>(
      Path.of("docker-compose.yml").toFile()
  );

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldPerformProductCRUD() throws Exception {
    // getAll
    mockMvc.perform(
            MockMvcRequestBuilders.get("/products/getAll")
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.content().json(
                // language=JSON
                """
                    {
                        "products": [
                          {
                            "id": 1,
                            "name": "тетрадь",
                            "price": 50,
                            "qty": 100,
                            "image": "noimage.png"
                          },
                          {
                            "id": 2,
                            "name": "ручка",
                            "price": 35,
                            "qty": 200,
                            "image": "noimage.png"
                          },
                          {
                            "id": 3,
                            "name": "карандаш",
                            "price": 30,
                            "qty": 120,
                            "image": "noimage.png"
                          },
                          {
                            "id": 4,
                            "name": "линейка",
                            "price": 45,
                            "qty": 200,
                            "image": "noimage.png"
                          },
                          {
                            "id": 5,
                            "name": "пенал",
                            "price": 120,
                            "qty": 110,
                            "image": "noimage.png"
                          },
                          {
                            "id": 6,
                            "name": "кисточка",
                            "price": 45,
                            "qty": 67,
                            "image": "noimage.png"
                          }
                        ]
                      }
                    """
            )
        );

    mockMvc.perform(
            MockMvcRequestBuilders.get("/products/getById")
                .queryParam("id", String.valueOf(1))
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.content().json(
                // language=JSON
                """
                    {
                        "product": {
                              "id": 1,
                              "name": "тетрадь",
                              "price": 50,
                              "qty": 100,
                              "image": "noimage.png"
                            }
                    }
                    """
            )
        );

    mockMvc.perform(
            MockMvcRequestBuilders.get("/products/getById/{id}", 1)
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.content().json(
                // language=JSON
                """
                    {
                        "product": {
                          "id": 1,
                          "name": "тетрадь",
                          "price": 50,
                          "qty": 100,
                          "image": "noimage.png"
                        }
                    }
                    """
            )
        );

    mockMvc.perform(
            MockMvcRequestBuilders.get("/products/getById")
                .queryParam("id", String.valueOf(999))
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isNotFound()
        );

    mockMvc.perform(
            MockMvcRequestBuilders.post("/products/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    // language=JSON
                    """
                        {
                           "id": 0,
                           "name": "блокнот",
                           "price": 105,
                           "qty": 10
                         }
                        """
                )
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.content().json(
                // language=JSON
                """
                {
                  "product": {
                     "id": 7,
                     "name": "блокнот",
                     "price": 105,
                     "qty": 10,
                     "image": "noimage.png"
                   }
                }
                """
            )
        );
  }
}
