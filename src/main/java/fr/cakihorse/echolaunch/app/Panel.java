package fr.cakihorse.echolaunch.app;

import fr.cakihorse.echolaunch.utils.Images;
import fr.cakihorse.echolaunch.threads.MsThread;
import fr.cakihorse.echolaunch.utils.PatchesWebView;
import fr.cakihorse.echolaunch.utils.Resources;
import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowlogger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

import static fr.cakihorse.echolaunch.Main.getSaver;


public class Panel extends JPanel{
    private ImageIcon normalIcon; // Icône par défaut
    private ImageIcon hoverIcon;  // Icône pour le survol

    private ImageIcon MsnormalIcon; // Icône par défaut
    private ImageIcon MshoverIcon;  // Icône pour le survol

    JProgressBar progressBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, 10);
    JButton msBtn = Images.newButton(Resources.getResource("ms.png"), 315, 125, false);
    JButton playBtn = Images.newButton(Resources.getResource("play.png"), 315, 125, false);
    JLabel title = new JLabel("Se connecter avec :");
    JLabel warning = new JLabel("Vérifiez d'être connecté avant de lancer le jeu !");

    JLabel credits = new JLabel("Made by cakihorse.");
    JLabel showPseudo = new JLabel("Bienvenue, " + pseudo + " !");

    JButton patches = new JButton("Patches");


    public static String pseudo;

    Font customFont = new Font("Arial", Font.BOLD, 16); // Exemple avec la police Arial, style gras, taille 16

    public void updatePseudo(String newPseudo) {
        if (newPseudo != null && !newPseudo.isEmpty()) {
            showPseudo.setText("Bienvenue, " + newPseudo + " !");
        } else {
            showPseudo.setText("Bienvenue, inconnu !");
        }
    }
    public Panel() throws MalformedURLException {


        if (Launcher.getAuthInfos() != null) {

            pseudo = getSaver().get("pseudo");
            updatePseudo(pseudo);
        } else if (pseudo == null) {
            showPseudo.setText("Bienvenue, l'inconnu !");
        }


        this.setLayout(null);

        //avatar:

        String avatarUrl = "https://minotar.net/bust/" + (
                getSaver().get("refresh_token") == null ?
                        "MHF_Steve.png" :
                        Launcher.getAuthInfos().getUuid() + ".png"
        );

        JLabel label = new JLabel( new ImageIcon( new URL(avatarUrl ) ) );




        showPseudo.setBounds(790, 430, 300, 50);
        showPseudo.setForeground(Color.WHITE);
        showPseudo.setFont(customFont);
        this.add(showPseudo);
        this.add(patches);





        this.add(label);

        //patches
        patches.setBounds(920, 450, 315, 100);
        patches.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {



                patches.setIcon(hoverIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                patches.setIcon(normalIcon);
            }
        });
        patches.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PatchesWebView.newWindow("https://cakihorse.fr/patches");
            }
        });


        //playbtn

        playBtn.setBounds(920, 550, 315, 100);
        playBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                

                playBtn.setIcon(hoverIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                playBtn.setIcon(normalIcon);
            }
        });
        playBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Launcher.update();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    Launcher.launch();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() + " | Please login!");
                    throw new RuntimeException(ex);

                }
                System.out.println("Lancement en cours...");
            }
        });



        //label
        title.setBounds(50,200,200, 30);
        title.setForeground(Color.WHITE);

        title.setFont(customFont);

        credits.setBounds(20,640,200, 30);
        credits.setForeground(Color.WHITE);

        credits.setFont(customFont);

        warning.setBounds(710,150,400, 70);
        warning.setForeground(Color.RED);
        warning.setFont(customFont);



        //msButton

        normalIcon = new ImageIcon(Resources.getResource("play.png"));
        hoverIcon = new ImageIcon(Resources.getResource("play_hover.png"));

        MsnormalIcon = new ImageIcon(Resources.getResource("ms.png"));
        MshoverIcon = new ImageIcon(Resources.getResource("ms_hover.png"));

        msBtn.setBounds(30, 220, 313, 160);

        msBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Lorsque la souris entre dans la zone du bouton, changez l'icône pour l'effet "hover"

                msBtn.setIcon(MshoverIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Lorsque la souris quitte la zone du bouton, réinitialisez l'icône au bouton normal
                msBtn.setIcon(MsnormalIcon);
            }
        });

        msBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Connexion réussie");

                Thread t = new Thread(new MsThread());
                t.start();
            }
        });

        //progress bar
        progressBar.setBounds(50, 800, 100, 20);
        progressBar.setValue(0);
        // Afficher la chaîne de progression
        progressBar.setStringPainted(true);

        //Icallback


        this.add(playBtn);
        this.add(title);
        this.add(msBtn);
        this.add(credits);
        this.add(warning);
        this.add(progressBar);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //background
        g.drawImage(Resources.getResource("bg.png"), 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
