package com.mirwanda.nottiled;

import java.io.InputStream;
import java.util.List;

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
        return "1.7.0";
    }

    @Override
    public String getFilename() {
        return null;
    }

    @Override
    public String getUri() {
        return null;
    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public byte[] getData() {
        return null;
    }

    @Override
    public List<byte[]> getDatas() {
        return null;
    }

    @Override
    public List<String> getFilenames() {
        return null;
    }

    /*
    @Override
    public String openDialog() {
        pet="";
        new Thread( new Runnable() {
            @Override
            public void run() {
                JFileChooser chooser = new JFileChooser();
                //chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //chooser.showSaveDialog(null);

                JFrame f = new JFrame();
                f.setVisible( true );
                f.toFront();
                f.setAlwaysOnTop( true );
                f.setVisible( false );

                int res = chooser.showOpenDialog( f );
                f.dispose();
                if (res == JFileChooser.APPROVE_OPTION) {
                    pet = chooser.getSelectedFile().getAbsolutePath();
                }else{
                    pet = "cancel";
                }

            }
        } ).start();
        return null;
    }

    @Override
    public String openDirectory() {
        pet="";
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
                    pet = chooser.getSelectedFile().getAbsolutePath();
                }else{
                    pet = "cancel";
                }

            }
        } ).start();
        return null;
    }
*/


    @Override
    public void speak(String s) {

    }

    @Override
    public void changelanguage(String lang) {

    }

    @Override
    public void saveFile(String data) {

    }

    @Override
    public void saveasFile(String data, String aaa) {

    }

    @Override
    public void saveasFile(byte[] data, String aaa) {

    }

    @Override
    public void openFile() {

    }

    @Override
    public void newFile() {

    }

    @Override
    public void selectFolder() {

    }

    @Override
    public String getdatafromURI(String URI) {
        return null;
    }


}
