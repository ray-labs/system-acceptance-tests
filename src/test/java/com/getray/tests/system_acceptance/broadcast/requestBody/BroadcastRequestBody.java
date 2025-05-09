package com.getray.tests.system_acceptance.broadcast.requestBody;



import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BroadcastRequestBody(
        String domainId,
        String type,
        List<Integer> toUsers,
        Integer toDomain,
        TranslationList translations
) {
}
