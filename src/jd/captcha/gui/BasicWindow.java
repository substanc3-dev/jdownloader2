//    jDownloader - Downloadmanager
//    Copyright (C) 2008  JD-Team jdownloader@freenet.de
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.


package jd.captcha.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import jd.captcha.utils.UTILITIES;

/**
 * Die Klasse dient als Window Basis Klasse.
 * 
 * @author JD-Team
 */
public class BasicWindow extends JFrame {
    /**
     * Aktuelle X Position der Autopositionierung
     */
    private static int        screenPosX       = 0;

    /**
     * Aktuelle Y Position der Autopositionierung
     */
    private static int        screenPosY       = 0;

    /**
     * 
     */
    private static final long serialVersionUID = 8474181150357563979L;

    /**
     * @param title
     * @param width
     * @param height
     * @return neues Fenster
     */
    public static BasicWindow getWindow(String title, int width, int height) {
//        new JFrame().getToolkit().getScreenSize();

        BasicWindow w = new BasicWindow();

        w.setSize(width, height);

        w.setLocation(screenPosX, screenPosY);
//        screenPosY += height + 30;
//        if (screenPosY >= screenSize.height) {
//            screenPosX += width + 40;
//            screenPosY = 0;
//        }

        w.setLayout(new GridBagLayout());
        w.setVisible(true);
        w.setTitle(title);
        w.repack();
        w.pack();

        return w;

    }

    /**
     * Zeigt ein Image in einem Neuen fenster an. Die fenster Positionieren sich
     * von Links oben nach rechts uten von selbst
     * 
     * @param file
     * @param title
     * @return Neues Fenster
     */

    public static BasicWindow showImage(File file, String title) {
//        new JFrame().getToolkit().getScreenSize();
        Image img = UTILITIES.loadImage(file);
        BasicWindow w = new BasicWindow();
        ImageComponent ic = new ImageComponent(img);

        w.setSize(ic.getImageWidth() + 10, ic.getImageHeight() + 20);
        w.setLocation(screenPosX, screenPosY);
     //   screenPosY += ic.getImageHeight() + 40;
//        if (screenPosY >= screenSize.height) {
//            screenPosX += ic.getImageWidth() + 160;
//            screenPosY = 0;
//        }
        w.setTitle(title);
        w.setLayout(new GridBagLayout());
        w.add(ic, UTILITIES.getGBC(0, 0, 1, 1));
        w.setVisible(true);
        w.pack();
        w.repack();

        return w;

    }

    /**
     * Zeigt ein Image in einem neuen fenster an.Die fenster Positionieren sich
     * von Links oben nach rechts uten von selbst
     * 
     * @param img
     * @return BasicWindow Das neue fenster
     */
    public static BasicWindow showImage(Image img) {

        return showImage(img, img.toString());
    }

    /**
     * Zeigt ein Bild an und setzt es um width/Height zum letzten Bild auf den
     * Screen
     * 
     * @param img
     * @param width
     * @param height
     * @return Neues Fenster
     */
    public static BasicWindow showImage(Image img, int width, int height) {
//        new JFrame().getToolkit().getScreenSize();

        BasicWindow w = new BasicWindow();
        ImageComponent ic = new ImageComponent(img);

        w.setSize(width, height);

        w.setLocation(screenPosX, screenPosY);
//        screenPosY += height + 30;
//        if (screenPosY >= screenSize.height) {
//            screenPosX += width + 40;
//            screenPosY = 0;
//        }

        w.setLayout(new GridBagLayout());
        w.add(ic, UTILITIES.getGBC(0, 0, 1, 1));
        w.setVisible(true);

        w.repack();
        w.pack();

        return w;

    }

    /**
     * Zeigt ein image in einem Neuen fenster an. Das fenster positioniert sich
     * im nächsten Freien bereich
     * 
     * @param img
     * @param title
     * @return BasicWindow das neue fenster
     */
    public static BasicWindow showImage(Image img, String title) {
//        new JFrame().getToolkit().getScreenSize();

        BasicWindow w = new BasicWindow();
        ImageComponent ic = new ImageComponent(img);

        w.setSize(ic.getImageWidth() + 10, ic.getImageHeight() + 20);
        w.repack();
        w.pack();
        w.setLocation(screenPosX, screenPosY);
       // screenPosY += w.getSize().width + 30;
//        if (screenPosY >= screenSize.height) {
//            screenPosX += w.getSize().height + 160;
//            screenPosY = 0;
//        }
        w.setTitle(title);
        w.setLayout(new GridBagLayout());
        w.add(ic, UTILITIES.getGBC(0, 0, 1, 1));
        w.setVisible(true);

        w.repack();
        w.pack();

        return w;

    }

    /**
     * Gibt an ob beim Schließen des fensters das programm beendet werden sol
     */
    public boolean            exitSystem       = false;

    /**
     * Owner. Owner der GUI
     */
    public Object             owner;

    /**
     * Erstellt ein einfaches neues GUI Fenster
     */
    public BasicWindow() {
        initWindow();
    }

    /**
     * Erstellt ein neues GUI Fenster mit dem Oner owner
     * 
     * @param owner
     */
    public BasicWindow(Object owner) {
        this.owner = owner;
        initWindow();
    }

    /**
     * Gibt das Fenster wieder frei
     */
    public void destroy() {
        setVisible(false);
        dispose();
    }

    /**
     * Gibt die default GridbagConstants zurück
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @return Default GridBagConstraints
     */
    public GridBagConstraints getGBC(int x, int y, int width, int height) {

        GridBagConstraints gbc = UTILITIES.getGBC(x, y, width, height);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.weightx = 1;

        return gbc;
    }

    /**
     * Initialisiert das Fenster und setzt den WindowClosing Adapter
     */
    private void initWindow() {
        final BasicWindow _this = this;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                Window window = event.getWindow();
                _this.setVisible(true);
                window.setVisible(false);
                window.dispose();
                if (_this.exitSystem) {
                    System.exit(0);
                }
            }

        });

        resizeWindow(100);
        setLocationByScreenPercent(50, 50);
        setBackground(Color.LIGHT_GRAY);
    }
@Override
public void pack() {
    try{
    super.pack();
    Dimension screenSize = getToolkit().getScreenSize();
    
    int newWidth = (int) Math.min(Math.max(this.getSize().width,300), screenSize.getWidth());
    int newHeight = (int) Math.min(Math.max(this.getSize().height,300), screenSize.getHeight());
    this.setSize(newWidth, newHeight);
    }catch(Exception e){
        
    }
}
    /**
     * Skaliert alle Komponenten und das fenster neu
     */
    public void refreshUI() {
        this.pack();
        this.repack();
    }
    /**
     * packt das fenster neu
     */
    public void repack() {
        final BasicWindow _this=this;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SwingUtilities.updateComponentTreeUI(_this);

            }
        });
       
    }

    /**
     * Prozentuales (im bezug aufd en Screen) setzend er größe
     * 
     * @param percent
     *            in screenProzent
     */
    public void resizeWindow(int percent) {
        Dimension screenSize = getToolkit().getScreenSize();
        setSize((screenSize.width * percent) / 100, (screenSize.height * percent) / 100);
    }

    /**
     * Fügt relative Threadsafe  an x,y die Kompoente cmp ein
     * @param x
     * @param y
     * @param cmp
     */
        public void setComponent(final int x, final int y, final Component cmp) {
            if(cmp==null)return;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    add(cmp, getGBC(x, y, 1, 1));
    
                }
            });
        }

    /**
     * Fügt relative Threadsafe  an x,y das Bild img ein
     * @param x
     * @param y
     * @param img 
     */
    public void setImage(final int x, final int y, final Image img) {
if(img==null)return;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                add(new ImageComponent(img), getGBC(x, y, 1, 1));

            }
        });
    }

    /**
     * Prozentuales Positionsetzen des fensters (Mittelpunkt
     * 
     * @param width
     *            in screenprozent
     * @param height
     *            in ScreenProzent
     */
    public void setLocationByScreenPercent(int width, int height) {
        Dimension screenSize = getToolkit().getScreenSize();

        setLocation(((screenSize.width - getSize().width) * width) / 100, ((screenSize.height - getSize().height) * height) / 100);
    }

    /**
     * Fügt relative Threadsafe  an x,y den text cmp ein
     * @param x
     * @param y
     * @param cmp
     */
    public void setText(final int x, final int y, final Object cmp) {
        if(cmp==null)return;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                add(new JLabel(cmp.toString()), getGBC(x, y, 1, 1));

            }
        });
    }
}