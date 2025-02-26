package at.rewe.definition;

import at.rewe.CellReference;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class DefinitionApiImpl implements DefinitionApi {

    @Override
    public Map<String, CellReference> getDefinitions(String documentType) {
        return Map.of();
    }
}
