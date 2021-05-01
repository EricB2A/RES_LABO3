package heig.smtp;

import heig.model.prank.Email;

import java.util.List;

public interface ISmtpClient {
    void sendEmails(List<Email> emails);
}
