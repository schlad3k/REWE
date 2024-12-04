package at.rewe.definition;

import at.rewe.CellReference;

import java.util.Map;

public interface DefinitionApi {

    Map<String, CellReference> getDefinitions(String documentType);
}
