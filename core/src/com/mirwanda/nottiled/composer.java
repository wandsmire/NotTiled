package com.mirwanda.nottiled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;
import com.leff.midi.examples.EventPrinter;
import com.leff.midi.util.MidiProcessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class composer {
    String sequence;
    public composer(){

    }
    public composer(String sequence){
        this.sequence=sequence;
    }

    public int clayer=0;
    public int clayertick=0;
    public int cnote=0;
    public int cduration=0;
    public int cvoice=0; //channel
    public int cinstrument=0; //program
    public int ctick=0; //tick
    MidiTrack tempoTrack = new MidiTrack();
    MidiTrack noteTrack = new MidiTrack();


    public void setVoice(int voice){
        if (voice!=cvoice) {
            cvoice = voice;
            ctick = 0;
        }
    }

    public void setLayer(int layer){
        if (layer>clayer) {
            ctick = clayertick;
        }
        clayer = layer;
        clayertick=ctick;
    }

    public void changeProgram(int Program){
        if (cinstrument!=Program) {
            ProgramChange pc = new ProgramChange( ctick, cvoice, Program );
            noteTrack.insertEvent( pc );
        }
    }
    public void setInstrument(String instrument){
        switch (instrument.toUpperCase()){
            case "[PIANO]": changeProgram(0); break;
            case "[ACOUSTIC_GRAND]": changeProgram(0); break;
            case "[BRIGHT_ACOUSTIC]": changeProgram(1); break;
            case "[ELECTRIC_GRAND]": changeProgram(2); break;
            case "[HONKEY_TONK]": changeProgram(3); break;
            case "[ELECTRIC_PIANO]": changeProgram(4); break;
            case "[ELECTRIC_PIANO1]": changeProgram(4); break;
            case "[ELECTRIC_PIANO2]": changeProgram(5); break;
            case "[HARPISCHORD]": changeProgram(6); break;
            case "[CLAVINET]": changeProgram(7); break;
            case "[CELESTA]": changeProgram(8); break;
            case "[GLOCKENSPIEL]": changeProgram(9); break;
            case "[MUSIC_BOX]": changeProgram(10); break;
            case "[VIBRAPHONE]": changeProgram(11); break;
            case "[MARIMBA]": changeProgram(12); break;
            case "[XYLOPHONE]": changeProgram(13); break;
            case "[TUBULAR_BELLS]": changeProgram(14); break;
            case "[DULCIMER]": changeProgram(15); break;
            case "[DRAWBAR_ORGAN]": changeProgram(16); break;
            case "[PERCUSSIVE_ORGAN]": changeProgram(17); break;
            case "[ROCK_ORGAN]": changeProgram(18); break;
            case "[CHURCH_ORGAN]": changeProgram(19); break;
            case "[REED_ORGAN]": changeProgram(20); break;
            case "[ACCORIDAN]": changeProgram(21); break;
            case "[HARMONICA]": changeProgram(22); break;
            case "[TANGO_ACCORDIAN]": changeProgram(23); break;
            case "[GUITAR]": changeProgram(24); break;
            case "[NYLON_STRING_GUITAR]": changeProgram(24); break;
            case "[STEEL_STRING_GUITAR]": changeProgram(25); break;
            case "[ELECTRIC_JAZZ_GUITAR]": changeProgram(26); break;
            case "[ELECTRIC_CLEAN_GUITAR]": changeProgram(27); break;
            case "[ELECTRIC_MUTED_GUITAR]": changeProgram(28); break;
            case "[OVERDRIVEN_GUITAR]": changeProgram(29); break;
            case "[DISTORTION_GUITAR]": changeProgram(30); break;
            case "[GUITAR_HARMONICS]": changeProgram(31); break;
            case "[ACOUSTIC_BASS]": changeProgram(32); break;
            case "[ELECTRIC_BASS_FINGER]": changeProgram(33); break;
            case "[ELECTRIC_BASS_PICK]": changeProgram(34); break;
            case "[FRETLESS_BASS]": changeProgram(35); break;
            case "[SLAP_BASS_1]": changeProgram(36); break;
            case "[SLAP_BASS_2]": changeProgram(37); break;
            case "[SYNTH_BASS_1]": changeProgram(38); break;
            case "[SYNTH_BASS_2]": changeProgram(39); break;
            case "[VIOLIN]": changeProgram(40); break;
            case "[VIOLA]": changeProgram(41); break;
            case "[CELLO]": changeProgram(42); break;
            case "[CONTRABASS]": changeProgram(43); break;
            case "[TREMOLO_STRINGS]": changeProgram(44); break;
            case "[PIZZICATO_STRINGS]": changeProgram(45); break;
            case "[ORCHESTRAL_STRINGS]": changeProgram(46); break;
            case "[TIMPANI]": changeProgram(47); break;
            case "[STRING_ENSEMBLE_1]": changeProgram(48); break;
            case "[STRING_ENSEMBLE_2]": changeProgram(49); break;
            case "[SYNTH_STRINGS_1]": changeProgram(50); break;
            case "[SYNTH_STRINGS_2]": changeProgram(51); break;
            case "[CHOIR_AAHS]": changeProgram(52); break;
            case "[VOICE_OOHS]": changeProgram(53); break;
            case "[SYNTH_VOICE]": changeProgram(54); break;
            case "[ORCHESTRA_HIT]": changeProgram(55); break;
            case "[TRUMPET]": changeProgram(56); break;
            case "[TROMBONE]": changeProgram(57); break;
            case "[TUBA]": changeProgram(58); break;
            case "[MUTED_TRUMPET]": changeProgram(59); break;
            case "[FRENCH_HORN]": changeProgram(60); break;
            case "[BRASS_SECTION]": changeProgram(61); break;
            case "[SYNTH_BRASS_1]": changeProgram(62); break;
            case "[SYNTH_BRASS_2]": changeProgram(63); break;
            case "[SOPRANO_SAX]": changeProgram(64); break;
            case "[ALTO_SAX]": changeProgram(65); break;
            case "[TENOR_SAX]": changeProgram(66); break;
            case "[BARITONE_SAX]": changeProgram(67); break;
            case "[OBOE]": changeProgram(68); break;
            case "[ENGLISH_HORN]": changeProgram(69); break;
            case "[BASSOON]": changeProgram(70); break;
            case "[CLARINET]": changeProgram(71); break;
            case "[PICCOLO]": changeProgram(72); break;
            case "[FLUTE]": changeProgram(73); break;
            case "[RECORDER]": changeProgram(74); break;
            case "[PAN_FLUTE]": changeProgram(75); break;
            case "[BLOWN_BOTTLE]": changeProgram(76); break;
            case "[SKAKUHACHI]": changeProgram(77); break;
            case "[WHISTLE]": changeProgram(78); break;
            case "[OCARINA]": changeProgram(79); break;
            case "[LEAD_SQUARE]": changeProgram(80); break;
            case "[SQUARE]": changeProgram(80); break;
            case "[LEAD_SAWTOOTH]": changeProgram(81); break;
            case "[SAWTOOTH]": changeProgram(81); break;
            case "[LEAD_CALLIOPE]": changeProgram(82); break;
            case "[CALLIOPE]": changeProgram(82); break;
            case "[LEAD_CHIFF]": changeProgram(83); break;
            case "[CHIFF]": changeProgram(83); break;
            case "[LEAD_CHARANG]": changeProgram(84); break;
            case "[CHARANG]": changeProgram(84); break;
            case "[LEAD_VOICE]": changeProgram(85); break;
            case "[VOICE]": changeProgram(85); break;
            case "[LEAD_FIFTHS]": changeProgram(86); break;
            case "[FIFTHS]": changeProgram(86); break;
            case "[LEAD_BASSLEAD]": changeProgram(87); break;
            case "[BASS_LEAD]": changeProgram(87); break;
            case "[PAD_NEW_AGE]": changeProgram(88); break;
            case "[NEW_AGE]": changeProgram(88); break;
            case "[PAD_WARM]": changeProgram(89); break;
            case "[WARM]": changeProgram(89); break;
            case "[PAD_POLYSYNTH]": changeProgram(90); break;
            case "[POLY_SYNTH]": changeProgram(90); break;
            case "[PAD_CHOIR]": changeProgram(91); break;
            case "[CHOIR]": changeProgram(91); break;
            case "[PAD_BOWED]": changeProgram(92); break;
            case "[BOWED]": changeProgram(92); break;
            case "[PAD_METALLIC]": changeProgram(93); break;
            case "[METALLIC]": changeProgram(93); break;
            case "[PAD_HALO]": changeProgram(94); break;
            case "[HALO]": changeProgram(94); break;
            case "[PAD_SWEEP]": changeProgram(95); break;
            case "[SWEEP]": changeProgram(95); break;
            case "[FX_RAIN]": changeProgram(96); break;
            case "[RAIN]": changeProgram(96); break;
            case "[FX_SOUNDTRACK]": changeProgram(97); break;
            case "[SOUNDTRACK]": changeProgram(97); break;
            case "[FX_CRYSTAL]": changeProgram(98); break;
            case "[CRYSTAL]": changeProgram(98); break;
            case "[FX_ATMOSPHERE]": changeProgram(99); break;
            case "[ATMOSPHERE]": changeProgram(99); break;
            case "[FX_BRIGHTNESS]": changeProgram(100); break;
            case "[BRIGHTNESS]": changeProgram(100); break;
            case "[FX_GOBLINS]": changeProgram(101); break;
            case "[GOBLINS]": changeProgram(101); break;
            case "[FX_ECHOES]": changeProgram(102); break;
            case "[ECHOES]": changeProgram(102); break;
            case "[FX_SCI-FI]": changeProgram(103); break;
            case "[SCI-FI]": changeProgram(103); break;
            case "[SITAR]": changeProgram(104); break;
            case "[BANJO]": changeProgram(105); break;
            case "[SHAMISEN]": changeProgram(106); break;
            case "[KOTO]": changeProgram(107); break;
            case "[KALIMBA]": changeProgram(108); break;
            case "[BAGPIPE]": changeProgram(109); break;
            case "[FIDDLE]": changeProgram(110); break;
            case "[SHANAI]": changeProgram(111); break;
            case "[TINKLE_BELL]": changeProgram(112); break;
            case "[AGOGO]": changeProgram(113); break;
            case "[STEEL_DRUMS]": changeProgram(114); break;
            case "[WOODBLOCK]": changeProgram(115); break;
            case "[TAIKO_DRUM]": changeProgram(116); break;
            case "[MELODIC_DRUM]": changeProgram(117); break;
            case "[SYNTH_DRUM]": changeProgram(118); break;
            case "[REVERSE_CYMBAL]": changeProgram(119); break;
            case "[GUITAR_FRET_NOISE]": changeProgram(120); break;
            case "[BREATH_NOISE]": changeProgram(121); break;
            case "[SEASHORE]": changeProgram(122); break;
            case "[BIRD_TWEET]": changeProgram(123); break;
            case "[TELEPHONE_RING]": changeProgram(124); break;
            case "[HELICOPTER]": changeProgram(125); break;
            case "[APPLAUSE]": changeProgram(126); break;
            case "[GUNSHOT]": changeProgram(127); break;
            default:

                try{
                    changeProgram(Integer.parseInt( instrument ) );
                } catch(Exception e){

                    changeProgram(0 );

                }break;
        }

    }

    int cdur=480;

    public void addChord(String chord){
        String command = new String( "" );
        String tnote = ""; int toctave = 5; String tkey="maj";
        String sequence = chord.toLowerCase();

        for (int i = 0; i < 2; i++) {
            String c = sequence.substring( i, i + 1 );
            if ("abcdefgr#".contains( c )) {
                command += c;
            }
        }
        tnote = command;

        //C#10
        command = new String( "" );
        for (int i = 0; i < 4; i++) {
            String c = sequence.substring( i, i + 1 );
            if ("0123456789".contains( c )) {
                command += c;
            }
        }
        String ttoctave = (command.equalsIgnoreCase( "" )) ? "" : command;
        toctave = (command.equalsIgnoreCase( "" )) ? 4 : Integer.parseInt( command );

        int inote = 0;
        switch (tnote) {
            case "c":
                inote = 0;
                break;
            case "c#":
                inote = 1;
                break;
            case "d":
                inote = 2;
                break;
            case "d#":
                inote = 3;
                break;
            case "e":
                inote = 4;
                break;
            case "f":
                inote = 5;
                break;
            case "f#":
                inote = 6;
                break;
            case "g":
                inote = 7;
                break;
            case "g#":
                inote = 8;
                break;
            case "a":
                inote = 9;
                break;
            case "a#":
                inote = 10;
                break;
            case "b":
                inote = 11;
                break;
        }
        cnote = toctave * 12 + inote;

        command = new String( "" );
        for (int i = 0; i < sequence.length(); i++) {
            String c = sequence.substring( i, i + 1 );
            if ("whq.".contains( c )) {
                command += c;
            }
        }
        String tlength = (command.equalsIgnoreCase( "" )) ? "q" : command;
        String ttlength = (command.equalsIgnoreCase( "" )) ? "" : command;

        command = "";
        int idur=0;
        for (int i = tlength.length() - 1; i >= 0; i--) {
            command += tlength.substring( i, i + 1 );
            switch (command) {
                case ".w":
                    idur += 1920 + 960;
                    command = "";
                    break;
                case ".h":
                    idur += 960 + 480;
                    command = "";
                    break;
                case ".q":
                    idur += 480 + 240;
                    command = "";
                    break;
                case ".i":
                    idur += 240 + 120;
                    command = "";
                    break;
                case ".s":
                    idur += 120 + 60;
                    command = "";
                    break;
                case ".t":
                    idur += 60 + 30;
                    command = "";
                    break;
                case ".x":
                    idur += 30 + 15;
                    command = "";
                    break;
                case ".o":
                    idur += 15 + 7;
                    command = "";
                    break;
                case "w":
                    idur += 1920;
                    command = "";
                    break;
                case "h":
                    idur += 960;
                    command = "";
                    break;
                case "q":
                    idur += 480;
                    command = "";
                    break;
                case "i":
                    idur += 240;
                    command = "";
                    break;
                case "s":
                    idur += 120;
                    command = "";
                    break;
                case "t":
                    idur += 60;
                    command = "";
                    break;
                case "x":
                    idur += 30;
                    command = "";
                    break;
                case "o":
                    idur += 15;
                    command = "";
                    break;
            }
        }
        cdur=idur;
        tkey = sequence.substring( tnote.length()+ttoctave.length(), sequence.length()-ttlength.length()).toUpperCase();

        switch (tkey){

            case"MAJ": buildChord("1 3 5"); break;
            case"MAJ6": buildChord("1 3 5 6"); break;
            case"MAJ7": buildChord("1 3 5 7"); break;
            case"MAJ9": buildChord("1 3 5 7 9"); break;
            case"ADD9": buildChord("1 3 5 9"); break;
            case"MAJ6%9": buildChord("1 3 5 6 9"); break;
            case"MAJ7%6": buildChord("1 3 5 6 7"); break;
            case"MAJ13": buildChord("1 3 5 7 9 13"); break;
            case"MIN": buildChord("1 b3 5"); break;
            case"MIN6": buildChord("1 b3 5 6"); break;
            case"MIN7": buildChord("1 b3 5 b7"); break;
            case"MIN9": buildChord("1 b3 5 b7 9"); break;
            case"MIN11": buildChord("1 b3 5 b7 9 11"); break;
            case"MIN7%11": buildChord("1 b3 5 b7 11"); break;
            case"MINADD9": buildChord("1 b3 5 9"); break;
            case"MIN6%9": buildChord("1 b3 5 6"); break;
            case"MINMAJ7": buildChord("1 b3 5 7"); break;
            case"MINMAJ9": buildChord("1 b3 5 7 9"); break;
            case"DOM7": buildChord("1 3 5 b7"); break;
            case"DOM7%6": buildChord("1 3 5 6 b7"); break;
            case"DOM7%11": buildChord("1 3 5 b7 11"); break;
            case"DOM7SUS": buildChord("1 4 5 b7"); break;
            case"DOM7%6SUS": buildChord("1 4 5 6 b7"); break;
            case"DOM9": buildChord("1 3 5 b7 9"); break;
            case"DOM11": buildChord("1 3 5 b7 9 11"); break;
            case"DOM13": buildChord("1 3 5 b7 9 13"); break;
            case"DOM13SUS": buildChord("1 3 5 b7 11 13"); break;
            case"DOM7%6%11": buildChord("1 3 5 b7 9 11 13"); break;
            case"AUG": buildChord("1 3 #5"); break;
            case"AUG7": buildChord("1 3 #5 b7"); break;
            case"DIM": buildChord("1 b3 b5"); break;
            case"DIM7": buildChord("1 b3 b5 6"); break;
            case"SUS2": buildChord("1 2 5"); break;
            case"SUS4": buildChord("1 4 5"); break;        }

        ctick += cdur; //why not longest? ask the one who made jfugue...


    }

    public void buildChord(String notes){
        int tnote=-1;
        String[] note = notes.trim().split( " " );
        for (int i=0;i<note.length;i++){
            String n=note[i];
            tnote=-1;
            switch (n){
                case "1": tnote=0; break;
                case "#1": case "b2": tnote=1; break;
                case "2": tnote=2; break;
                case "#2": case "b3": tnote=3; break;
                case "3": tnote=4; break;
                case "4": tnote=5; break;
                case "#4": case "b5": tnote=6; break;
                case "5": tnote=7; break;
                case "#5": case "b6": tnote=8; break;
                case "6": tnote=9; break;
                case "#6": case "b7": tnote=10; break;
                case "7": tnote=11; break;
                case "8": tnote=12; break;
                case "#8": case "b9": tnote=13; break;
                case "9": tnote=14; break;
                case "#9": case "b10": tnote=15; break;
                case "10": tnote=16; break;
                case "11": tnote=17; break;
                case "#11": case "b12": tnote=18; break;
                case "12": tnote=19; break;
                case "#12": case "b13": tnote=20; break;
                case "13": tnote=21; break;
            }

            if (tnote!=-1) noteTrack.insertNote(cvoice, cnote+tnote, 100, ctick+i*10, cdur);


        }
    }

    public void addNote(String note) {
        String[] notes = note.split( "\\+" );
        long longest=0;            long idur = 0;

        //C#123+C#5q+Dq
        for (String n: notes) {
            String tnote = "";
            int toctave = 5;
            String tlength = "";
            String command = new String( "" );
            String sequence = n.toLowerCase();

            for (int i = 0; i < sequence.length(); i++) {
                String c = sequence.substring( i, i + 1 );
                if ("abcdefgr#".contains( c )) {
                    command += c;
                }
            }
            tnote = command;

            command = new String( "" );
            for (int i = 0; i < sequence.length(); i++) {
                String c = sequence.substring( i, i + 1 );
                if ("0123456789".contains( c )) {
                    command += c;
                }
            }
            toctave = (command.equalsIgnoreCase( "" )) ? 5 : Integer.parseInt( command );

            command = new String( "" );
            for (int i = 0; i < sequence.length(); i++) {
                String c = sequence.substring( i, i + 1 );
                if ("whqistxo.".contains( c )) {
                    command += c;
                }
            }
            tlength = (command.equalsIgnoreCase( "" )) ? "q" : command;

            int inote = 0;
            switch (tnote) {
                case "c":
                    inote = 0;
                    break;
                case "c#":
                    inote = 1;
                    break;
                case "d":
                    inote = 2;
                    break;
                case "d#":
                    inote = 3;
                    break;
                case "e":
                    inote = 4;
                    break;
                case "f":
                    inote = 5;
                    break;
                case "f#":
                    inote = 6;
                    break;
                case "g":
                    inote = 7;
                    break;
                case "g#":
                    inote = 8;
                    break;
                case "a":
                    inote = 9;
                    break;
                case "a#":
                    inote = 10;
                    break;
                case "b":
                    inote = 11;
                    break;
            }
            cnote = toctave * 12 + inote;

            idur=0;

            command = "";
            for (int i = tlength.length() - 1; i >= 0; i--) {
                command += tlength.substring( i, i + 1 );
                switch (command) {
                    case ".w":
                        idur += 1920 + 960;
                        command = "";
                        break;
                    case ".h":
                        idur += 960 + 480;
                        command = "";
                        break;
                    case ".q":
                        idur += 480 + 240;
                        command = "";
                        break;
                    case ".i":
                        idur += 240 + 120;
                        command = "";
                        break;
                    case ".s":
                        idur += 120 + 60;
                        command = "";
                        break;
                    case ".t":
                        idur += 60 + 30;
                        command = "";
                        break;
                    case ".x":
                        idur += 30 + 15;
                        command = "";
                        break;
                    case ".o":
                        idur += 15 + 7;
                        command = "";
                        break;
                    case "w":
                        idur += 1920;
                        command = "";
                        break;
                    case "h":
                        idur += 960;
                        command = "";
                        break;
                    case "q":
                        idur += 480;
                        command = "";
                        break;
                    case "i":
                        idur += 240;
                        command = "";
                        break;
                    case "s":
                        idur += 120;
                        command = "";
                        break;
                    case "t":
                        idur += 60;
                        command = "";
                        break;
                    case "x":
                        idur += 30;
                        command = "";
                        break;
                    case "o":
                        idur += 15;
                        command = "";
                        break;
                }
            }

            longest = (idur>longest) ? idur : longest;

            if (!tnote.toLowerCase().equalsIgnoreCase( "r" )) {
                noteTrack.insertNote( cvoice, cnote, 100, ctick, idur );
            }
        }
        ctick += idur; //why not longest? ask the one who made jfugue...

    }

    public void addDrum(String note) {
        //C#123+C#5q+Dq
        String tnote=""; int toctave=5; String tlength="";
        String command= new String("");
        String sequence = note.toUpperCase();


        tnote=sequence.substring( 0,sequence.indexOf( "]" )+1);

        tlength=sequence.substring( sequence.indexOf( "]" )+1).toLowerCase();
        Gdx.app.log(tlength+"","");
        int inote=0;
        switch (tnote){
            case "[ACOUSTIC_BASS_DRUM]": inote=35; break;
            case "[BASS_DRUM]": inote=36; break;
            case "[SIDE_KICK]": inote=37; break;
            case "[ACOUSTIC_SNARE]": inote=38; break;
            case "[HAND_CLAP]": inote=39; break;
            case "[ELECTRIC_SNARE]": inote=40; break;
            case "[LO_FLOOR_TOM]": inote=41; break;
            case "[CLOSED_HI_HAT]": inote=42; break;
            case "[HIGH_FLOOR_TOM]": inote=43; break;
            case "[PEDAL_HI_HAT]": inote=44; break;
            case "[LO_TOM]": inote=45; break;
            case "[OPEN_HI_HAT]": inote=46; break;
            case "[LO_MID_TOM]": inote=47; break;
            case "[HI_MID_TOM]": inote=48; break;
            case "[CRASH_CYMBAL_1]": inote=49; break;
            case "[HI_TOM]": inote=50; break;
            case "[RIDE_CYMBAL_1]": inote=51; break;
            case "[CHINESE_CYMBAL]": inote=52; break;
            case "[RIDE_BELL]": inote=53; break;
            case "[TAMBOURINE]": inote=54; break;
            case "[SPLASH_CYMBAL]": inote=55; break;
            case "[COWBELL]": inote=56; break;
            case "[CRASH_CYMBAL_2]": inote=57; break;
            case "[VIBRASLAP]": inote=58; break;
            case "[RIDE_CYMBAL_2]": inote=59; break;
            case "[HI_BONGO]": inote=60; break;
            case "[LO_BONGO]": inote=61; break;
            case "[MUTE_HI_CONGA]": inote=62; break;
            case "[OPEN_HI_CONGA]": inote=63; break;
            case "[LO_CONGA]": inote=64; break;
            case "[HI_TIMBALE]": inote=65; break;
            case "[LO_TIMBALE]": inote=66; break;
            case "[HI_AGOGO]": inote=67; break;
            case "[LO_AGOGO]": inote=68; break;
            case "[CABASA]": inote=69; break;
            case "[MARACAS]": inote=70; break;
            case "[SHORT_WHISTLE]": inote=71; break;
            case "[LONG_WHISTLE]": inote=72; break;
            case "[SHORT_GUIRO]": inote=73; break;
            case "[LONG_GUIRO]": inote=74; break;
            case "[CLAVES]": inote=75; break;
            case "[HI_WOOD_BLOCK]": inote=76; break;
            case "[LO_WOOD_BLOCK]": inote=77; break;
            case "[MUTE_CUICA]": inote=78; break;
            case "[OPEN_CUICA]": inote=79; break;
            case "[MUTE_TRIANGLE]": inote=80; break;
            case "[OPEN_TRIANGLE]": inote=81; break;        }
        cnote= inote;

        long idur=0;
        command="";
        for (int i=tlength.length()-1;i>=0;i--){
            command+=tlength.substring(i,i+1);
            switch(command){
                case ".w":	idur+=	1920+	960	;	command="";	break;
                case ".h":	idur+=	960	+	480	;	command="";	break;
                case ".q":	idur+=	480	+	240	;	command="";	break;
                case ".i":	idur+=	240	+	120	;	command="";	break;
                case ".s":	idur+=	120	+	60	;	command="";	break;
                case ".t":	idur+=	60	+	30	;	command="";	break;
                case ".x":	idur+=	30	+	15	;	command="";	break;
                case ".o":	idur+=	15	+	7	;	command="";	break;
                case "w":	idur+=	1920		;	command="";	break;
                case "h":	idur+=	960			;	command="";	break;
                case "q":	idur+=	480			;	command="";	break;
                case "i":	idur+=	240			;	command="";	break;
                case "s":	idur+=	120			;	command="";	break;
                case "t":	idur+=	60			;	command="";	break;
                case "x":	idur+=	30			;	command="";	break;
                case "o":	idur+=	15			;	command="";	break;
            }
        }

        if (tnote.toLowerCase().equalsIgnoreCase( "r" )){
            ctick+=idur;
        }else{
            noteTrack.insertNote(cvoice, cnote, 100, ctick, idur);
            if (!note.contains( "+" )) ctick+=idur;

        }



    }

    public void save(String path){
        // 1. Create some MidiTracks
        tempoTrack = new MidiTrack();
        noteTrack = new MidiTrack();

// 2. Add events to the tracks
// Track 0 is the tempo map
        //set the value for now. don't overcomplicate.
        TimeSignature ts = new TimeSignature();
        ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);

        Tempo tempo = new Tempo();
        tempo.setBpm(120);

        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(tempo);

// Track 1 will have some notes in it
        String[] commands; boolean block=false, blocked=false;
        commands=sequence.toUpperCase().split( " " );



        for (String com : commands) {
            if (com.length()==0) continue;
            switch (com.substring( 0, 1 )) {
                case "V":
                    setVoice( Integer.parseInt( com.substring( 1 ) ) );
                    break;
                case "L":
                    setLayer( Integer.parseInt( com.substring( 1 ) ) );
                    break;
                case "I":
                    setInstrument( com.substring( 1 ) );
                    break;
                case "A":
                case "a":
                case "B":
                case "b":
                case "C":
                case "c":
                case "D":
                case "d":
                case "E":
                case "e":
                case "F":
                case "f":
                case "G":
                case "g":
                case "R":
                case "r":
                    boolean chord=false;
                    if (com.contains( "MAJ" )) chord=true;
                    if (com.contains( "MIN" )) chord=true;
                    if (com.contains( "AUG" )) chord=true;
                    if (com.contains( "ADD" )) chord=true;
                    if (com.contains( "DOM" )) chord=true;
                    if (com.contains( "DIM" )) chord=true;
                    if (com.contains( "SUS" )) chord=true;
                    if (chord){
                        addChord(com);
                    }else{
                        addNote(com);
                    }
                    break;
                case "[":
                    addDrum( com );
                    break;
                default:
            }
        }

// 3. Create a MidiFile with the tracks we created
        List<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);

        MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
        //playmidi( midi );
// 4. Write the MIDI data to a file

        FileHandle fl = Gdx.files.external( path );
        File output = fl.file();
        try
        {
            midi.writeToFile(output);
        }
        catch(IOException e)
        {
            System.err.println(e);
        }


        /*
         */


    }

    private void playmidi(MidiFile midi){
        // Create a new MidiProcessor:
        MidiProcessor processor = new MidiProcessor(midi);

// Register for the events you're interested in:
        //EventPrinter ep = new EventPrinter("Individual Listener");
        //processor.registerEventListener(ep, Tempo.class);
        //processor.registerEventListener(ep, NoteOn.class);

// or listen for all events:
        EventPrinter ep2 = new EventPrinter("Listener For All");
        processor.registerEventListener(ep2, MidiEvent.class);

// Start the processor:
        processor.start();
    }
}
