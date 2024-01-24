package com.dom.tasks.service;


import com.dom.tasks.domain.MailType;
import com.dom.tasks.domain.user.User;

import java.util.Properties;

public interface MailService {

    void sendEmail(User user, MailType type, Properties params);

}