package at.rewe.lov;

import java.util.Set;

public interface ListOfValueApi {

  Set<LovItem> getAll(String lovId);

  LovItem getSingle(String lovId, String code);
}
