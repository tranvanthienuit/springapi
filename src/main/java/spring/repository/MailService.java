package spring.repository;

import spring.model.Mail;

public interface MailService {
    public void sendEmail(Mail mail);

    public boolean checkMail(String mail);
}
