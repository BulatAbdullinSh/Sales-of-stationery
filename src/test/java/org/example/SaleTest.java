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
class SaleTest {
  @Container
  static DockerComposeContainer<?> compose = new DockerComposeContainer<>(
      Path.of("docker-compose.yml").toFile()
  );

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldPerformSale() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.post("/sales/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    // language=JSON
                    """
                        {
                          "productId": 3,
                          "price": 30,
                          "qty": 3
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
                       "sale": {
                           "id": 1,
                           "productId": 3,
                           "name": "карандаш",
                           "price": 30,
                           "qty": 3
                         }
                    }
                    """
            )
        );

    mockMvc.perform(
            MockMvcRequestBuilders.get("/products/getById")
                .queryParam("id", String.valueOf(3))
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.content().json(
                // language=JSON
                """
                    {
                      "product": {
                        "id": 3,
                        "name": "карандаш",
                        "price": 30,
                        "qty": 117,
                        "image": "noimage.png"
                      }
                    }
                    """
            )
        );
  }
}
