package fr.cakihorse.echolaunch.threads;

import fr.cakihorse.echolaunch.app.Launcher;
import fr.cakihorse.echolaunch.utils.Auth;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;

public class MsThread implements Runnable{
    @Override
    public void run() {
        try {

            Auth.launch();
        } catch (MicrosoftAuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}