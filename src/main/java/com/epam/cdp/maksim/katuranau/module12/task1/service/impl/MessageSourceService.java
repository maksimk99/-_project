package com.epam.cdp.maksim.katuranau.module12.task1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageSourceService {

    private MessageSource messageSource;

    @Autowired
    public MessageSourceService(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getLocaleMessage(final String propertyName) {
        return messageSource.getMessage(propertyName, null, LocaleContextHolder.getLocale());
    }
}
