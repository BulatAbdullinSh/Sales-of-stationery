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
                          "name": "Биг Мак",
                          "price": 300,
                          "qty": 10,
                          "image": "noimage.png"
                        },
                        {
                          "id": 2,
                          "name": "greeck mac",
                          "price": 200,
                          "qty": 10,
                          "image": "noimage.png"
                        },
                        {
                          "id": 3,
                          "name": "burger",
                          "price": 150,
                          "qty": 10,
                          "image": "noimage.png"
                        },
                        {
                          "id": 4,
                          "name": "coffee",
                          "price": 150,
                          "qty": 10,
                          "image": "noimage.png"
                        },
                        {
                          "id": 5,
                          "name": "tea",
                          "price": 50,
                          "qty": 5,
                          "image": "noimage.png"
                        },
                        {
                          "id": 6,
                          "name": "water",
                          "price": 50,
                          "qty": 0,
                          "image": "noimage.png"
                        }
                      ]
                    }
                    """
            )
        );

    mockMvc.perform(
            MockMvcRequestBuilders.get("/products/getById")
                .queryParam("id", String.valueOf(2))
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.content().json(
                // language=JSON
                """
                    {
                      "product": {
                        "id": 2,
                        "name": "greeck mac",
                        "price": 200,
                        "qty": 10,
                        "image": "noimage.png"
                      }
                    }
                    """
            )
        );

    mockMvc.perform(
            MockMvcRequestBuilders.get("/products/getById/{id}", 2)
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.content().json(
                // language=JSON
                """
                    {
                      "product": {
                        "id": 2,
                        "name": "greeck mac",
                        "price": 200,
                        "qty": 10,
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
                          "name": "Java Book",
                          "price": 1000,
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
                    "name": "Java Book",
                    "price": 1000,
                    "qty": 10,
                    "image": "noimage.png"
                  }
                }
                """
            )
        );
  }
}
