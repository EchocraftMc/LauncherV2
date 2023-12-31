package fr.cakihorse.echolaunch.utils;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;

import javax.swing.*;

import static fr.cakihorse.echolaunch.Main.getSaver;


public class Auth {

    public static AuthInfos authInfos;

    public static void launch() throws MicrosoftAuthenticationException {
        if (getSaver().get("refresh_token") == null) {
            MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
            final String refresh_token = getSaver().get("refresh_token");
            MicrosoftAuthResult result = null;
            if (refresh_token != null && !refresh_token.isEmpty()) {
                result = microsoftAuthenticator.loginWithRefreshToken(refresh_token);
                authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId());
                System.out.printf("Logged in with '%s'%n", result.getProfile().getName());
            } else {
                result = microsoftAuthenticator.loginWithWebview();
                getSaver().set("refresh_token", result.getRefreshToken());
                getSaver().set("accessToken", result.getAccessToken());
                getSaver().set("clientToken", result.getClientId());

                getSaver().save();
                authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId());
                System.out.printf("Logged in with '%s'%n", result.getProfile().getName());
            }
        }else {
            JOptionPane.showMessageDialog(null, "Vous êtes déjà connecté !");
        }
    }

}
