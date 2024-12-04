package at.rewe.lov;

import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;


@Service
class ListOfValueApiImpl implements ListOfValueApi {

  @Override
  public Set<LovItem> getAll(String lovId) {
    return switch (lovId) {
      case "tax" -> Set.of(
          new LovItem("10", "10 Prozent"),
          new LovItem("20", "20 Prozent")
      );
      case "packing_type" -> Set.of(
          new LovItem("BD", "Bund"),
          new LovItem("KI", "Kiste")
      );
      case "measurement_unit" -> Set.of(
          new LovItem("cm", "Centimeter"),
          new LovItem("mm", "Millimeter")
      );
      case "country_of_origin", "country_of_production" -> Set.of(
          new LovItem("AUT", "Österreich"),
          new LovItem("CRI", "Costa Rica")
      );
      case "un_number" -> Set.of(
          new LovItem("as", "Type 1"),
          new LovItem("ct", "Type 2")
      );
      default -> Set.of();
    };
  }

  @Override
  public LovItem getSingle(String lovId, String code) {
    return getAll(lovId).stream()
        .filter(l -> Objects.equals(l.code(), code))
        .findFirst()
        .orElseThrow();
  }
}
