package at.rewe.lov;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ListOfValueApiImplTest {

  private final ListOfValueApiImpl lovApi = new ListOfValueApiImpl();

  @Test
  void shouldProvideAllItems() {
    assertThat(lovApi.getAll("tax")).hasSize(2);
  }

  @Test
  void shouldProvideSingleItem() {
    assertThat(lovApi.getSingle("tax", "10")).isNotNull();
  }
}