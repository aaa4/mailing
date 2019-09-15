#  Simple send mail
Using from  java and html thymeleaf template. To send email set this props:

    spring.mail.username=${MAIL_MY_ROBOT_LOGIN2}
    spring.mail.password=${MAIL_MY_ROBOT_PASSWORD2}
    spring.mail.host = smtp.yourEmailProvider.itsDomain

To pass some data to email template add it to the `ctx` instance of `Context` class in the `sendHtmlMessage` method.
