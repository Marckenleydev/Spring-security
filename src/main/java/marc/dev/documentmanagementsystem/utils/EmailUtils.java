package marc.dev.documentmanagementsystem.utils;

public class EmailUtils {

    public static String getEmailMessage(String name, String host, String token){
        return "Hello " + name + ",\n\nYour new account has been created. please click to verify the link below to verify  \n\n" +
                getVerificationUrl(host, token) + "\n\nThe Support  Team";

    }
    public static String getResetPasswordMessage(String name, String host, String token){
        return "Hello " + name + ",\n\nYour new account has been created. please click to verify the link below to verify  \n\n" +
                getResetPasswordUrl(host, token) + "\n\nThe Support  Team";
    }

    private static String getResetPasswordUrl(String host, String token) {
        return  host +"/api/v1/user/verify/password?token=" + token;
    }

    public static String getVerificationUrl(String host, String token) {
        return  host +"/api/v1/user/verify/account?token=" + token;
    }

}
