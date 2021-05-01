package heig.smtp;

import heig.prank.Email;

import java.util.List;

public interface ISMTPClient {
    void sendEmails(List<Email> emails);
}
