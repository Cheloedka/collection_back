package com.example.collections_backend.email;

public class EmailBody {

    public static String mailBody(String text, String name, String link, String button){
        return "<html>\n" +
                "<head>\n" +
                "<style type=\"text/css\">\n" +
                ".abutton{\n" +
                "font-size: 16px;\n" +
                "font-style: italic;\n" +
                "display: inline-block;\n" +
                "color: #ffffff;\n" +
                "background-color: #211C35;\n" +
                "text-transform: uppercase;\n" +
                "text-decoration: none;\n" +
                "border-radius: 10px;\n" +
                "font-weight: 800;\n" +
                "padding: 12px 36px;\n" +
                "cursor:pointer;\n" +
                "}\n" +
                ".abutton:hover{\n" +
                "background-color: #383156;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"margin: 0; padding: 0;\">\n" +
                "<div style=\"max-width: 700px; margin: 0 auto; font-family: Arial;\">\n" +
                "<div style=\"padding-bottom: 20px; padding-top: 20px;\">\n" +
                "<div style=\"padding: 20px 0;text-align: center;margin: 0;background-color: #211C35;background-position: top center; color: #ffffff;\">\n" +
                "Collection\n" +
                "</div>\n" +
                "<div style=\"padding: 0 20px\">\n" +
                "<h1 style=\"text-align: center;font-size: 22px;line-height: 24px;padding: 0 30px\">\n" +
                "Hi "+ name +" \n" +
                "</h1>\n" +
                "<p style=\"text-align: center;font-size: 18px;line-height: 28px;margin-bottom: 15px;margin-top: 0\">\n" +
                text +"\n" +
                "</p>\n" +
                "</div>\n" +
                "<div style=\"text-align: center;margin-top: 40px;margin-bottom: 40px\">\n" +
                "<a style=\"color: #ffffff\" class=\"abutton\" href=\""+ link +"\" target=\"_blank\">\n" +
                button + "\n" +
                "</a>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }

    public static String verificationTemplate(String name, String link) {
        return mailBody(
                "If you have just registered on Collections, please confirm your email.",
                name,
                link,
                "Verify your account"
        );
    }

    public static String changeEmailTemplate(String name, String link, String email) {
        return mailBody(
                "If you requested to change your email to " + email + ", click a button below. Otherwise, don't do anything",
                name,
                link,
                "Confirm action"
        );
    }

    public static String changeEmailConfirmationNewTemplate(String name, String link) {
        return mailBody(
                "If you wanted to change e-mail on Collections, click a button below. Otherwise, don't do anything",
                name,
                link,
                "Confirm action"
        );
    }


}
