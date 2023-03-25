package by.nika_doroshkevich.service;

import by.nika_doroshkevich.model.Email;
import by.nika_doroshkevich.model.EmailsThread;

import java.util.List;

public interface EmailsThreadService {

    List<EmailsThread> getEmailsBySocketId(String socketId);

    EmailsThread storeEmail(Integer socketId, Email email);
}
