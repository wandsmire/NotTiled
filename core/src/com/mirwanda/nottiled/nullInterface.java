package com.mirwanda.nottiled;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class nullInterface implements Interface {
    @Override
    public void showbanner(boolean show) {

    }

    @Override
    public void showinterstitial() {

    }

    @Override
    public boolean buyadfree() {
        return false;
    }

    @Override
    public boolean ispro() {
        return false;
    }

    @Override
    public String getVersione() {
        return "1.6.7";
    }

    @Override
    public String openDialog() {
        new Thread( new Runnable() {
            @Override
            public void run() {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //chooser.showSaveDialog(null);

                JFrame f = new JFrame();
                f.setVisible( true );
                f.toFront();
                f.setAlwaysOnTop( true );
                f.setVisible( false );

                int res = chooser.showSaveDialog( f );
                f.dispose();
                if (res == JFileChooser.APPROVE_OPTION) {
                    //Do some stuff
                }

            }
        } ).start();
        return null;
    }

    String pet="";
    @Override
    public String opet() {
        return pet;
    }

    @Override
    public String saveDialog() {
        return null;
    }

    @Override
    public void speak(String s) {

    }

    @Override
    public void changelanguage(String lang) {

    }
}
