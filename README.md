Simple send mail from template class
To send email set this props:

<code>
spring.mail.username=${MAIL_MY_ROBOT_LOGIN2}
spring.mail.password=${MAIL_MY_ROBOT_PASSWORD2}
spring.mail.host = smtp.yourEmailProvider.itsDomain`
</code>

To pass some data to email template add it to the  `ctx` instance of Context class in the sendHtmlMessage method.
