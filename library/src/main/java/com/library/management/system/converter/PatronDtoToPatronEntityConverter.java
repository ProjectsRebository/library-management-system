package com.library.management.system.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.library.management.system.dto.PatronDto;
import com.library.management.system.models.Patron;

@Component
public class PatronDtoToPatronEntityConverter implements Converter<PatronDto, Patron> {

    @Override
    public Patron convert(PatronDto source) {
        return Patron.builder()
                .name(source.getName())
                .contactInformation(source.getContactInformation())
                .build();
    }
}
