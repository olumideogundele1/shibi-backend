package com.shibi.app.utils;

import com.shibi.app.config.MailConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by User on 08/06/2018.
 */
@Service
@PropertySource("classpath:application.properties")
public class SmtpMailSender {

    private Environment env;
    private MailConfig mailConfig;
    private EmailService emailService;

    @Value("${email-banner-image}")
    private String emailLogo;

    @Value("${poweredby}")
    private String poweredBy;

    @Autowired
    public SmtpMailSender(Environment env, MailConfig mailConfig, EmailService emailService) {
        this.env = env;
        this.mailConfig = mailConfig;
        this.emailService = emailService;
    }

    private static Logger logger = LoggerFactory.getLogger(SmtpMailSender.class);

    /**
     * Send mail using this.
     * Details to be displayed have to be seperated with <br/>
     * @param to
     * @param subject
     * @param body
     */
    @Async
    public void sendTo(String[] to, String subject, String moduleName, String details,String creatorEmail,String url, boolean isNew) {

        int randomNumbers = new Random().nextInt(1000) + 1;

        try {
            MimeMessage message = new MimeMessage(mailConfig.getSession());
            MimeMessageHelper mailHelper = new MimeMessageHelper(message, true);
            mailHelper.setSubject(subject);
            mailHelper.setTo(to);
            mailHelper.setText(htmlMail(moduleName,details,env.getProperty("mail_url")+url+"/"+Long.valueOf(randomNumbers+""),creatorEmail, isNew),true);
            //logger.info("About to send mail....");
            Transport.send(message);
            //logger.info("Mail Sent....");
        } catch (MessagingException e) {
            logger.error("Exception : ",e.getMessage());
        }

    }

    @Async
    public void sendApprovalStatus(String to, String subject, String moduleName, String details,String approvalEmail) {

        try {

            MimeMessage message = new MimeMessage(mailConfig.getSession());
            MimeMessageHelper mailHelper = new MimeMessageHelper(message, true);
            mailHelper.setSubject(subject);
            mailHelper.setTo(to);
            mailHelper.setText(htmlApprovalStatusMail(moduleName,details,approvalEmail),true);
            Transport.send(message);

        } catch (MessagingException e) {
            logger.error("Exception : ",e);
        }

    }

    @Async
    public void sendMail(String from, String[] to, String subject, String title, String subtitle, String details) {

        MailContent mail = new MailContent();
        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);

        Map<String, Object> model = new HashMap<>();
        model.put("title", title);
        model.put("subtitle", subtitle);
     //   model.put("url", url);

        //client specific (Logo and powered)
        model.put("emailLogo", emailLogo);
        model.put("poweredBy", poweredBy);

        //Details from the module
        model.put("details", details);
        mail.setModel(model);

        try {
            emailService.sendSimpleMessage(mail);
        } catch (MessagingException | IOException e) {
            logger.error("Exception  occurred trying to send a mail", e);
        }

    }

    @Async
    public void sendNotificationToUser(String from, String[] to, String subject, String title, String subtitle, String details) {
        try {

            MimeMessage message = new MimeMessage(mailConfig.getSession());
            MimeMessageHelper mailHelper = new MimeMessageHelper(message, true);
            mailHelper.setSubject(subject);
            mailHelper.setTo(to);
            mailHelper.setText(htmlNotificationMail(details, title, subtitle), true);
            mailHelper.setFrom(from);
            Transport.send(message);
            //logger.info("Mail notification sent to the user");

        } catch (MessagingException e) {
            logger.error("Exception : ",e);
        }
    }

    /**
     * Request for approval Mail
     * Details to be displayed have to be seperated with <br/>
     * @param moduleName
     * @param details
     * @param approvalLink
     * @param disApprovalLink
     * @return
     */
    private String htmlMail(String moduleName,String details, String approvalLink, String creatorEmailAddress, boolean isNew){
        return "<style type=\"text/css\"> "
                + "#yiv5707510850 body {"
                + "width:100% !important;min-width:100%;margin:0;padding:0;}"
                + "#yiv5707510850 .yiv5707510850ReadMsgBody {"
                + "width:100%;}"
                + "#yiv5707510850 .yiv5707510850ExternalClass {"
                + "width:100%;}"
                + "#yiv5707510850 .yiv5707510850ExternalClass {"
                + "line-height:100%;}"
                + "#yiv5707510850 #yiv5707510850backgroundTable {"
                + "margin:0;padding:0;width:100% !important;line-height:100% !important;}"
                + "#yiv5707510850 img {"
                + "width:auto;max-width:100%;}"
                + "#yiv5707510850 img {"
                + "border:none;outline:none;text-decoration:none;}"
                + "#yiv5707510850 body.yiv5707510850outlook p {"
                + "display:inline;}"
                + "#yiv5707510850 body {"
                + "color:#313a45;font-family:\"Open Sans\", \"Arial\", sans-serif;}"
                + "#yiv5707510850 body {"
                + "font-size:16px;line-height:1.4;}"
                + "#yiv5707510850 a:hover {"
                + "color:#009999;}"
                + "#yiv5707510850 a:active {"
                + "color:#009999;}"
                + "#yiv5707510850 a:visited {"
                + "color:#009999;}"
                + "#yiv5707510850 body {"
                + "background-color:#f4f4f4;}"
                + "#yiv5707510850 .yiv5707510850button-block .yiv5707510850button a:visited {"
                + "color:#fff;}"
                + "#yiv5707510850 .yiv5707510850social a:visited {"
                + "font-weight:bold;color:#313a45;}"
                + "#yiv5707510850 .yiv5707510850social a:hover {"
                + "color:#009999;}"
                + "#yiv5707510850 .yiv5707510850social a:active {"
                + "color:#009999;}"
                + "@media screen and (max-width:600px){"
                + "#yiv5707510850 center {"
                + "min-width:0 !important;}"
                + "#yiv5707510850 body {"
                + "line-height:1.3 !important;}#yiv5707510850 table[class=\"yiv5707510850body\"] {"
                + "line-height:1.3 !important;}"
                + "#yiv5707510850 h1 {"
                + "font-size:24px !important;padding-bottom:20px !important;}"
                + "#yiv5707510850 .yiv5707510850h1 {"
                + "font-size:24px !important;padding-bottom:20px !important;}"
                + "#yiv5707510850 h1 span {"
                + "font-size:21px !important;}"
                + "#yiv5707510850 .yiv5707510850h1 span {"
                + "font-size:21px !important;}"
                + "#yiv5707510850 h2 {"
                + "font-size:21px !important;}"
                + "#yiv5707510850 p.yiv5707510850lead {"
                + "font-size:21px !important;padding-bottom:20px !important;}"
                + "#yiv5707510850 .yiv5707510850content {"
                + "padding:30px 15px 20px !important;}"
                + "#yiv5707510850 .yiv5707510850footer {"
                + "padding:20px 15px 30px !important;}"
                + "#yiv5707510850 .yiv5707510850button-block {"
                + "max-width:85% !important;}"
                + "#yiv5707510850 body {"
                + "width:100% !important;min-width:100% !important;}"
                + "#yiv5707510850 table {"
                + "max-width:580px !important;width:100% !important;}"
                + "#yiv5707510850 body {"
                + "}"
                + "#yiv5707510850 table {"
                + "}"
                + "#yiv5707510850 td {"
                + "}"
                + "#yiv5707510850 p {"
                + "}"
                + "#yiv5707510850 a {"
                + "}"
                + "#yiv5707510850 img {"
                + "width:auto !important;height:auto !important;}"
                + "}"
                + "@media screen and (max-width:480px){"
                + "#yiv5707510850 .yiv5707510850button-block .yiv5707510850button {"
                + "padding:20px 10px !important;}"
                + "#yiv5707510850 .yiv5707510850button-block .yiv5707510850button a {"
                + "font-size:20px !important;}"
                + "#yiv5707510850 .yiv5707510850column {"
                + "width:100% !important;display:block;padding:0 0 15px !important;}"
                + "#yiv5707510850 .yiv5707510850invoice-section .yiv5707510850invoice-logo {"
                + "display:none;}"
                + "}"
                + "</style>"
                + "<center style=\"min-width:580px;width:100%;\" id=\"yui_3_16_0_ym19_1_1466665043153_26595\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" class=\"yiv5707510850body\" style=\"background: rgba(242, 245, 247, 1);border-collapse:collapse;border-spacing:0;color:#313a45;font-family:'Open Sans', 'Arial', sans-serif;font-size:16px;line-height:1.4;margin:0;min-width:100%;padding:0;table-layout:fixed;width:100% !important;\" bgcolor=\"#f4f4f4\" id=\"yui_3_16_0_ym19_1_1466665043153_26594\">"
                + "<tbody id=\"yui_3_16_0_ym19_1_1466665043153_26593\"><tr style=\"padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26592\">"
                + "<td align=\"center\" valign=\"top\" class=\"yiv5707510850email-container\" style=\"border-collapse:collapse;margin:0;min-width:100%;padding:20px 10px;width:100% !important;\" id=\"yui_3_16_0_ym19_1_1466665043153_26591\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"580\" class=\"yiv5707510850email-body\" style=\"background:#fff;border-collapse:collapse;border-radius:8px;border-spacing:0;padding:0;table-layout:auto; box-shadow: 0 2px 3px -1px rgba(151, 171, 187, .7);\" bgcolor=\"#fff\" id=\"yui_3_16_0_ym19_1_1466665043153_26601\">"
                + "<tbody id=\"yui_3_16_0_ym19_1_1466665043153_26600\"><tr style=\"padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26599\">"
                + "<td align=\"center\" valign=\"middle\" class=\"yiv5707510850header\" style=\"background:#f0e4ba; border-collapse:collapse;border-radius:8px 8px 0 0;min-height:55px;padding:0;\" bgcolor=\"#313945\" id=\"yui_3_16_0_ym19_1_1466665043153_26598\">"
                + "<a rel=\"nofollow\" target=\"_blank\" href=\"#\" style=\"color:#00b2b2;text-decoration:none;\">"
                + "<img alt=\"ebillsPay\" title=\"ebillsPay\" style=\"border:none;min-height:55px !important;overflow-x:auto;max-width:100%;outline:none;text-decoration:none;vertical-align:middle;width:270px !important; margin: 10px 10px\" src=\""+emailLogo+"\">"
                + "</a>"
                + "</td>"
                + "</tr>"
                + "<tr style=\"padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26673\">"
                + "<td align=\"left\" valign=\"top\" class=\"yiv5707510850content\" style=\"border-collapse:collapse;padding:50px 50px 40px;\" id=\"yui_3_16_0_ym19_1_1466665043153_26672\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse;border-spacing:0;padding:0;table-layout:auto;\" id=\"yui_3_16_0_ym19_1_1466665043153_26686\">"
                + "<tbody id=\"yui_3_16_0_ym19_1_1466665043153_26685\"><tr style=\"padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26684\">"
                + "<td align=\"center\" valign=\"middle\" style=\"border-collapse:collapse;padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26683\">"
                + "<h1 style=\"font-size:28px;font-weight:bold;line-height:30px;margin:0;padding-bottom:40px;\" id=\"yui_3_16_0_ym19_1_1466665043153_26688\">Hello <br><span style=\"font-size:23px;font-weight:normal;\" id=\"yui_3_16_0_ym19_1_1466665043153_26694\">Administrator!</span>"
                + "</h1>"
                + "<p class=\"yiv5707510850lead\" style=\"font-size:14px;font-weight:normal;line-height:30px;margin:0;padding-bottom:40px;\" id=\"yui_3_16_0_ym19_1_1466665043153_26687\">Please see "+moduleName+" details below for approval.</p>"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"335\" style=\"border-collapse:separate;border-spacing:0;padding:0;table-layout:auto;\">"
                + "<tbody><tr style=\"padding:0;\">"
                + "<td align=\"center\" valign=\"middle\" style=\"border-collapse:collapse;\">"
                + "<p style=\"font-size:18px;margin:0;padding-bottom:10px; text-align: center !important;\" id=\"yui_3_16_0_ym19_1_1466665043153_28295\">"
                +details+ "</p>"
                + "</td>"
                + "</tr>"
                + "</tbody></table><br><br>"
                + "</td>"
                + "</tr>"
                + "</tbody></table>"
                + "</td>"
                + "</tr>"
                + "<tr style=\"padding:0;\">"
                + "<td align=\"center\" valign=\"middle\" class=\"yiv5707510850footer\" style=\"border-collapse:collapse;padding:0 15px 35px;\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse;border-spacing:0;padding:0;table-layout:auto;\">"
                + "<tbody><tr style=\"padding:0;\">"
                + "<td align=\"center\" valign=\"middle\" class=\"yiv5707510850close-text\" style=\"border-collapse:collapse;border-top-color:#ccc;border-top-style:solid;border-top-width:1px;font-size:17px;padding:30px 0 0;\">"
                + "<strong>Click <a rel=\"nofollow\" target=\"_blank\" href=\""+approvalLink+"\" style=\"color:#2381bb; text-decoration:none;\">here</a> to make a decision</strong>"
                + "<br>"
                + (isNew ? "Created" : "Modified")+" by <a rel=\"nofollow\" target=\"_blank\" href=\"#\" style=\"color:#2381bb; text-decoration:none;\">"+creatorEmailAddress+"</a>"
                + "</td>"
                + "</tr>"
                + "</tbody></table>"
                + "</td>"
                + "</tr>"
                + "</tbody></table>"
                + "</td>"
                + "</tr>"
                + "</tbody></table>"
                + "</center>";
    }

    /**
     * Approval or Disapproval Mail
     * Details to be displayed have to be seperated with <br/>
     * @param moduleName
     * @param details
     * @param approvalLink
     * @param disApprovalLink
     * @return
     */
    private String htmlApprovalStatusMail(String moduleName, String details, String approvalEmailAddress){
        return "<style type=\"text/css\"> "
                + "#yiv5707510850 body {"
                + "width:100% !important;min-width:100%;margin:0;padding:0;}"
                + "#yiv5707510850 .yiv5707510850ReadMsgBody {"
                + "width:100%;}"
                + "#yiv5707510850 .yiv5707510850ExternalClass {"
                + "width:100%;}"
                + "#yiv5707510850 .yiv5707510850ExternalClass {"
                + "line-height:100%;}"
                + "#yiv5707510850 #yiv5707510850backgroundTable {"
                + "margin:0;padding:0;width:100% !important;line-height:100% !important;}"
                + "#yiv5707510850 img {"
                + "width:auto;max-width:100%;}"
                + "#yiv5707510850 img {"
                + "border:none;outline:none;text-decoration:none;}"
                + "#yiv5707510850 body.yiv5707510850outlook p {"
                + "display:inline;}"
                + "#yiv5707510850 body {"
                + "color:#313a45;font-family:\"Open Sans\", \"Arial\", sans-serif;}"
                + "#yiv5707510850 body {"
                + "font-size:16px;line-height:1.4;}"
                + "#yiv5707510850 a:hover {"
                + "color:#009999;}"
                + "#yiv5707510850 a:active {"
                + "color:#009999;}"
                + "#yiv5707510850 a:visited {"
                + "color:#009999;}"
                + "#yiv5707510850 body {"
                + "background-color:#f4f4f4;}"
                + "#yiv5707510850 .yiv5707510850button-block .yiv5707510850button a:visited {"
                + "color:#fff;}"
                + "#yiv5707510850 .yiv5707510850social a:visited {"
                + "font-weight:bold;color:#313a45;}"
                + "#yiv5707510850 .yiv5707510850social a:hover {"
                + "color:#009999;}"
                + "#yiv5707510850 .yiv5707510850social a:active {"
                + "color:#009999;}"
                + "@media screen and (max-width:600px){"
                + "#yiv5707510850 center {"
                + "min-width:0 !important;}"
                + "#yiv5707510850 body {"
                + "line-height:1.3 !important;}#yiv5707510850 table[class=\"yiv5707510850body\"] {"
                + "line-height:1.3 !important;}"
                + "#yiv5707510850 h1 {"
                + "font-size:24px !important;padding-bottom:20px !important;}"
                + "#yiv5707510850 .yiv5707510850h1 {"
                + "font-size:24px !important;padding-bottom:20px !important;}"
                + "#yiv5707510850 h1 span {"
                + "font-size:21px !important;}"
                + "#yiv5707510850 .yiv5707510850h1 span {"
                + "font-size:21px !important;}"
                + "#yiv5707510850 h2 {"
                + "font-size:21px !important;}"
                + "#yiv5707510850 p.yiv5707510850lead {"
                + "font-size:21px !important;padding-bottom:20px !important;}"
                + "#yiv5707510850 .yiv5707510850content {"
                + "padding:30px 15px 20px !important;}"
                + "#yiv5707510850 .yiv5707510850footer {"
                + "padding:20px 15px 30px !important;}"
                + "#yiv5707510850 .yiv5707510850button-block {"
                + "max-width:85% !important;}"
                + "#yiv5707510850 body {"
                + "width:100% !important;min-width:100% !important;}"
                + "#yiv5707510850 table {"
                + "max-width:580px !important;width:100% !important;}"
                + "#yiv5707510850 body {"
                + "}"
                + "#yiv5707510850 table {"
                + "}"
                + "#yiv5707510850 td {"
                + "}"
                + "#yiv5707510850 p {"
                + "}"
                + "#yiv5707510850 a {"
                + "}"
                + "#yiv5707510850 img {"
                + "width:auto !important;height:auto !important;}"
                + "}"
                + "@media screen and (max-width:480px){"
                + "#yiv5707510850 .yiv5707510850button-block .yiv5707510850button {"
                + "padding:20px 10px !important;}"
                + "#yiv5707510850 .yiv5707510850button-block .yiv5707510850button a {"
                + "font-size:20px !important;}"
                + "#yiv5707510850 .yiv5707510850column {"
                + "width:100% !important;display:block;padding:0 0 15px !important;}"
                + "#yiv5707510850 .yiv5707510850invoice-section .yiv5707510850invoice-logo {"
                + "display:none;}"
                + "}"
                + "</style>"
                + "<center style=\"min-width:580px;width:100%;\" id=\"yui_3_16_0_ym19_1_1466665043153_26595\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" class=\"yiv5707510850body\" style=\"background: rgba(242, 245, 247, 1);border-collapse:collapse;border-spacing:0;color:#313a45;font-family:'Open Sans', 'Arial', sans-serif;font-size:16px;line-height:1.4;margin:0;min-width:100%;padding:0;table-layout:fixed;width:100% !important;\" bgcolor=\"#f4f4f4\" id=\"yui_3_16_0_ym19_1_1466665043153_26594\">"
                + "<tbody id=\"yui_3_16_0_ym19_1_1466665043153_26593\"><tr style=\"padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26592\">"
                + "<td align=\"center\" valign=\"top\" class=\"yiv5707510850email-container\" style=\"border-collapse:collapse;margin:0;min-width:100%;padding:20px 10px;width:100% !important;\" id=\"yui_3_16_0_ym19_1_1466665043153_26591\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"580\" class=\"yiv5707510850email-body\" style=\"background:#fff;border-collapse:collapse;border-radius:8px;border-spacing:0;padding:0;table-layout:auto; box-shadow: 0 2px 3px -1px rgba(151, 171, 187, .7);\" bgcolor=\"#fff\" id=\"yui_3_16_0_ym19_1_1466665043153_26601\">"
                + "<tbody id=\"yui_3_16_0_ym19_1_1466665043153_26600\"><tr style=\"padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26599\">"
                + "<td align=\"center\" valign=\"middle\" class=\"yiv5707510850header\" style=\"background:#f0e4ba; border-collapse:collapse;border-radius:8px 8px 0 0;min-height:55px;padding:0;\" bgcolor=\"#313945\" id=\"yui_3_16_0_ym19_1_1466665043153_26598\">"
                + "<a rel=\"nofollow\" target=\"_blank\" href=\"#\" style=\"color:#00b2b2;text-decoration:none;\">"
                + "<img alt=\"ebillsPay\" title=\"ebillsPay\" style=\"border:none;min-height:55px !important;overflow-x:auto;max-width:100%;outline:none;text-decoration:none;vertical-align:middle;width:270px !important; margin: 10px 10px\" src=\""+emailLogo+"\">"
                + "</a>"
                + "</td>"
                + "</tr>"
                + "<tr style=\"padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26673\">"
                + "<td align=\"left\" valign=\"top\" class=\"yiv5707510850content\" style=\"border-collapse:collapse;padding:50px 50px 40px;\" id=\"yui_3_16_0_ym19_1_1466665043153_26672\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse;border-spacing:0;padding:0;table-layout:auto;\" id=\"yui_3_16_0_ym19_1_1466665043153_26686\">"
                + "<tbody id=\"yui_3_16_0_ym19_1_1466665043153_26685\"><tr style=\"padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26684\">"
                + "<td align=\"center\" valign=\"middle\" style=\"border-collapse:collapse;padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26683\">"

                + "<p class=\"yiv5707510850lead\" style=\"font-size:14px;font-weight:normal;line-height:30px;margin:0;padding-bottom:40px;\" id=\"yui_3_16_0_ym19_1_1466665043153_26687\">Please see below "+moduleName+" approval status.</p>"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"335\" style=\"border-collapse:separate;border-spacing:0;padding:0;table-layout:auto;\">"
                + "<tbody><tr style=\"padding:0;\">"
                + "<td align=\"center\" valign=\"middle\" style=\"border-collapse:collapse;\">"
                + "<p style=\"font-size:18px;margin:0;padding-bottom:10px; text-align: center !important;\" id=\"yui_3_16_0_ym19_1_1466665043153_28295\">"
                +details+ "</p>"
                + "</td>"
                + "</tr>"
                + "</tbody></table><br><br>"
                + "</td>"
                + "</tr>"
                + "</tbody></table>"
                + "</td>"
                + "</tr>"
                + "<tr style=\"padding:0;\">"
                + "<td align=\"center\" valign=\"middle\" class=\"yiv5707510850footer\" style=\"border-collapse:collapse;padding:0 15px 35px;\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse;border-spacing:0;padding:0;table-layout:auto;\">"
                + "<tbody><tr style=\"padding:0;\">"
                + "<td align=\"center\" valign=\"middle\" class=\"yiv5707510850close-text\" style=\"border-collapse:collapse;border-top-color:#ccc;border-top-style:solid;border-top-width:1px;font-size:17px;padding:30px 0 0;\">"
                + "<br>"
                + " Approver : <a rel=\"nofollow\" target=\"_blank\" href=\"#\" style=\"color:#2381bb; text-decoration:none;\">"+approvalEmailAddress+"</a>"
                + "</td>"
                + "</tr>"
                + "</tbody></table>"
                + "</td>"
                + "</tr>"
                + "</tbody></table>"
                + "</td>"
                + "</tr>"
                + "</tbody></table>"
                + "</center>";
    }

    /**
     * Notification Mail
     * Details to be displayed have to be seperated with <br/>
     * @param moduleName
     * @param details
     * @param approvalLink
     * @param disApprovalLink
     * @return
     */
    private String htmlNotificationMail(String details, String title, String subtitle){
        String mailToSend = "<style type=\"text/css\"> "
                + "#yiv5707510850 body {"
                + "width:100% !important;min-width:100%;margin:0;padding:0;}"
                + "#yiv5707510850 .yiv5707510850ReadMsgBody {"
                + "width:100%;}"
                + "#yiv5707510850 .yiv5707510850ExternalClass {"
                + "width:100%;}"
                + "#yiv5707510850 .yiv5707510850ExternalClass {"
                + "line-height:100%;}"
                + "#yiv5707510850 #yiv5707510850backgroundTable {"
                + "margin:0;padding:0;width:100% !important;line-height:100% !important;}"
                + "#yiv5707510850 img {"
                + "width:auto;max-width:100%;}"
                + "#yiv5707510850 img {"
                + "border:none;outline:none;text-decoration:none;}"
                + "#yiv5707510850 body.yiv5707510850outlook p {"
                + "display:inline;}"
                + "#yiv5707510850 body {"
                + "color:#313a45;font-family:\"Open Sans\", \"Arial\", sans-serif;}"
                + "#yiv5707510850 body {"
                + "font-size:16px;line-height:1.4;}"
                + "#yiv5707510850 a:hover {"
                + "color:#009999;}"
                + "#yiv5707510850 a:active {"
                + "color:#009999;}"
                + "#yiv5707510850 a:visited {"
                + "color:#009999;}"
                + "#yiv5707510850 body {"
                + "background-color:#f4f4f4;}"
                + "#yiv5707510850 .yiv5707510850button-block .yiv5707510850button a:visited {"
                + "color:#fff;}"
                + "#yiv5707510850 .yiv5707510850social a:visited {"
                + "font-weight:bold;color:#313a45;}"
                + "#yiv5707510850 .yiv5707510850social a:hover {"
                + "color:#009999;}"
                + "#yiv5707510850 .yiv5707510850social a:active {"
                + "color:#009999;}"
                + "@media screen and (max-width:600px){"
                + "#yiv5707510850 center {"
                + "min-width:0 !important;}"
                + "#yiv5707510850 body {"
                + "line-height:1.3 !important;}#yiv5707510850 table[class=\"yiv5707510850body\"] {"
                + "line-height:1.3 !important;}"
                + "#yiv5707510850 h1 {"
                + "font-size:24px !important;padding-bottom:20px !important;}"
                + "#yiv5707510850 .yiv5707510850h1 {"
                + "font-size:24px !important;padding-bottom:20px !important;}"
                + "#yiv5707510850 h1 span {"
                + "font-size:21px !important;}"
                + "#yiv5707510850 .yiv5707510850h1 span {"
                + "font-size:21px !important;}"
                + "#yiv5707510850 h2 {"
                + "font-size:21px !important;}"
                + "#yiv5707510850 p.yiv5707510850lead {"
                + "font-size:21px !important;padding-bottom:20px !important;}"
                + "#yiv5707510850 .yiv5707510850content {"
                + "padding:30px 15px 20px !important;}"
                + "#yiv5707510850 .yiv5707510850footer {"
                + "padding:20px 15px 30px !important;}"
                + "#yiv5707510850 .yiv5707510850button-block {"
                + "max-width:85% !important;}"
                + "#yiv5707510850 body {"
                + "width:100% !important;min-width:100% !important;}"
                + "#yiv5707510850 table {"
                + "max-width:580px !important;width:100% !important;}"
                + "#yiv5707510850 body {"
                + "}"
                + "#yiv5707510850 table {"
                + "}"
                + "#yiv5707510850 td {"
                + "}"
                + "#yiv5707510850 p {"
                + "}"
                + "#yiv5707510850 a {"
                + "}"
                + "#yiv5707510850 img {"
                + "width:auto !important;height:auto !important;}"
                + "}"
                + "@media screen and (max-width:480px){"
                + "#yiv5707510850 .yiv5707510850button-block .yiv5707510850button {"
                + "padding:20px 10px !important;}"
                + "#yiv5707510850 .yiv5707510850button-block .yiv5707510850button a {"
                + "font-size:20px !important;}"
                + "#yiv5707510850 .yiv5707510850column {"
                + "width:100% !important;display:block;padding:0 0 15px !important;}"
                + "#yiv5707510850 .yiv5707510850invoice-section .yiv5707510850invoice-logo {"
                + "display:none;}"
                + "}"
                + "</style>"
                + "<center style=\"min-width:580px;width:100%;\" id=\"yui_3_16_0_ym19_1_1466665043153_26595\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" class=\"yiv5707510850body\" style=\"background: rgba(242, 245, 247, 1);border-collapse:collapse;border-spacing:0;color:#313a45;font-family:'Open Sans', 'Arial', sans-serif;font-size:16px;line-height:1.4;margin:0;min-width:100%;padding:0;table-layout:fixed;width:100% !important;\" bgcolor=\"#f4f4f4\" id=\"yui_3_16_0_ym19_1_1466665043153_26594\">"
                + "<tbody id=\"yui_3_16_0_ym19_1_1466665043153_26593\"><tr style=\"padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26592\">"
                + "<td align=\"center\" valign=\"top\" class=\"yiv5707510850email-container\" style=\"border-collapse:collapse;margin:0;min-width:100%;padding:20px 10px;width:100% !important;\" id=\"yui_3_16_0_ym19_1_1466665043153_26591\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"580\" class=\"yiv5707510850email-body\" style=\"background:#fff;border-collapse:collapse;border-radius:8px;border-spacing:0;padding:0;table-layout:auto; box-shadow: 0 2px 3px -1px rgba(151, 171, 187, .7);\" bgcolor=\"#fff\" id=\"yui_3_16_0_ym19_1_1466665043153_26601\">"
                + "<tbody id=\"yui_3_16_0_ym19_1_1466665043153_26600\"><tr style=\"padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26599\">"
                + "<td align=\"center\" valign=\"middle\" class=\"yiv5707510850header\" style=\"background:#f0e4ba; border-collapse:collapse;border-radius:8px 8px 0 0;min-height:55px;padding:0;\" bgcolor=\"#313945\" id=\"yui_3_16_0_ym19_1_1466665043153_26598\">"
                + "<a rel=\"nofollow\" target=\"_blank\" href=\"#\" style=\"color:#00b2b2;text-decoration:none;\">"
                + "<img alt=\"ebillsPay\" title=\"ebillsPay\" style=\"border:none;min-height:55px !important;overflow-x:auto;max-width:100%;outline:none;text-decoration:none;vertical-align:middle;width:270px !important; margin: 10px 10px\" src=\""+emailLogo+"\">"
                + "</a>"
                + "</td>"
                + "</tr>"
                + "<tr style=\"padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26673\">"
                + "<td align=\"left\" valign=\"top\" class=\"yiv5707510850content\" style=\"border-collapse:collapse;padding:50px 50px 40px;\" id=\"yui_3_16_0_ym19_1_1466665043153_26672\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse;border-spacing:0;padding:0;table-layout:auto;\" id=\"yui_3_16_0_ym19_1_1466665043153_26686\">"
                + "<tbody id=\"yui_3_16_0_ym19_1_1466665043153_26685\"><tr style=\"padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26684\">"
                + "<td align=\"center\" valign=\"middle\" style=\"border-collapse:collapse;padding:0;\" id=\"yui_3_16_0_ym19_1_1466665043153_26683\">"
                + "<h1 style=\"font-size:28px;font-weight:bold;line-height:30px;margin:0;padding-bottom:40px;\" id=\"yui_3_16_0_ym19_1_1466665043153_26688\">{title}"
                + "</h1>"
                + "<p class=\"yiv5707510850lead\" style=\"font-size:14px;font-weight:normal;line-height:30px;margin:0;padding-bottom:40px;\" id=\"yui_3_16_0_ym19_1_1466665043153_26687\">{subtitle} </p>"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"335\" style=\"border-collapse:separate;border-spacing:0;padding:0;table-layout:auto;\">"
                + "<tbody><tr style=\"padding:0;\">"
                + "<td align=\"center\" valign=\"middle\" style=\"border-collapse:collapse;\">"
                + "<p style=\"font-size:18px;margin:0;padding-bottom:10px; text-align: center !important;\" id=\"yui_3_16_0_ym19_1_1466665043153_28295\">"
                +details+ "</p>"
                + "</td>"
                + "</tr>"
                + "</tbody></table><br><br>"
                + "</td>"
                + "</tr>"
                + "</tbody></table>"
                + "</td>"
                + "</tr>"
                + "<tr style=\"padding:0;\">"
                + "<td align=\"center\" valign=\"middle\" class=\"yiv5707510850footer\" style=\"border-collapse:collapse;padding:0 15px 35px;\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse;border-spacing:0;padding:0;table-layout:auto;\">"
                + "<tbody><tr style=\"padding:0;\">"
                + "<td align=\"center\" valign=\"middle\" class=\"yiv5707510850close-text\" style=\"border-collapse:collapse;border-top-color:#ccc;border-top-style:solid;border-top-width:1px;font-size:17px;padding:30px 0 0;\">"
                + "powered by <a rel=\"nofollow\" target=\"_blank\" href=\"#\" style=\"color:#2381bb; text-decoration:none;\">"+poweredBy+"</a>"
                + "</td>"
                + "</tr>"
                + "</tbody></table>"
                + "</td>"
                + "</tr>"
                + "</tbody></table>"
                + "</td>"
                + "</tr>"
                + "</tbody></table>"
                + "</center>";
        mailToSend = mailToSend.replace("{title}", title).replace("{subtitle}", subtitle);
        return mailToSend;
    }

}
